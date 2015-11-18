package hypermedia.mediatypes.json.serialize;

import hypermedia.annotations.Link;
import hypermedia.core.Resource;
import hypermedia.mediatypes.json.ReflectionUtils;

import java.lang.reflect.Field;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

public class LinksWriter extends AbstractWriter {

    @Override
    public void write(Resource resource, JsonObject jsonObject, JsonSerializationContext context) {
        final JsonArray jsonLinksArray = new JsonArray();
        Field[] fields = ReflectionUtils.getFieldsAnnotatedWith(resource, Link.class);
        for (Field field : fields) {
            Link link = field.getAnnotation(Link.class);
            String href = (String) getFieldValue(resource, field);
            if(href != null){
            	JsonObject jsonLink = new JsonObject();
            	
            	JsonArray relsArray = new JsonArray();
            	relsArray.add(new JsonPrimitive(link.rel()));
            	jsonLink.add("rel", relsArray);
            	
            	jsonLink.addProperty("href", APPLICATION_URL + href);
            	jsonLinksArray.add(jsonLink);
            }
        }
        if (jsonLinksArray.size() != 0)
            jsonObject.add("links", jsonLinksArray);
    }

}
