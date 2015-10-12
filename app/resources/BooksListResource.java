package resources;

import hypermedia.annotations.Operation;
import hypermedia.annotations.Parameter;
import hypermedia.core.PagedListResource;
import hypermedia.core.Resource;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import controllers.SecurityController;

public class BooksListResource extends PagedListResource<BookResource> {
	
	private static final String BASE_URL = "/books";
	
	@Operation(rel = "add-book", method = "POST", params = { @Parameter(name = "title") })
	public String addBook;
	
	public BooksListResource(List<BookResource> items, Long totalElements, Integer size, Integer page) {
		super(items, BASE_URL, totalElements, size, page);
		
		if(SecurityController.isAuthenticated()){
			this.addBook = BASE_URL;
		}
	}

}
