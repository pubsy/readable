package resources;

import models.User;
import hypermedia.annotations.Link;
import hypermedia.annotations.Operation;
import hypermedia.annotations.Parameter;
import hypermedia.core.BasicResource;

public final class NavigationResource extends BasicResource {

	@Operation(rel = "register", method = "POST", params = { @Parameter(name = "name"), @Parameter(name = "password") })
	public String register;

	@Link(rel = "books")
	public String books = "/books";

	@Link(rel = "users")
	public String users = "/users";
	
	@Link(rel = "my-profile")
	public String myProfile;
	
	@Link(rel = "login")
	public String login;
	
	public NavigationResource(String self, User authenticatedUser) {
		super(self);
		if(authenticatedUser == null) {
			login = "/login";
			register = "/register";
		} else {
			myProfile = "/users/" + authenticatedUser.id;
		}
	}

}
