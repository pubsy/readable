package hypermedia.mediatypes.json.serialize;

import hypermedia.core.Resource;
import hypermedia.mediatypes.json.ReflectionUtils;

import java.lang.reflect.Field;

import play.Logger;
import play.Play;

import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

public abstract class AbstractWriter implements Writer {
	
	public static final String APPLICATION_URL = Play.configuration.getProperty("application.baseUrl") ;

    public abstract void write(Resource resource, JsonObject jsonObject, JsonSerializationContext context);

    protected Object getFieldValue(Resource resource, Field field) {
        return ReflectionUtils.getFieldValue(resource, field);
    }
}
