package resources;

import hypermedia.annotations.Link;
import hypermedia.annotations.Operation;
import hypermedia.annotations.Parameter;
import hypermedia.core.Resource;
import models.User;

public class RootResource implements Resource {

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

	public RootResource(User authenticatedUser){
		if(authenticatedUser == null) {
			login = "/login";
			register = "/register";
		} else {
			myProfile = "/users/" + authenticatedUser.id;
		}
	}
}
