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

import javax.jms.*;
import java.util.concurrent.BlockingQueue;

/** ActiveMQ Producer for outputting the JSON messages to the recipient ActiveMQ server.
 *
 * @author George Goldberg <george@fasteroute.com>
 */
public class JsonProducer implements Runnable {
    private final Logger log = LoggerFactory.getLogger(JsonProducer.class);

    private final String url;
    private final String topic;
    private final String user;
    private final String pass;
    private final BlockingQueue<String> onwardQueue;

    public JsonProducer(String url, String topic, String user, String pass, BlockingQueue<String> onwardQueue) {
        this.url = url;
        this.topic = topic;
        this.user = user;
        this.pass = pass;
        this.onwardQueue = onwardQueue;
    }

    public void run() {
        ActiveMQConnectionFactory connectionFactory;
        Connection connection;
        Session session;
        Destination destination;
        MessageProducer producer;
        try {
            // Create a ConnectionFactory
            log.debug("Instantiating Connection Factory");
            connectionFactory = new ActiveMQConnectionFactory(this.user, this.pass, this.url);

            // Create a Connection
            log.debug("Connecting to ActiveMQ server");
            connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            log.debug("Creating Session");
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            destination = session.createTopic(this.topic);

            // Create a MessageProducer from the Session to the Topic or Queue
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        } catch (Exception e) {
            log.error("Something went wrong setting things up");
            return;
        }

        do {
            try {
                String text = onwardQueue.take();
                if (text == null) {
                    continue;
                }

                TextMessage message = session.createTextMessage(text);

                // Tell the producer to send the message
                System.out.println("Sent message: " + message.hashCode() + " : " + Thread.currentThread().getName());
                producer.send(message);

            } catch (Exception e) {
                System.out.println("Caught: " + e);
                e.printStackTrace();
            }
        } while (true);
        /*
        // Clean up
                session.close();
                connection.close();
         */
    }
}
