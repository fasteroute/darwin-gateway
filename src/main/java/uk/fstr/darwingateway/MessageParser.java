package uk.fstr.darwingateway;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.fstr.darwingateway.bindings.*;
import uk.fstr.darwingateway.serializers.*;

import java.util.concurrent.BlockingQueue;

/**
 * Created by gberg on 17/08/15.
 */
public class MessageParser implements Runnable {
    private final Logger log = LoggerFactory.getLogger(MessageParser.class);
    private final BlockingQueue<Pport> inputQueue;
    private final BlockingQueue<String> outputQueue;
    private final Gson gson;

    public MessageParser(BlockingQueue<Pport> inputQueue, BlockingQueue<String> outputQueue) {
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;

        this.gson = new GsonBuilder()
                .registerTypeAdapter(Pport.class, new PushPortSerializer())
                .registerTypeAdapter(Schedule.class, new ScheduleMessageSerializer())
                .registerTypeAdapter(Association.class, new AssociationMessageSerializer())
                .registerTypeAdapter(DeactivatedSchedule.class, new DeactivatedMessageSerializer())
                .registerTypeAdapter(TS.class, new TrainStatusMessageSerializer())
                .registerTypeAdapter(StationMessage.class, new StationMessageSerializer())
                .registerTypeAdapter(TrainOrder.class, new TrainOrderMessageSerializer())
                .registerTypeAdapter(RTTIAlarm.class, new AlarmMessageSerializer())
                .registerTypeAdapter(DisruptionReasonType.class, new DisruptionReasonTypeSerializer())
                .registerTypeAdapter(TSLocation.class, new TSLocationSerializer())
                .registerTypeAdapter(TSTimeData.class, new TSTimeDataSerializer())
                .registerTypeAdapter(PlatformData.class, new PlatformDataSerializer())
                //.setPrettyPrinting()
                .create();
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
        outputQueue.add(gson.toJson(m));
    }
}
