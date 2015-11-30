package resources;

import hypermedia.annotations.Link;
import hypermedia.annotations.Operation;
import hypermedia.annotations.Parameter;
import hypermedia.core.Resource;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import models.User;
import controllers.SecurityController;

public class BooksListResource extends PagedListResource<BookResource> {

	private static final String BASE_URL = "/books";
	
	@Operation(rel = "search", method = "GET", title="Search", params = { @Parameter(name = "query")})
	public String searchBook = "/books-search";
	
	public BooksListResource() {
		withRootUrl(BASE_URL);
	}

}
