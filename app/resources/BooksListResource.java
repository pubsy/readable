package resources;

import hypermedia.annotations.Link;
import hypermedia.annotations.Operation;
import hypermedia.annotations.Parameter;
import hypermedia.core.PagedListResource;
import hypermedia.core.Resource;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import models.User;
import controllers.SecurityController;

public class BooksListResource extends PagedListResource<BookResource> {

	private static final String BASE_URL = "/books";
	
	@Operation(rel = "search", name="search",  method = "GET", title="Title, author etc.", params = { @Parameter(name = "query")})
	public String searchBook = "/books-search";
	
	public BooksListResource(List<BookResource> items, Long totalElements, Integer size, Integer page) {
		super(items, BASE_URL, totalElements, size, page);
	}

}
