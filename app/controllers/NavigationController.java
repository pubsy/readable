package controllers;

import play.mvc.With;
import resources.NavigationResource;
import controllers.hypercore.BasicController;

@With(SecurityController.class)
public class NavigationController extends BasicController {

	public static void navigate(){
		render(new NavigationResource("/", SecurityController.getAuthenticatedUser()));
	}
}
