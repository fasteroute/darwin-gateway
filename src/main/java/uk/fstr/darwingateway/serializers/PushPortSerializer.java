package uk.fstr.darwingateway.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import uk.fstr.darwingateway.bindings.*;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by gberg on 17/08/15.
 */
public class PushPortSerializer implements JsonSerializer<Pport> {

    @Override
    public JsonElement serialize(Pport src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.addProperty("timestamp", src.getTs().toString());
        object.addProperty("version", src.getVersion());
        object.addProperty("update_origin", src.getUR().getUpdateOrigin());

        Pport.UR r = src.getUR();

        JsonElement scheduleMessages = context.serialize(r.getSchedule(),
                new TypeToken<List<Schedule>>() {
                }.getType());
        object.add("schedule_messages", scheduleMessages);

        JsonElement associationMessages = context.serialize(r.getAssociation(),
                new TypeToken<List<Association>>() {
                }.getType());
        object.add("association_messages", associationMessages);

        JsonElement deactivatedMessages = context.serialize(r.getDeactivated(),
                new TypeToken<List<DeactivatedSchedule>>(){}.getType());
        object.add("deactivated_messages", deactivatedMessages);

        JsonElement trainStatusMessages = context.serialize(r.getTS(),
                new TypeToken<List<TS>>(){}.getType());
        object.add("train_status_messages", trainStatusMessages);

        JsonElement stationMessages = context.serialize(r.getOW(),
                new TypeToken<List<StationMessage>>(){}.getType());
        object.add("station_messages", stationMessages);

        JsonElement trainOrderMessages = context.serialize(r.getTrainOrder(),
                new TypeToken<List<TrainOrder>>(){}.getType());
        object.add("train_order_messages", trainOrderMessages);

        JsonElement alarmMessages = context.serialize(r.getAlarm(),
                new TypeToken<List<RTTIAlarm>>(){}.getType());
        object.add("alarm_messages", alarmMessages);

        // TrainAlert and TrackingId messages are not processed as they are currently not emitted
        // by the push port at all.

        return object;
    }
}
