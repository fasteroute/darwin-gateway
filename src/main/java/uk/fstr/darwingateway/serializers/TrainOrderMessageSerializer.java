package uk.fstr.darwingateway.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import uk.fstr.darwingateway.bindings.Schedule;
import uk.fstr.darwingateway.bindings.TrainOrder;

import java.lang.reflect.Type;

/**
 * Created by gberg on 17/08/15.
 */
public class TrainOrderMessageSerializer implements JsonSerializer<TrainOrder> {

    @Override
    public JsonElement serialize(TrainOrder src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.addProperty("tiploc", src.getTiploc());

        return object;
    }
}
