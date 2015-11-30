package hypermedia.mediatypes.xhtml;

import hypermedia.annotations.Link;
import hypermedia.annotations.Operation;
import hypermedia.annotations.Parameter;
import hypermedia.core.Resource;
import hypermedia.mediatypes.json.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;

import play.Play;
import resources.ReadableBasicResource;
import resources.PagedListResource;

public class XhtmlResourceAdapter {

	public List<LinkAdapter> links = new ArrayList<LinkAdapter>();
	public List<OperationAdapter> operations = new ArrayList<OperationAdapter>();
	public List<PropertyAdapter> properties = new ArrayList<PropertyAdapter>();
	public List<ListPropertyAdapter> collections = new ArrayList<ListPropertyAdapter>();

	public XhtmlResourceAdapter(Resource resource) {
		populateLinks(resource);
		populateOperartions(resource);
		populateFields(resource);
	}

	private void populateFields(Resource resource) {
		Field[] fields = resource.getClass().getFields();
		for (Field field : fields) {
			if (isCollectionProperty(field) && isAProperty(field)) {
				List<LinkAdapter> items = new ArrayList<LinkAdapter>();
				
				try {
					Collection<ReadableBasicResource> collection = (Collection) field.get(resource);
					if(collection == null){
						continue;
					}
					Iterator<ReadableBasicResource> i = collection.iterator();
					while(i.hasNext()){
						ReadableBasicResource next = i.next();
						items.add(new LinkAdapter(next.toString(), next.self, next.toString()));
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				
				collections.add(new ListPropertyAdapter(field.getName(), items ));				
			} else if (isAProperty(field)) {
				Object value = ReflectionUtils.getFieldValue(resource, field);
				properties.add(new PropertyAdapter(field.getName(), value));
			}
		}
	}

	private boolean isCollectionProperty(Field field) {
		return List.class.isAssignableFrom(field.getType());
	}

	private boolean isAProperty(Field field) {
		return field.getAnnotations().length == 0;
	}

	private void populateOperartions(Resource resource) {
		Field[] fields = ReflectionUtils.getFieldsAnnotatedWith(resource, Operation.class);
		for (Field field : fields) {
			Operation operation = field.getAnnotation(Operation.class);
			String uri = (String) ReflectionUtils.getFieldValue(resource, field);
			if(uri != null){
				this.operations.add(new OperationAdapter(operation, uri));
			}
		}
	}

	private void populateLinks(Resource resource) {
		Field[] fields = ReflectionUtils.getFieldsAnnotatedWith(resource, Link.class);
		for (Field field : fields) {
			Link link = field.getAnnotation(Link.class);
			String uri = (String) ReflectionUtils.getFieldValue(resource, field);
			if(uri != null){
				links.add(new LinkAdapter(link.rel(), uri, link.title()));
			}
		}
	}
	
	class ListPropertyAdapter {
		public String name;
		public List<LinkAdapter> items;
		
		public ListPropertyAdapter(String name, List<LinkAdapter> items){
			this.name = name;
			this.items = items;
		}
	}

	class LinkAdapter {

		public String uri;
		public String rel;
        public String title;

		public LinkAdapter(String rel, String uri, String title) {
			this.uri = uri;
			this.rel = rel;
			this.title = title;
		}
	}
	
	class OperationAdapter extends LinkAdapter {

		public String method;
		public List<String> params = new ArrayList<String>();
		
		public OperationAdapter(Operation operation, String uri) {
			super(operation.rel(), uri, operation.title());
			this.method = operation.method();
			for(Parameter param: operation.params()){
				this.params.add(param.name());
			}
			
		}
	}
	
	class PropertyAdapter {
		public String name;
		public String value;
		
		public PropertyAdapter(String name, Object value2){
			this.name = name;
			this.value = value2 == null ? "" : value2.toString();
		}
	}
}
