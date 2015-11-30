package controllers;

import hypermedia.core.Resource;

import java.util.ArrayList;
import java.util.List;

import play.mvc.With;
import play.mvc.Http.Header;
import models.User;
import resources.BooksListResource;
import resources.UserResource;
import resources.UsersListResource;
import controllers.hypercore.BasicController;

@With(SecurityController.class)
public class UsersController extends BasicController {

	/*
	 * Controller methods
	 */

	public static void users(Integer page, Integer size){
	    UsersListResource usersListRes = new UsersListResource();
	    usersListRes.withItems(getUsers(page, size));
	    usersListRes.withPage(page);
	    usersListRes.withTotalElements(getTotalUsersCount());
	    usersListRes.withSize(size);
	    usersListRes.build();
	    
		render(usersListRes);
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
	
	public static void myProfile(){
	    render(new UserResource(SecurityController.getAuthenticatedUser()));
	}
	
	/*
	 * Utility methods
	 */
	
	private static Resource getUser(long id) {
		User user = (User)User.findById(id);
		return new UserResource(user);
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
