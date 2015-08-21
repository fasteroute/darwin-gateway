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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.fstr.darwingateway.bindings.Pport;

import javax.jms.Message;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/** Main class - instantiates the application control flow objects and joins them all together.
 *
 * @author George Goldberg <george@fasteroute.com>
 */
public class Main {

    private final static String IN_ADDR = "DG_IN_ADDR";
    private final static String IN_QUEUE = "DG_IN_QUEUE";
    private final static String IN_USER = "DG_IN_USER";
    private final static String IN_PASS = "DG_IN_PASS";
    private final static String OUT_ADDR = "DG_OUT_ADDR";
    private final static String OUT_TOPIC = "DG_OUT_TOPIC";
    private final static String OUT_USER = "DG_OUT_USER";
    private final static String OUT_PASS = "DG_OUT_PASS";

    public static void main(String[] args) {

        // Set up the main function loger.
        Logger log = LoggerFactory.getLogger(Main.class);
        log.info("Launching Darwin Gateway.");

        // Read in all the required environment variables.
        Map<String, String> env = System.getenv();

        String inAddr = env.get(IN_ADDR);
        String inQueue = env.get(IN_QUEUE);
        String inUser = env.get(IN_USER);
        String inPass = env.get(IN_PASS);
        String outAddr = env.get(OUT_ADDR);
        String outTopic = env.get(OUT_TOPIC);
        String outUser = env.get(OUT_USER);
        String outPass = env.get(OUT_PASS);

        if (inAddr == null || inQueue == null || inUser == null || inPass == null || outAddr == null || outTopic == null
                || outUser == null || outPass == null) {
            System.err.println();
            System.err.println();
            System.err.println("All of the following environment variables must be set for this program to run:");
            System.err.printf("    $%s\t\t - The address of the Push Port ActiveMQ server.\n", IN_ADDR);
            System.err.printf("    $%s\t - The queue name to connect to on the Push Port ActiveMQ server.\n", IN_QUEUE);
            System.err.printf("    $%s\t\t - The user name to connect to the Push Port ActiveMQ server with.\n", IN_USER);
            System.err.printf("    $%s\t\t - The password to connect to the Push Port ActiveMQ server with.\n", IN_PASS);
            System.err.printf("    $%s\t - The address of the Output ActiveMQ server to send JSON messages to.\n", OUT_ADDR);
            System.err.printf("    $%s\t - The topic name on the Output ActiveMQ server to send JSON messages to.\n", OUT_TOPIC);
            System.err.printf("    $%s\t - The user name to connect to the Output ActiveMQ server with.\n", OUT_USER);
            System.err.printf("    $%s\t - The password to connect to the Output ActiveMQ server with.\n", OUT_PASS);
            System.err.println();
            System.exit(1);
        }

        final BlockingQueue<MessageAndPPortPair> pportInputQueue = new LinkedBlockingQueue<>();
        final BlockingQueue<MessageAndJsonStringPair> jsonOutputQueue = new LinkedBlockingQueue<>();
        final BlockingQueue<Message> messageAckQueue = new LinkedBlockingQueue<>();

        thread(new PushPortListener(inAddr, inQueue, inUser, inPass, pportInputQueue, messageAckQueue), false);
        thread(new MessageParser(pportInputQueue, jsonOutputQueue), false);
        thread(new JsonProducer(outAddr, outTopic, outUser, outPass, jsonOutputQueue, messageAckQueue), false);

        thread(new Runnable() {
            @Override
            public void run() {
                do {
                    log.info("Queue Status Report. In Queue: {}, Out Queue: {}, Ack Queue {}", pportInputQueue.size(), jsonOutputQueue.size(), messageAckQueue.size());
                    try {
                        Thread.sleep(60000);
                    } catch (Exception e) {
                        continue;
                    }
                } while(true);
            }
        }, false);

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                log.error("Caught Exception whilst sleeping main thread.");
                return;
            }
        }
    }

    public static void thread(Runnable runnable, boolean daemon) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.setDaemon(daemon);
        brokerThread.start();
    }
}


