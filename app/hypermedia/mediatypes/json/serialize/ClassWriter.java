package hypermedia.mediatypes.json.serialize;

import hypermedia.core.Resource;

import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

public class ClassWriter extends AbstractWriter{

	@Override
	public void write(Resource resource, JsonObject jsonObject, JsonSerializationContext context) {
		jsonObject.addProperty("class", resource.getClass().getSimpleName());
	}

}
