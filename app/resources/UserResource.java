package resources;

import hypermedia.core.BasicResource;

import java.util.ArrayList;
import java.util.List;

import models.User;
import models.UserBookConnection;

public class UserResource extends BasicResource{

	public String username;
	
	public List<UserBookResource> readBooks;
	public List<UserBookResource> planningToReadBooks;

	public UserResource(User user) {
		super("/users/" + user.getId());
		this.username = user.username;
		this.readBooks = convertToResource(user.readBooks);
		this.planningToReadBooks = convertToResource(user.planningToReadBooks);
	}
	
	private List<UserBookResource> convertToResource(List<UserBookConnection> readBooks) {
		if(readBooks.isEmpty()){
			return null;
		}
		
		List<UserBookResource> list = new ArrayList<UserBookResource>(); 
		for(UserBookConnection conn: readBooks){
			list.add(new UserBookResource(conn.book));
		}
		return list;
	}

	public String toString(){
		return this.username;
	}
}
