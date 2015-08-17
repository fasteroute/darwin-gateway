package uk.fstr.darwingateway.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import uk.fstr.darwingateway.bindings.Schedule;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by gberg on 17/08/15.
 */
public class ScheduleMessageSerializer implements JsonSerializer<Schedule> {

    @Override
    public JsonElement serialize(Schedule src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.addProperty("rid", src.getRid());
        object.addProperty("uid", src.getUid());
        object.addProperty("toc_code", src.getToc());
        object.addProperty("status", src.getStatus());
        object.addProperty("category", src.getTrainCat());
        object.addProperty("headcode", src.getTrainId());
        object.addProperty("start_date", src.getSsd().toString());
        object.addProperty("deleted", src.isDeleted());
        object.addProperty("charter", src.isIsCharter());
        object.addProperty("active", src.isIsActive());
        object.addProperty("passenger_service", src.isIsPassengerSvc());
        object.add("cancellation_reason", context.serialize(src.getCancelReason()));

        JsonElement locations = context.serialize(src.getOROrOPOROrIP(),
                new TypeToken<List<Object>>(){}.getType());
        object.add("locations", locations);

        return object;
    }

}
