package resources;

import hypermedia.annotations.Link;
import hypermedia.annotations.Operation;
import hypermedia.annotations.Parameter;
import hypermedia.core.Resource;

public class RootResource implements Resource {

	public String desrc = "bla";

	@Operation(rel = "add-book", method = "POST", params = { @Parameter(name = "title"), @Parameter(name = "author") })
	public String addBook = "addbook";

	@Link(rel = "read-books")
	public String readBooks = "readbooks";

	@Link(rel = "want-to-read-books")
	public String wantToReadBooks = "wanttoreadbooks";
}
