package resources;

import hypermedia.annotations.Link;
import hypermedia.annotations.Operation;
import hypermedia.annotations.Parameter;
import hypermedia.core.PagedListResource;

import java.util.List;

import models.User;

public class UsersListResource extends PagedListResource<UserResource>{

	private static final String BASE_URL = "/users";
	
	public UsersListResource(List<UserResource> items, Long totalElements, Integer size, Integer page) {
		super(items, BASE_URL, totalElements, size, page);
	}

}
