package uk.fstr.darwingateway.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import uk.fstr.darwingateway.bindings.TSTimeData;

import java.lang.reflect.Type;

/**
 * Created by gberg on 17/08/15.
 */
public class TSTimeDataSerializer implements JsonSerializer<TSTimeData> {

    @Override
    public JsonElement serialize(TSTimeData src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.addProperty("actual_time", src.getAt());
        object.addProperty("estimated_time", src.getEt());
        object.addProperty("working_estimated_time", src.getWet());
        object.addProperty("actual_time_removed", src.isAtRemoved());
        object.addProperty("manual_estimate_lower_limit_minutes", src.getEtmin());
        object.addProperty("manual_estimate_unknown_delay", src.isEtUnknown());
        object.addProperty("unknown_delay", src.isDelayed());
        object.addProperty("source", src.getSrc());
        object.addProperty("source_cis", src.getSrcInst());

        return object;
    }
}
