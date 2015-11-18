package controllers;

import play.mvc.With;
import play.mvc.Http.Header;
import resources.NavigationResource;
import controllers.hypercore.BasicController;

@With(SecurityController.class)
public class NavigationController extends BasicController {

	public static void navigate(){
		render(new NavigationResource(SecurityController.getAuthenticatedUser()));
	}
}
