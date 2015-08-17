package uk.fstr.darwingateway.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import uk.fstr.darwingateway.bindings.TSLocation;

import java.lang.reflect.Type;

/**
 * Created by gberg on 17/08/15.
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
