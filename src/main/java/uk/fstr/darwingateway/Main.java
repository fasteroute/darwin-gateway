package uk.fstr.darwingateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(Main.class);
        log.info("Launching Darwin Gateway.");

        Map<String, String> env = System.getenv();
        String STOMP_QUEUE;
        String STOMP_USER;
        String STOMP_PASS;
        try {
            STOMP_QUEUE = env.get("STOMP_QUEUE");
            STOMP_USER = env.get("STOMP_USER");
            STOMP_PASS = env.get("STOMP_PASS");
        } catch (Exception e) {
            log.error("Could not get all STOMP environment variables");
            return;
        }

        thread(new PushPortListener(STOMP_QUEUE, STOMP_USER, STOMP_PASS), false);

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


