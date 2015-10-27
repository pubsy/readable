package resources;

import java.util.List;

import hypermedia.annotations.Link;
import hypermedia.annotations.Operation;
import hypermedia.annotations.Parameter;
import hypermedia.core.PagedListResource;

public class UsersListResource extends PagedListResource<UserResource>{

	private static final String BASE_URL = "/users";
	
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
	
	public UsersListResource(List<UserResource> items, Long totalElements, Integer size, Integer page) {
		super(items, BASE_URL, totalElements, size, page);
	}

}
