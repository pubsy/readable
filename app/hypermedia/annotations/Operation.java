package hypermedia.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Operation {

	String rel();

	String method();
	
	String title();
	
	String name();

	Parameter[] params() default {};

}
