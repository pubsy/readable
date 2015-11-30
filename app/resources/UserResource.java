package resources;

import java.util.ArrayList;
import java.util.List;

import models.User;
import models.UserBookConnection;

public class UserResource extends ReadableBasicResource{

	public String username;
	
	public List<BookResource> readBooks;
	public List<BookResource> planningToReadBooks;

	public UserResource(User user) {
		setSelf("/users/" + user.getId());
		
		this.username = user.username;
		this.readBooks = convertToResource(user.readBooks);
		this.planningToReadBooks = convertToResource(user.planningToReadBooks);
	}
	
	private List<BookResource> convertToResource(List<UserBookConnection> readBooks) {
		if(readBooks.isEmpty()){
			return null;
		}
		
		List<BookResource> list = new ArrayList<BookResource>(); 
		for(UserBookConnection conn: readBooks){
			list.add(new BookResource(conn.book));
		}
		return list;
	}

	public String toString(){
		return this.username;
	}
}
