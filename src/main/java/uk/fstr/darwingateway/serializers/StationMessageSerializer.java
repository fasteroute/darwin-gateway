package uk.fstr.darwingateway.serializers;

import com.google.gson.*;
import uk.fstr.darwingateway.bindings.A;
import uk.fstr.darwingateway.bindings.P;
import uk.fstr.darwingateway.bindings.StationMessage;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by gberg on 17/08/15.
 */
public class StationMessageSerializer implements JsonSerializer<StationMessage> {

    @Override
    public JsonElement serialize(StationMessage src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.addProperty("smid", src.getId());
        object.addProperty("category", src.getCat().value());
        object.addProperty("severity", src.getSev());
        object.addProperty("suppressed", src.isSuppress());

        JsonArray stations = new JsonArray();
        for (StationMessage.Station s: src.getStation()) {
            JsonObject o = new JsonObject();
            o.addProperty("crs", s.getCrs());
            stations.add(o);
        }
        object.add("stations", stations);

        List<Object> messageContents = src.getMsg().getContent();
        StringBuilder mb = new StringBuilder();
        for (Object o: messageContents) {
            if (o.getClass() == String.class) {
                mb.append((String) o);
            } else if (o.getClass() == P.class) {
                for (Object p: ((P) o).getContent()) {
                    if (p.getClass() == String.class) {
                        mb.append((String) p);
                    } else if (p.getClass() == A.class) {
                        mb.append("<a href=")
                                .append('"')
                                .append(((A) p).getHref())
                                .append('"')
                                .append(">")
                                .append(((A) p).getValue())
                                .append("</a>");
                    }
                }
            } else if (o.getClass() == A.class) {
                mb.append("<a href=")
                        .append('"')
                        .append(((A) o).getHref())
                        .append('"')
                        .append(">")
                        .append(((A) o).getValue())
                        .append("</a>");
            }
        }
        object.addProperty("message", mb.toString());

        return object;
    }
}
