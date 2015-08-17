package uk.fstr.darwingateway.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import uk.fstr.darwingateway.bindings.RTTIAlarm;

import java.lang.reflect.Type;

/**
 * Created by gberg on 17/08/15.
 */
public class AlarmMessageSerializer implements JsonSerializer<RTTIAlarm> {

    @Override
    public JsonElement serialize(RTTIAlarm src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.addProperty("aid", src.getClear());

        return object;
    }
}
