package resources;

import hypermedia.annotations.Link;
import hypermedia.core.Resource;

import java.util.List;

import play.mvc.results.NotFound;

public abstract class PagedListResource<T extends ReadableBasicResource>
    extends ReadableBasicResource {

    @Link(rel = "next pagination", title = "Next")
    public String next;

    @Link(rel = "previous pagination", title = "Previous")
    public String previous;

    @Link(rel = "first pagination", title = "First")
    public String first;

    @Link(rel = "last pagination", title = "Last")
    public String last;

    public List<T> items;
    
    private static final Integer DEFAULT_PAGE_SIZE = 9;
    
    private String rootUrl;
    private Long totalElements;
    private Integer size;
    private Integer page;

    public PagedListResource withItems(List<T> items) {
        this.items = items;
        return this;
    }

    public PagedListResource withRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
        return this;
    }

    public PagedListResource withTotalElements(Long totalElements) {
        this.totalElements = totalElements;
        return this;
    }

    public PagedListResource withSize(Integer size) {
        this.size = size;
        return this;
    }

    public PagedListResource withPage(Integer page) {
        this.page = page;
        return this;
    }
    
    public PagedListResource build() {
        validate();
        initDefaults();

        setSelf(rootUrl + "/page/" + page + "/size/" + size);

        if (size * page > totalElements) {
            throw new NotFound("");
        }
        if (size * (page + 1) < totalElements) {
            next = rootUrl + "/page/" + (page + 1) + "/size/" + size;

            int maxPage = (int) (totalElements / size + (totalElements % size == 0 ? -1 : 1));

            last = rootUrl + "/page/" + maxPage + "/size/" + size;
        }
        if (page > 0) {
            first = rootUrl + "/page/0/size/" + size;
            previous = rootUrl + "/page/" + (page - 1) + "/size/" + size;
        }
        
        return this;
    }

    private void validate() {
        if(items == null || rootUrl == null || totalElements == null){
            throw new RuntimeException("Can not build PagedListResource. Mandatory fileds missing.");
        }
    }

    private void initDefaults() {
        this.size = (this.size == null) ? DEFAULT_PAGE_SIZE : this.size;
        this.page = (this.page == null) ? 0 : this.page;
    }

}
