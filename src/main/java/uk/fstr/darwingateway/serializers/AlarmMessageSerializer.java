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

        if (src.getSet() != null) {
            object.addProperty("action", "set");
            object.addProperty("aid", src.getSet().getId());
            if (src.getSet().getTdFeedFail() != null) {
                object.addProperty("failure", "tdfeed");
            } else if (src.getSet().getTyrellFeedFail() != null){
                object.addProperty("failure", "tyrellfeed");
            } else if (src.getSet().getTdAreaFail() != null) {
                object.addProperty("failure", "tdarea");
                object.addProperty("td_area", src.getSet().getTdAreaFail());
            } else {
                object.addProperty("failure", "unknown");
            }
        } else {
            object.addProperty("action", "clear");
            object.addProperty("aid", src.getClear());
        }

        return object;
    }
}
