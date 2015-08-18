package uk.fstr.darwingateway.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import uk.fstr.darwingateway.bindings.TrainOrderItem;

import java.lang.reflect.Type;

/**
 * Created by gberg on 18/08/15.
 */
public class TrainOrderItemSerializer implements JsonSerializer<TrainOrderItem> {

    @Override
    public JsonElement serialize(TrainOrderItem src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.addProperty("rid", src.getRid().getValue());
        object.addProperty("headcode", src.getTrainID());
        object.addProperty("working_arrival_time", src.getRid().getWta());
        object.addProperty("working_pass_time", src.getRid().getWtp());
        object.addProperty("working_departure_time", src.getRid().getWtd());
        object.addProperty("public_arrival_time", src.getRid().getPta());
        object.addProperty("public_departure_time", src.getRid().getPtd());

        return object;
    }
}
