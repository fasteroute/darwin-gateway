package uk.fstr.darwingateway.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import uk.fstr.darwingateway.bindings.DisruptionReasonType;

import java.lang.reflect.Type;

/**
 * Created by gberg on 17/08/15.
 */
public class DisruptionReasonTypeSerializer implements JsonSerializer<DisruptionReasonType> {

    @Override
    public JsonElement serialize(DisruptionReasonType src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.addProperty("code", src.getValue());
        object.addProperty("tiploc", src.getTiploc());
        object.addProperty("near", src.isNear());

        return object;
    }
}
