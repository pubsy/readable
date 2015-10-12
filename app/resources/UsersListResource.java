package resources;

import java.util.List;

import hypermedia.core.PagedListResource;

public class UsersListResource extends PagedListResource<UserResource>{

	public UsersListResource(List<UserResource> items, Long totalElements, Integer size, Integer page) {
		super(items, "/users", totalElements, size, page);
	}

}
