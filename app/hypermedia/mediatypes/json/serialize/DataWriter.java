package hypermedia.mediatypes.json.serialize;

import hypermedia.core.Resource;

import java.lang.reflect.Field;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

public class DataWriter extends AbstractWriter {

	@Override
    public void write(Resource resource, JsonObject jsonObject, JsonSerializationContext context) {
        Field[] fields = resource.getClass().getFields();
        JsonObject properties = new JsonObject();
        for (Field field : fields) {
            if (isAProperty(field)) {
            	JsonElement embeddedJson = null;
                
                Object value = getFieldValue(resource, field);
                embeddedJson = context.serialize(value);
                
                properties.add(field.getName(), embeddedJson);
            }
        }
        if(properties.entrySet().size() != 0) {
        	jsonObject.add("properties", properties);
        }
    }

	private boolean isAProperty(Field field) {
		return field.getAnnotations().length == 0;
	}

}
