package resources;

import controllers.SecurityController;
import models.Book;
import hypermedia.core.BasicResource;

public class UserBookResource extends BasicResource {

	public BookResource book;

	public UserBookResource(Book book) {
		super("/books/" + book.id);
		this.book = new BookResource(book);
	}
	
	public String toString() {
		return book.title;
	}

}
