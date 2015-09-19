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

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Message;
import java.util.concurrent.BlockingQueue;

/** RabbitMQ Producer for outputting the JSON messages to the recipient RabbitMQ server.
 *
 * @author George Goldberg <george@fasteroute.com>
 */
public class RabbitJsonProducer implements Runnable {
    private final Logger log = LoggerFactory.getLogger(JsonProducer.class);

    private final String url;
    private final String topic;
    private final String user;
    private final String pass;
    private final BlockingQueue<MessageAndJsonStringPair> onwardQueue;
    private final BlockingQueue<Message> ackQueue;

    public RabbitJsonProducer(String url, String topic, String user, String pass,
                        BlockingQueue<MessageAndJsonStringPair> onwardQueue, BlockingQueue<Message> ackQueue) {
        this.url = url;
        this.topic = topic;
        this.user = user;
        this.pass = pass;
        this.onwardQueue = onwardQueue;
        this.ackQueue = ackQueue;
    }

    public void run() {
        Channel channel;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(url);
            factory.setUsername(user);
            factory.setPassword(pass);
            Connection connection = factory.newConnection();
            channel = connection.createChannel();

            //channel.exchangeDeclare("JsonPushPort", "fanout");
        } catch (Exception e) {
            log.error("Something went wrong setting up the rabbitmq client.", e);
            return;
        }

        do {
            try {
                MessageAndJsonStringPair payload = onwardQueue.take();

                if (payload.string == null) {
                    continue;
                }

                channel.basicPublish(topic, "", null, payload.string.getBytes());

                Message message = payload.message;
                if (message != null) {
                    // Tell the producer to send the message
                    log.debug("Sent message: " + message.hashCode() + " : " + Thread.currentThread().getName());

                    // Add the message to the ack queue.
                    ackQueue.add(message);
                }

            } catch (Exception e) {
                log.error("Caught Exception", e);
            }
        } while (true);
        /*
        // Clean up
                session.close();
                connection.close();
         */
    }
}
