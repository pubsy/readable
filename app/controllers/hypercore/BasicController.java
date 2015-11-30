package controllers.hypercore;

import models.User;

import com.google.gson.GsonBuilder;

import controllers.SecurityController;
import hypermedia.core.Resource;
import hypermedia.mediatypes.json.serialize.ResourceSerializer;
import hypermedia.mediatypes.xhtml.XhtmlResourceAdapter;
import play.Play;
import play.mvc.Controller;

public class BasicController extends Controller {
	
	protected static void render(Resource resource) {
		String acceptsHeader = request.headers.get("accept").value();
		if("application/vnd.siren+json".equals(acceptsHeader)) {
			renderSiren(resource);
    	} else {
    		renderXHTML(new XhtmlResourceAdapter(resource));
    	}
	}

	private static void renderXHTML(XhtmlResourceAdapter resource) {
		renderTemplate("hypercore/BasicController/template.html", resource);
	}

	private static void renderSiren(Resource resource) {
		GsonBuilder gson = new GsonBuilder();
		gson.registerTypeHierarchyAdapter(Resource.class, new ResourceSerializer());
		renderJSON(gson.create().toJson(resource));
	}
			
}
