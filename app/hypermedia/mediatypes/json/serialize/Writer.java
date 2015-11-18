package hypermedia.mediatypes.json.serialize;

import hypermedia.core.Resource;

import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

public interface Writer {

    public void write(Resource resource, JsonObject jsonObject, JsonSerializationContext context);
}
