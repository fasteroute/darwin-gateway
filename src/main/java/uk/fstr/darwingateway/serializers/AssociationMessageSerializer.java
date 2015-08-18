package uk.fstr.darwingateway.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import uk.fstr.darwingateway.bindings.Association;
import uk.fstr.darwingateway.bindings.Schedule;

import java.lang.reflect.Type;

/**
 * Created by gberg on 17/08/15.
 */
public class AssociationMessageSerializer implements JsonSerializer<Association> {

    @Override
    public JsonElement serialize(Association src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.addProperty("tiploc", src.getTiploc());
        object.addProperty("category", src.getCategory().value());
        object.addProperty("deleted", src.isIsDeleted());
        object.addProperty("cancelled", src.isIsCancelled());

        object.add("main_service", context.serialize(src.getMain()));
        object.add("associated_service", context.serialize(src.getAssoc()));

        return object;
    }
}
