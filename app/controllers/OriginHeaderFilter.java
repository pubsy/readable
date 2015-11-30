package controllers;

import org.apache.commons.lang.ArrayUtils;
import org.bouncycastle.util.Arrays;

import play.Play;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Http.Header;

public class OriginHeaderFilter extends Controller {

	private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
	private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	private static final String SEPARATOR = ",";
	private static final String ALLOWED_ORIGINS_PROP_NAME = "allowed.origins";
	private static final String ORIGIN_HEADER_NAME = "origin";

	private static final String[] allowedOrigins = Play.configuration.getProperty(ALLOWED_ORIGINS_PROP_NAME).split(SEPARATOR);

	@Before
	public static void intercept() {
		Header originHeader = request.headers.get(ORIGIN_HEADER_NAME);
		if(originHeader == null){
			return;
		}
		
		String origin = originHeader.value();
		if (ArrayUtils.contains(allowedOrigins, origin)) {
			addOriginHeader(origin);
		}
	}
	
	public static void options(){
		ok();
	}
	
	private static void addOriginHeader(String domain) {
		response.headers.put(ACCESS_CONTROL_ALLOW_ORIGIN, new Header(ACCESS_CONTROL_ALLOW_ORIGIN, domain));
		response.headers.put(ACCESS_CONTROL_ALLOW_HEADERS, new Header(ACCESS_CONTROL_ALLOW_HEADERS, "Authorization"));
	}
	
}
