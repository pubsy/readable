package resources;

import hypermedia.annotations.Link;
import hypermedia.annotations.Operation;
import hypermedia.annotations.Parameter;

import java.util.List;

import models.User;

public class UsersListResource extends PagedListResource<UserResource>{

	private static final String BASE_URL = "/users";
	
	public UsersListResource() {
		withRootUrl(BASE_URL);
	}

}
