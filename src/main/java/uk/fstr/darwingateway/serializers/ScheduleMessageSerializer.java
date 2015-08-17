package uk.fstr.darwingateway.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import uk.fstr.darwingateway.bindings.Schedule;

import java.lang.reflect.Type;

/**
 * Created by gberg on 17/08/15.
 */
public class ScheduleMessageSerializer implements JsonSerializer<Schedule> {

    @Override
    public JsonElement serialize(Schedule src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.addProperty("rid", src.getRid());

        return object;
    }

}
