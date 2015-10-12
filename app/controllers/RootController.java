package controllers;

import play.mvc.With;
import resources.RootResource;
import controllers.SecurityController.Secured;
import controllers.hypercore.BasicController;

@With(SecurityController.class)
public class RootController extends BasicController {

    public static void root() {
    	render(new RootResource(SecurityController.getAuthenticatedUser()));
    }

    @Secured
    public static void login(){
    	renderText("You are successfully authenticated.");
    }
}