package uk.fstr.darwingateway.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import uk.fstr.darwingateway.bindings.PlatformData;

import java.lang.reflect.Type;

/**
 * Created by gberg on 17/08/15.
 */
public class PlatformDataSerializer implements JsonSerializer<PlatformData> {

    @Override
    public JsonElement serialize(PlatformData src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.addProperty("number", src.getValue());
        object.addProperty("suppressed", src.isPlatsup());
        object.addProperty("suppressed_by_cis", src.isCisPlatsup());
        object.addProperty("source", src.getPlatsrc());
        object.addProperty("confirmed", src.isConf());

        return object;
    }
}
