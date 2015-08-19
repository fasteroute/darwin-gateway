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

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/** Main class - instantiates the application control flow objects and joins them all together.
 *
 * @author George Goldberg <george@fasteroute.com>
 */
public class Main {

    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(Main.class);
        log.info("Launching Darwin Gateway.");

        // Get all the environment variables we need.
        Map<String, String> env = System.getenv();
        String STOMP_QUEUE;
        String STOMP_USER;
        String STOMP_PASS;
        String JSON_URL;
        String JSON_TOPIC;
        String JSON_USER;
        String JSON_PASS;
        try {
            STOMP_QUEUE = env.get("STOMP_QUEUE");
            STOMP_USER = env.get("STOMP_USER");
            STOMP_PASS = env.get("STOMP_PASS");
            JSON_URL = env.get("JSON_URL");
            JSON_TOPIC = env.get("JSON_TOPIC");
            JSON_USER = env.get("JSON_USER");
            JSON_PASS = env.get("JSON_PASS");
        } catch (Exception e) {
            log.error("Could not get all STOMP environment variables");
            return;
        }

        BlockingQueue<Pport> pportInputQueue = new LinkedBlockingQueue<>();
        BlockingQueue<String> jsonOutputQueue = new LinkedBlockingQueue<>();

        thread(new PushPortListener(STOMP_QUEUE, STOMP_USER, STOMP_PASS, pportInputQueue), false);
        thread(new MessageParser(pportInputQueue, jsonOutputQueue), false);
        thread(new JsonProducer(JSON_URL, JSON_TOPIC, JSON_USER, JSON_PASS, jsonOutputQueue), false);

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


