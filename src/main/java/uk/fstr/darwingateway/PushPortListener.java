/**
 * Copyright 2015 Faster Route Limited <hello@fasteroute.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.fstr.darwingateway;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.fstr.darwingateway.bindings.Pport;

import javax.jms.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.zip.GZIPInputStream;

/** Listens on the NRE ActiveMQ server to receive Push Port messages and instantiate them with the XML bindings.
 *
 * @author George Goldberg <george@fasteroute.com>
 */
public class PushPortListener implements Runnable, ExceptionListener {
    private final Logger log = LoggerFactory.getLogger(PushPortListener.class);

    private final String ppAddr;
    private final String ppQueue;
    private final String ppUser;
    private final String ppPass;
    private final BlockingQueue outputQueue;
    private final BlockingQueue ackQueue;

    private final ActiveMQConnectionFactory connectionFactory;
    private final JAXBContext jaxbContext;

    private int backoffTime = 1;

    public PushPortListener(final String addr, final String queue, final String user, final String pass,
                            BlockingQueue<MessageAndPPortPair> outputQueue, final BlockingQueue<Message> ackQueue) {
        this.ppAddr = addr;
        this.ppQueue = queue;
        this.ppUser = user;
        this.ppPass = pass;
        this.outputQueue = outputQueue;
        this.ackQueue = ackQueue;

        connectionFactory = new ActiveMQConnectionFactory(this.ppUser, this.ppPass, this.ppAddr);

        JAXBContext jbc = null;
        try {
            jbc = JAXBContext.newInstance("uk.fstr.darwingateway.bindings");
        } catch (Exception e) {
            log.error("Could not build JAXB context");
        }
        jaxbContext = jbc;
    }

    public void run() {
        // Main loop of the thread.
        do {
            // Connect to the Push Port server. This will only return if the connection is lost for some reason.
            connect();

            try {
                backoffTime = backoffTime * 2;
                if (backoffTime > 256) {
                    backoffTime = 128;
                }
                log.info("Connection to Push Port server lost. Attempting to reconnect in {} seconds.", backoffTime);
                Thread.sleep(backoffTime*1000);
            } catch (InterruptedException e) {
                log.error("Sleep until time to try reconnect was interrupted.", e);
            }
        } while (true);
    }

    public void connect() {
        // Create a Connection
        Connection connection;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
        } catch (Exception e) {
            log.error("Could not create ActiveMQ connection.");
            return;
        }

        try {
            connection.setExceptionListener(this);
        } catch (Exception e) {
            log.error("Could not set connection exception listener.");
            try {
                connection.close();
            } catch (Exception f) {
                log.error("Could not close connection");
            }
            return;
        }

        // Create a Session
        Session session;
        try {
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        } catch (Exception e) {
            log.error("Could not create a session.");
            try {
                connection.close();
            } catch (Exception f) {
                log.error("Could not close connection");
            }
            return;
        }

        // Create the destination (Topic or Queue)
        Destination destination;
        try {
            destination = session.createQueue(this.ppQueue);
        } catch (Exception e) {
            log.error("Could not create queue");
            try {
                session.close();
                connection.close();
            } catch (Exception f) {
                log.error("Could not close session or connection");
            }
            return;
        }

        // Create a MessageConsumer from the Session to the Topic or Queue
        MessageConsumer consumer;
        try {
            consumer = session.createConsumer(destination);
        } catch (Exception e) {
            log.error("Could not create consumer.");
            try {
                session.close();
                connection.close();
            } catch (Exception f) {
                log.error("Could not close session or connection.");
            }
            return;
        }

        // We're connected. Reset the backoff time.
        backoffTime = 1;

        do {
            try {
                do {
                    Message m = (Message) ackQueue.poll();
                    if (m == null) {
                        break;
                    }
                    // Acking immediately for now to avoid memory leak.
                    //m.acknowledge();
                    //log.debug("Acking message");
                } while(true);
            } catch (Exception e) {
                log.error("Something horrible happened trying to get messages from the Ack queue.");
                break;
            }
            try {
                // Wait for a message
                Message message = consumer.receive(100);

                if (message instanceof BytesMessage) {
                    final BytesMessage bytesMessage = (BytesMessage) message;
                    byte[] messageBytes;

                    try {
                        messageBytes = new byte[(int) bytesMessage.getBodyLength()];
                        bytesMessage.readBytes(messageBytes);
                        //System.out.println("Read " + messageBytes.length + " bytes");
                    } catch (JMSException e1) {
                        throw new RuntimeException(e1);
                    }

                    GZIPInputStream in = null;
                    OutputStream out = null;
                    try {
                        in = new GZIPInputStream(new ByteArrayInputStream(messageBytes));

                        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                        Reader reader = new InputStreamReader(in, "UTF-8");
                        try {
                            Pport pport = (Pport) unmarshaller.unmarshal(reader);
                            outputQueue.add(new MessageAndPPortPair(message, pport));

                            // Acknowledge messages here to stop them leaking.
                            // FIXME: Change to a less buggy activemq client library to fix this properly.
                            message.acknowledge();
                        } finally {
                            reader.close();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException ignore) {

                            }
                        }
                    }
                }

            }catch (Exception e) {
                log.error("Caught Exception", e);
                return;
            }
        } while(true);
    }

    public synchronized void onException(JMSException ex) {
        log.error("JMS Exception occured.  Shutting down client.");
    }
}
