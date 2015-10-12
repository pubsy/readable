package controllers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import models.User;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Util;
import play.mvc.Http.Header;

public class SecurityController extends Controller {

	@Before
	public static void checkAuthentication(){
		Secured secured = getActionAnnotation(Secured.class);
		if (secured != null) {
			if(getAuthUserName() == null){
				unauthorized();
			}
		}
	}

	@Util
	public static User getAuthenticatedUser() {
		if(!isAuthenticated()){
			return null;
		}
		return User.findByUserName(getAuthUserName());
	}

	@Util
	public static boolean isAuthenticated() {
		return getAuthUserName() != null;
	}

	private static String getAuthUserName() {
		return request.user;
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD, ElementType.TYPE})
	public @interface Secured {
	}

}
