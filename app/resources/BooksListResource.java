package resources;

import hypermedia.core.PagedListResource;
import hypermedia.core.Resource;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class BooksListResource extends PagedListResource{

	public BooksListResource(String baseUrl, List<Resource> items, int totalElements, int size, int page) {
		super(baseUrl + "books", totalElements, size, page);
	}

}
