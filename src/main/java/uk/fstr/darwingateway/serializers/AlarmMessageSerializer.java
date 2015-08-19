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
import uk.fstr.darwingateway.bindings.RTTIAlarm;

import java.lang.reflect.Type;

/** JSON Serializer for Push Port Alarm messages created from the XML bindings.
 *
 * @author George Goldberg <george@fasteroute.com>
 */
public class AlarmMessageSerializer implements JsonSerializer<RTTIAlarm> {

    @Override
    public JsonElement serialize(RTTIAlarm src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        if (src.getSet() != null) {
            object.addProperty("action", "set");
            object.addProperty("aid", src.getSet().getId());
            if (src.getSet().getTdFeedFail() != null) {
                object.addProperty("failure", "tdfeed");
            } else if (src.getSet().getTyrellFeedFail() != null){
                object.addProperty("failure", "tyrellfeed");
            } else if (src.getSet().getTdAreaFail() != null) {
                object.addProperty("failure", "tdarea");
                object.addProperty("td_area", src.getSet().getTdAreaFail());
            } else {
                object.addProperty("failure", "unknown");
            }
        } else {
            object.addProperty("action", "clear");
            object.addProperty("aid", src.getClear());
        }

        return object;
    }
}
