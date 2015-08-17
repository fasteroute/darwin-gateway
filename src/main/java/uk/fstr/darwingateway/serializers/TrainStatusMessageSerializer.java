package uk.fstr.darwingateway.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import uk.fstr.darwingateway.bindings.TS;

import java.lang.reflect.Type;

/**
 * Created by gberg on 17/08/15.
 */
public class TrainStatusMessageSerializer implements JsonSerializer<TS> {

    @Override
    public JsonElement serialize(TS src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.addProperty("rid", src.getRid());
        object.addProperty("uid", src.getUid());
        object.addProperty("reverse_formation", src.isIsReverseFormation());
        object.addProperty("start_date", src.getSsd().toString());

        return object;
    }
}
