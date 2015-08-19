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
import uk.fstr.darwingateway.bindings.TSLocation;

import java.lang.reflect.Type;

/** JSON Serializer for Push Port Train Status message Locations created from the XML bindings.
 *
 * @author George Goldberg <george@fasteroute.com>
 */
public class TSLocationSerializer implements JsonSerializer<TSLocation> {

    @Override
    public JsonElement serialize(TSLocation src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.addProperty("length", src.getLength());
        object.addProperty("public_arrival_time", src.getPta());
        object.addProperty("public_departure_time", src.getPtd());
        object.addProperty("working_arrival_time", src.getWta());
        object.addProperty("working_departure_time", src.getWtd());
        object.addProperty("working_pass_time", src.getWtp());
        object.addProperty("tiploc", src.getTpl());
        object.addProperty("suppressed", src.isSuppr());
        object.addProperty("detach_front", src.isDetachFront());

        object.add("arrival", context.serialize(src.getArr()));
        object.add("pass", context.serialize(src.getPass()));
        object.add("departure", context.serialize(src.getDep()));
        object.add("platform", context.serialize(src.getPlat()));

        return object;
    }
}
