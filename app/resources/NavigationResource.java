package resources;

import hypermedia.annotations.Link;
import hypermedia.annotations.Operation;
import hypermedia.annotations.Parameter;
import hypermedia.core.Resource;
import models.User;

public final class NavigationResource implements Resource {

	@Operation(rel = "register", name = "register", method = "POST", title="Register", params = { @Parameter(name = "name"), @Parameter(name = "password") })
	public String register;

	@Link(rel = "Books")
	public String books = "/books";

	@Link(rel = "Users")
	public String users = "/users";
	
	@Link(rel = "My Profile")
	public String myProfile;
	
	public NavigationResource(User authenticatedUser) {
		if(authenticatedUser == null) {
			register = "/register";
		} else {
			myProfile = "/users/" + authenticatedUser.id;
		}
	}

}
