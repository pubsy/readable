package hypermedia.core;

import hypermedia.annotations.Link;

import java.util.List;

import play.mvc.results.NotFound;

public abstract class PagedListResource<T extends BasicResource> implements Resource {
	
	protected static final Integer DEFAULT_PAGE_SIZE = 9;
	
	public List<T> items;

	public PagedListResource(List<T> items, String rootUrl, Long totalElements, Integer size, Integer page) {
    	size = (size == null) ? DEFAULT_PAGE_SIZE : size;
    	page = (page == null) ? 0 : page;
    	
		this.self = rootUrl + "/page/" + page + "/size/" + size;

		this.items = items;
		
		if(size * page > totalElements){
			throw new NotFound("");
		}
		if(size * (page + 1) < totalElements){
			next = rootUrl + "/page/" + (page + 1) + "/size/" + size;
			
			long maxPage = totalElements / size + (totalElements % size == 0? -1 : 1);
			
			last = rootUrl + "/page/" + maxPage + "/size/" + size;
		}
		if(page > 0){
			first = rootUrl + "/page/0/size/" + size;
			previous = rootUrl + "/page/" + (page - 1) + "/size/" + size;
		}
	}
	
    @Link(rel = "self")
    public String self;

	@Link(rel = "next")
	public String next;

	@Link(rel = "previous")
	public String previous;
	
	@Link(rel = "first")
	public String first;
	
	@Link(rel = "last")
	public String last;
}
