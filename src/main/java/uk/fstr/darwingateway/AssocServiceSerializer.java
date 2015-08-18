package uk.fstr.darwingateway;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import uk.fstr.darwingateway.bindings.AssocService;
import uk.fstr.darwingateway.bindings.Association;

import java.lang.reflect.Type;

/**
 * Created by gberg on 18/08/15.
 */
public class AssocServiceSerializer implements JsonSerializer<AssocService> {

    @Override
    public JsonElement serialize(AssocService src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.addProperty("rid", src.getRid());
        object.addProperty("working_arrival_time", src.getWta());
        object.addProperty("working_pass_time", src.getWtp());
        object.addProperty("working_departure_time", src.getWtd());
        object.addProperty("public_arrival_time", src.getPta());
        object.addProperty("public_departure_time", src.getPtd());

        return object;
    }
}
