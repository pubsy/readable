package resources;

import hypermedia.annotations.Operation;
import models.Book;
import models.User;
import models.UserBookConnection;
import controllers.SecurityController;

public class BookResource extends ReadableBasicResource {

	@Operation(rel = "mark-as-read", method = "POST", title="Add to 'Read'")
	public String markAsRead;

	@Operation(rel = "mark-as-planning-to-read", method = "POST", title="Add to 'Planing to read'")
	public String markAsPlanningToRead;

	public String title;
	public String description;
	public String authorName;
	public String thumbnailUrl;
	public String smallThumbnailUrl;
	public int readersCount;

	
	public BookResource(Book book) {
		setSelf("/books/" + book.externalId);
		
		this.title = book.title;
		this.description = book.description;
		this.authorName = book.authorName;
		this.readersCount = book.readers.size();
		this.thumbnailUrl = book.thumbnailUrl;
		this.smallThumbnailUrl = book.smallThumbnailUrl;

		if(!SecurityController.isAuthenticated()){
			return;
		}
		if (bookNotMarked(book, UserBookConnection.ConnectionType.READ)) {
			markAsRead = "/books/" + book.externalId + "/mark?type=READ";
		}
		if (bookNotMarked(book, UserBookConnection.ConnectionType.PLANNING_TO_READ)) {
			markAsPlanningToRead = "/books/" + book.externalId + "/mark?type=PLANNING_TO_READ";
		}
		
	}

	private boolean bookNotMarked(Book book, UserBookConnection.ConnectionType type) {
		User user = SecurityController.getAuthenticatedUser();
		if(book.getId() != null){
			return UserBookConnection.find("byBookAndUserAndType", book, user, type).first() == null;
		}
		return true;
	}

	public String toString() {
		return this.title;
	}
}