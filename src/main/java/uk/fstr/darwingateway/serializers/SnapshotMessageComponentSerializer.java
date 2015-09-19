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
package uk.fstr.darwingateway.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import uk.fstr.darwingateway.SnapshotMessageComponent;
import uk.fstr.darwingateway.bindings.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/** JSON Serializer for SnapshotMessageComponents.
 *
 * @author George Goldberg <george@fasteroute.com>
 */
public class SnapshotMessageComponentSerializer implements JsonSerializer<SnapshotMessageComponent> {

    @Override
    public JsonElement serialize(SnapshotMessageComponent src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.addProperty("timestamp", src.getTimestamp());
        object.addProperty("version", src.getVersion());
        object.addProperty("message_type", "snapshot");

        ArrayList<String> emptyList = new ArrayList<String>();
        JsonElement emptyListElement = context.serialize(emptyList,
                new TypeToken<List<String>>(){}.getType());

        if (src.getSchedule() != null) {
            ArrayList<Schedule> schedulesList = new ArrayList<Schedule>();
            schedulesList.add(src.getSchedule());
            JsonElement scheduleMessages = context.serialize(schedulesList,
                    new TypeToken<List<Schedule>>() {
                    }.getType());
            object.add("schedule_messages", scheduleMessages);
        } else {
            object.add("schedule_messages", emptyListElement);
        }

        if (src.getAssociation() != null) {
            ArrayList<Association> assocList = new ArrayList<Association>();
            assocList.add(src.getAssociation());
            JsonElement associationMessages = context.serialize(assocList,
                    new TypeToken<List<Association>>(){}.getType());
            object.add("association_messages", associationMessages);
        } else {
            object.add("association_messages", emptyListElement);
        }

        object.add("deactivated_messages", emptyListElement);
        object.add("train_status_messages", emptyListElement);
        object.add("station_messages", emptyListElement);
        object.add("train_order_messages", emptyListElement);
        object.add("alarm_messages", emptyListElement);

        // TrainAlert and TrackingId messages are not processed as they are currently not emitted
        // by the push port at all.

        return object;
    }
}
