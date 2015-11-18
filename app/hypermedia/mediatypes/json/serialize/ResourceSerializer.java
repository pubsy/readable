package hypermedia.mediatypes.json.serialize;

import hypermedia.core.Resource;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ResourceSerializer implements JsonSerializer<Resource> {

    private final List<Writer> writers = new ArrayList<Writer>();
    
    //We should have some DI here
    public ResourceSerializer() {
    	writers.add(new ClassWriter());
        writers.add(new DataWriter());
        writers.add(new LinksWriter());
        writers.add(new ActionsWriter());
    }

	@Override
	public JsonElement serialize(Resource resource, Type type, JsonSerializationContext context) {
		final JsonObject jsonObject = new JsonObject();

		for(Writer writer: writers) {
		    writer.write(resource, jsonObject, context);
		}

		return jsonObject;
	}

}