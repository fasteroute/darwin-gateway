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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.fstr.darwingateway.bindings.*;
import uk.fstr.darwingateway.serializers.*;

import java.util.concurrent.BlockingQueue;

/** Receives messages generated from the XML bindings, and converts them to JSON strings.
 *
 * @author George Goldberg <george@fasteroute.com>
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
                .registerTypeAdapter(OR.class, new ORSerializer())
                .registerTypeAdapter(OPOR.class, new OPORSerializer())
                .registerTypeAdapter(IP.class, new IPSerializer())
                .registerTypeAdapter(OPIP.class, new OPIPSerializer())
                .registerTypeAdapter(PP.class, new PPSerializer())
                .registerTypeAdapter(DT.class, new DTSerializer())
                .registerTypeAdapter(OPDT.class, new OPDTSerializer())
                .registerTypeAdapter(AssocService.class, new AssocServiceSerializer())
                .registerTypeAdapter(TrainOrderItem.class, new TrainOrderItemSerializer())
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
