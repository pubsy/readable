package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Book;
import models.User;
import models.UserBookConnection;
import models.UserBookConnection.ConnectionType;
import play.mvc.With;
import resources.BookResource;
import resources.BooksListResource;
import controllers.SecurityController.Secured;
import controllers.hypercore.BasicController;

@With(SecurityController.class)
public class BooksController extends BasicController{

	/*
	 * Controller methods
	 */

    public static void books(Integer page, Integer size){
    	render(new BooksListResource(getBooksList(page, size), getTotalBooksCount(), size, page));
    }

	public static void book(Long id){
    	render(new BookResource(getBookById(id)));
    }

	@Secured
	public static void createBook(String title){
		Book book = new Book(title).save();
		response.status = 201;
		response.setHeader("Location", "/books/" + book.id);
	}

	@Secured
	public static void mark(Long id, UserBookConnection.ConnectionType type) {
		User user = SecurityController.getAuthenticatedUser();
		Book book = getBookById(id);

		removeExistingUserBookConnectionIfExists(type, user, book);
		saveNewUserBookConnection(type, user, book);
		
    	ok();
    }

	/*
	 * Utility methods
	 */

	private static void removeExistingUserBookConnectionIfExists(ConnectionType type, User user, Book book) {
		UserBookConnection connection = UserBookConnection.find("byUserAndBook", user, book).first();
		if(connection != null) {
			connection.delete();
		}
	}

	private static void saveNewUserBookConnection(UserBookConnection.ConnectionType type, User user, Book book) {
		UserBookConnection connection = new UserBookConnection();
		connection.book = book;
		connection.user = user;
		connection.type = type;
		connection.save();
	}
	
	private static Long getTotalBooksCount() {
		return Book.count();
	}

	private static Book getBookById(Long id) {
		return Book.findById(id);
	}

	private static List<BookResource> getBooksList(Integer page, Integer size) {
    	size = (size == null) ? 3 : size;
    	page = (page == null) ? 0 : page;
    	
		List<Book> books = Book.find("order by insertedAt asc").fetch(page + 1, size);
		
		List<BookResource> resources = new ArrayList<BookResource>();
		for(Book book: books) {
			resources.add(new BookResource(book));
		}
		
		return resources;
	}
}
