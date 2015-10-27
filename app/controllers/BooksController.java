package controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import models.Book;
import models.User;
import models.UserBookConnection;
import models.UserBookConnection.ConnectionType;
import play.libs.F;
import play.libs.F.Promise;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.mvc.With;
import play.mvc.results.Result;
import resources.BookResource;
import resources.BooksListResource;
import controllers.SecurityController.Secured;
import controllers.hypercore.BasicController;

@With(SecurityController.class)
public class BooksController extends BasicController {

	/*
	 * Controller methods
	 */

	public static void books(Integer page, Integer size) {
		render(new BooksListResource(getBooksList(page, size), getTotalBooksCount(), size, page));
	}

	public static void book(Long id) {
		render(new BookResource(getBookById(id)));
	}

	@Secured
	public static void searchBook(String query) {
		List<Book> books = getBooks(query);
		List<BookResource> resourcesList = transformToBookResourcesList(books);
		render(new BooksListResource(resourcesList, (long)resourcesList.size(), resourcesList.size(), 0));
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
		if (connection != null) {
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

		List<BookResource> resources = transformToBookResourcesList(books);

		return resources;
	}

	private static List<BookResource> transformToBookResourcesList(List<Book> books) {
		List<BookResource> resources = new ArrayList<BookResource>();
		for (Book book : books) {
			resources.add(new BookResource(book));
		}
		return resources;
	}

	private static List<Book> getBooks(String query) {
		F.Promise<WS.HttpResponse> f = WS.url("https://www.googleapis.com/books/v1/volumes?q=" 
				+ query.replaceAll("\\s", "+")).getAsync();
		Promise<HttpResponse> promise = F.Promise.waitAny(f);
		
		List<Book> list = new ArrayList<Book>();
		
		try {
			String title = "";
			String authorName = "";
			String thumbnailUrl = "https://books.google.de/googlebooks/images/no_cover_thumb.gif";
			
			Iterator<JsonElement> iterator = promise.get().getJson().getAsJsonObject().getAsJsonArray("items").iterator();
			while(iterator.hasNext()){
				JsonElement next = iterator.next();
				JsonObject volumeInfo = next.getAsJsonObject().getAsJsonObject("volumeInfo").getAsJsonObject();
				
				title = volumeInfo.getAsJsonPrimitive("title").getAsString();
				JsonArray authorsArray = volumeInfo.getAsJsonArray("authors");
				if(authorsArray != null){
					authorName = volumeInfo.getAsJsonArray("authors").get(0).getAsString();
				}
				JsonObject imageLinks = volumeInfo.getAsJsonObject("imageLinks");
				if(imageLinks != null){
					thumbnailUrl = imageLinks.getAsJsonObject().getAsJsonPrimitive("smallThumbnail").getAsString();
				}
				list.add(new Book(title, authorName, thumbnailUrl));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return list;
	}
}
