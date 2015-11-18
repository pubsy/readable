package hypermedia.mediatypes.json.deserialize;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.Set;

import javax.activation.FileDataSource;

import hypermedia.annotations.Link;
import hypermedia.annotations.Operation;
import hypermedia.core.Resource;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializer;

public class ResourceDeserializer implements JsonDeserializer<Resource> {

	@Override
	public Resource deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
		Resource resource = createResourceInstance(type);
		
		for(Field field: resource.getClass().getFields()){
			if (field.isAnnotationPresent(Link.class)){
				deserializeLink(resource, field, element);
			} else if(field.isAnnotationPresent(Operation.class)){
				deserializeOperation(resource, field, element);
			} else{
				deserializeData(resource, field, element, context);
			}
		}

		return resource;
	}

	private void deserializeData(Resource resource, Field field, JsonElement element, JsonDeserializationContext context) {
		JsonElement dataFiled = element.getAsJsonObject().get(field.getName());
		try {
			Object deserialize = context.deserialize(dataFiled, field.getGenericType());
			field.set(resource, deserialize);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		}
	}

	private void deserializeOperation(Resource resource, Field field, JsonElement element) {
		JsonElement operations = element.getAsJsonObject().get("_operations");
		if(operations == null) {
			return;
		}
		
		Set<Entry<String, JsonElement>> set = operations.getAsJsonObject().entrySet();
		Iterator<Entry<String, JsonElement>> iterator = set.iterator();
		while(iterator.hasNext()){
			Entry<String, JsonElement> entry = iterator.next();
			
			if(entry.getKey().equals(field.getAnnotationsByType(Operation.class)[0].rel())){
				try {
					field.set(resource, entry.getValue().getAsJsonObject().get("href").getAsString());
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				return;
			}
		}
	}

	private void deserializeLink(Resource resource, Field field, JsonElement element) {
		JsonElement links = element.getAsJsonObject().get("_links");
		if(links == null) {
			return;
		}
		
		Set<Entry<String, JsonElement>> set = links.getAsJsonObject().entrySet();
		Iterator<Entry<String, JsonElement>> iterator = set.iterator();
		while(iterator.hasNext()){
			Entry<String, JsonElement> entry = iterator.next();
			
			if(entry.getKey().equals(field.getAnnotationsByType(Link.class)[0].rel())){
				try {
					field.set(resource, entry.getValue().getAsString());
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				return;
			}
		}
	}

	private Resource createResourceInstance(Type type) {
		Resource resource = null;
		try {
			Class clazz = Class.forName(type.getTypeName());
			resource = (Resource) clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return resource;
	}

}
