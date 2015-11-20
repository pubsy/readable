package controllers;

import org.apache.commons.lang.ArrayUtils;
import org.bouncycastle.util.Arrays;

import play.Play;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Http.Header;

public class BasicInterceptor extends Controller {

	private static final String SEPARATOR = ",";
	private static final String ALLOWED_ORIGINS_PROP_NAME = "allowed.origins";
	private static final String ACCESS_CONTROL_HEADER_NAME = "Access-Control-Allow-Origin";
	private static final String ORIGIN_HEADER_NAME = "Origin";

	private static final String[] allowedOrigins = Play.configuration.getProperty(ALLOWED_ORIGINS_PROP_NAME).split(SEPARATOR);

	@Before
	public static void intercept() {
		String origin = request.headers.get(ORIGIN_HEADER_NAME).value();
		if (ArrayUtils.contains(allowedOrigins, origin)) {
			addHeader(origin);
		}
	}

	private static void addHeader(String domain) {
		response.headers.put(ACCESS_CONTROL_HEADER_NAME, new Header(ACCESS_CONTROL_HEADER_NAME, domain));
	}
}
