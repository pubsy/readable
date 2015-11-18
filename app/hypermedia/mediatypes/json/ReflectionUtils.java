package hypermedia.mediatypes.json;

import hypermedia.core.Resource;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import play.Logger;

public class ReflectionUtils {

    public static Field[] getFieldsAnnotatedWith(Resource resource, Class annotationClass) {
        Set<Field> fields = new HashSet<Field>();
        for (Field field : resource.getClass().getFields()) {
            Annotation annotation = field.getAnnotation(annotationClass);
            if (annotation != null) {
                fields.add(field);
            }
        }
        return fields.toArray(new Field[fields.size()]);
    }
    
    public static Object getFieldValue(Resource resource, Field field) {
        Object value = null;
        try {
            value = field.get(resource);
        } catch (IllegalArgumentException e) {
            Logger.error(e, "Could not get filed value");
        } catch (IllegalAccessException e) {
            Logger.error(e, "Could not get filed value");
        }
        return value;
    }
}
