package uk.fstr.darwingateway.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import uk.fstr.darwingateway.bindings.PP;

import java.lang.reflect.Type;

/**
 * Created by gberg on 17/08/15.
 */
public class PPSerializer implements JsonSerializer<PP> {

    @Override
    public JsonElement serialize(PP src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.addProperty("location_type", "PP");
        object.addProperty("working_pass_time", src.getWtp());
        object.addProperty("route_delay", src.getRdelay());
        object.addProperty("tiploc", src.getTpl());
        object.addProperty("activities", src.getAct());
        object.addProperty("planned_activities", src.getPlanAct());
        object.addProperty("cancelled", src.isCan());

        return object;
    }
}
