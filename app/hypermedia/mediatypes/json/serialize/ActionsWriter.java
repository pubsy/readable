package hypermedia.mediatypes.json.serialize;

import hypermedia.annotations.Operation;
import hypermedia.annotations.Parameter;
import hypermedia.core.Resource;
import hypermedia.mediatypes.json.ReflectionUtils;

import java.lang.reflect.Field;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

public class ActionsWriter extends AbstractWriter {

    @Override
    public void write(Resource resource, JsonObject jsonObject, JsonSerializationContext context) {
        final JsonArray jsonActionsArray = new JsonArray();

        for (Field field : ReflectionUtils.getFieldsAnnotatedWith(resource, Operation.class)) {
            Operation operation = field.getAnnotation(Operation.class);
            JsonObject operationJsonObject = new JsonObject();
            String href = (String) getFieldValue(resource, field);
            if(href != null){
            	
            	JsonArray relsArray = new JsonArray();
            	for(String rel: operation.rel().split(" ")){
            	    relsArray.add(new JsonPrimitive(rel));
            	}
            	operationJsonObject.add("rel", relsArray);

                operationJsonObject.addProperty("href", APPLICATION_URL + href);
                operationJsonObject.addProperty("method", operation.method());
                operationJsonObject.addProperty("title", operation.title());
                
                addOperationParamsIfAny(operation, operationJsonObject);
    
                jsonActionsArray.add(operationJsonObject);
            }
        }

        if (jsonActionsArray.size() != 0)
            jsonObject.add("actions", jsonActionsArray);        
    }

    private void addOperationParamsIfAny(Operation operation, JsonObject link) {
        final JsonArray paramsArray = new JsonArray();
        for (Parameter param : operation.params()) {
            JsonObject paramJsonObject = new JsonObject();
            paramJsonObject.addProperty("name", param.name());
            paramsArray.add(paramJsonObject);
        }
        if (paramsArray.size() > 0)
            link.add("fields", paramsArray);
    }

}
