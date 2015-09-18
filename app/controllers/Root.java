package controllers;

import hypermedia.core.Resource;

import java.util.ArrayList;
import java.util.List;

import play.mvc.Http.Header;
import resources.BookResource;
import resources.BooksListResource;
import resources.RootResource;
import controllers.hypercore.BasicController;

public class Root extends BasicController {

    public static void root() {
    	render(new RootResource());
    }

    public static void books(Integer page, Integer size){
    	BookResource resource1 = new BookResource("books/123", "book 1");
    	BookResource resource2 = new BookResource("books/345", "book 2");
    	BookResource resource3 = new BookResource("books/789", "book 2");

    	List<Resource> list = new ArrayList<Resource>();
    	list.add(resource1); list.add(resource2); list.add(resource3);

    	int totalElements = 50;
    	size = (size == null) ? 5 : size;
    	page = (page == null) ? 0 : page;
    	render(new BooksListResource("books", list, totalElements, size, page));
    }

}