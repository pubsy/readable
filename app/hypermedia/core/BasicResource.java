package hypermedia.core;

import hypermedia.annotations.Link;

public abstract class BasicResource implements Resource {

    @Link(rel = "self")
    public String self;
    
	public BasicResource(String self) {
		this.self = self;
	}
}
