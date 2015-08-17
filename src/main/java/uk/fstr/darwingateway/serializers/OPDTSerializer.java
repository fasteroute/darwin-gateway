package uk.fstr.darwingateway.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import uk.fstr.darwingateway.bindings.OPDT;

import java.lang.reflect.Type;

/**
 * Created by gberg on 17/08/15.
 */
public class OPDTSerializer implements JsonSerializer<OPDT> {

    @Override
    public JsonElement serialize(OPDT src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.addProperty("location_type", "OPDT");
        object.addProperty("working_arrival_time", src.getWta());
        object.addProperty("working_departure_time", src.getWtd());
        object.addProperty("tiploc", src.getTpl());
        object.addProperty("activities", src.getAct());
        object.addProperty("planned_activities", src.getPlanAct());
        object.addProperty("cancelled", src.isCan());
        object.addProperty("route_delay", src.getRdelay());

        return object;
    }
}