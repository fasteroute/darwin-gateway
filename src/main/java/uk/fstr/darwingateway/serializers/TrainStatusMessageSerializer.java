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
import uk.fstr.darwingateway.bindings.TS;
import uk.fstr.darwingateway.bindings.TSLocation;

import java.lang.reflect.Type;
import java.util.List;

/** JSON Serializer for Push Port Train Status messages created from the XML bindings.
 *
 * @author George Goldberg <george@fasteroute.com>
 */
public class TrainStatusMessageSerializer implements JsonSerializer<TS> {

    @Override
    public JsonElement serialize(TS src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.addProperty("rid", src.getRid());
        object.addProperty("uid", src.getUid());
        object.addProperty("reverse_formation", src.isIsReverseFormation());
        object.addProperty("start_date", src.getSsd().toString());

        object.add("late_reason", context.serialize(src.getLateReason()));

        JsonElement locations = context.serialize(src.getLocation(),
                new TypeToken<List<TSLocation>>(){}.getType());
        object.add("locations", locations);

        return object;
    }
}
