package uk.fstr.darwingateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.fstr.darwingateway.bindings.*;

import java.util.concurrent.BlockingQueue;

/**
 * Created by gberg on 17/08/15.
 */
public class MessageParser implements Runnable {
    private final Logger log = LoggerFactory.getLogger(MessageParser.class);
    private final BlockingQueue<Pport> inputQueue;
    private final BlockingQueue<String> outputQueue;

    public MessageParser(BlockingQueue<Pport> inputQueue, BlockingQueue<String> outputQueue) {
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
    }

    public void run() {
        do {
            Pport pport;
            try {
                pport = inputQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }

            processMessage(pport);
        } while (true);
    }

    public void processMessage(Pport m) {
        if (m.getUR() == null) {
            log.error("UR is null");
            return;
        }

        Pport.UR r = m.getUR();

        for (Schedule s: r.getSchedule()) {
            //log.debug("Received schedule message");
        }

        for (Association a: r.getAssociation()) {
            //log.debug("Received association message");
        }

        for (DeactivatedSchedule d: r.getDeactivated()) {
            log.debug("Received deactivated message");
            String value = new String();
            value += d.getRid();
            outputQueue.add(value);
        }

        for (TS t: r.getTS()) {
            //log.debug("Received a train status message");
        }

        for (StationMessage s: r.getOW()) {
            //log.debug("Received a station message");
        }

        for (TrainAlert a: r.getTrainAlert()) {
            //log.debug("Received a train alert message");
        }

        for (TrainOrder o: r.getTrainOrder()) {
            //log.debug("Received a train order message");
        }

        for (TrackingID t: r.getTrackingID()) {
            //log.debug("Received a tracking ID message");
        }

        for (RTTIAlarm a: r.getAlarm()) {
            //log.debug("Received an alarm message");
        }
    }
}
