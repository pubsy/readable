package resources;

import controllers.SecurityController;
import models.Book;

public class UserBookResource extends ReadableBasicResource {

	public BookResource book;

	public UserBookResource(Book book) {
		setSelf("/books/" + book.id);
		this.book = new BookResource(book);
	}
	
	public String toString() {
		return book.title;
	}

}
