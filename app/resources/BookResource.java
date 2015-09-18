package resources;

import hypermedia.core.Resource;

public class BookResource implements Resource {
	
	public BookResource(String self, String name) {
		this.name = name;
	}

	public String name;
	
}
