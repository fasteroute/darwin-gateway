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
import uk.fstr.darwingateway.bindings.Association;

import java.lang.reflect.Type;

/** JSON Serializer for Push Port Association messages created from the XML bindings.
 *
 * @author George Goldberg <george@fasteroute.com>
 */
public class AssociationMessageSerializer implements JsonSerializer<Association> {

    @Override
    public JsonElement serialize(Association src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.addProperty("tiploc", src.getTiploc());
        object.addProperty("category", src.getCategory().value());
        object.addProperty("deleted", src.isIsDeleted());
        object.addProperty("cancelled", src.isIsCancelled());

        object.add("main_service", context.serialize(src.getMain()));
        object.add("associated_service", context.serialize(src.getAssoc()));

        return object;
    }
}
