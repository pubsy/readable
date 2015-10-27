package controllers;

import hypermedia.core.Resource;

import java.util.ArrayList;
import java.util.List;

import models.User;
import resources.BooksListResource;
import resources.UserResource;
import resources.UsersListResource;
import controllers.hypercore.BasicController;

public class UsersController extends BasicController {

	/*
	 * Controller methods
	 */

	public static void users(Integer page, Integer size){
		render(new UsersListResource(getUsers(page, size), getTotalUsersCount(), size, page));
	}

	public static void user(long id){
		render(getUser(id));
	}

	public static void createUser(String name, String password) {
		User user  = new User(name).save();
		response.status = 201;
		response.setHeader("Location", "/users/" + user.id);
		render(getUser(user.id));
	}
	
	/*
	 * Utility methods
	 */
	
	private static Resource getUser(long id) {
		return new UserResource((User)User.findById(id));
	}
	
	private static List<UserResource> getUsers(Integer page, Integer size) {
    	size = (size == null) ? 3 : size;
    	page = (page == null) ? 0 : page;
    	
		List<User> users = User.find("order by insertedAt asc").fetch(page + 1, size);
		
		List<UserResource> resources = new ArrayList<UserResource>();
		for(User user: users){
			resources.add(new UserResource(user));
		}
		return resources;
	}
	
	private static Long getTotalUsersCount() {
		return User.count();
	}
}
