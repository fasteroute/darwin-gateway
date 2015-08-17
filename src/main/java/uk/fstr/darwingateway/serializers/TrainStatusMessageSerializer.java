package uk.fstr.darwingateway.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import uk.fstr.darwingateway.bindings.DisruptionReasonType;
import uk.fstr.darwingateway.bindings.TS;
import uk.fstr.darwingateway.bindings.TSLocation;

import java.lang.reflect.Type;
import java.util.List;

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

        object.add("late_reason", context.serialize(src.getLateReason()));

        JsonElement locations = context.serialize(src.getLocation(),
                new TypeToken<List<TSLocation>>(){}.getType());
        object.add("locations", locations);

        return object;
    }
}
