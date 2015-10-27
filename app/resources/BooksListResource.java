package resources;

import hypermedia.annotations.Link;
import hypermedia.annotations.Operation;
import hypermedia.annotations.Parameter;
import hypermedia.core.PagedListResource;
import hypermedia.core.Resource;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import controllers.SecurityController;

public class BooksListResource extends PagedListResource<BookResource> {
	
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
	
	private static final String BASE_URL = "/books";
	
	@Operation(rel = "search-book", method = "GET", params = { @Parameter(name = "query")})
	public String searchBook;
	
	public BooksListResource(List<BookResource> items, Long totalElements, Integer size, Integer page) {
		super(items, BASE_URL, totalElements, size, page);
		
		if(SecurityController.isAuthenticated()){
			this.searchBook = "/books-search/";
		}
	}

}
