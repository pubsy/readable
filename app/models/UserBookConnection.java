package models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "users_books", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "book_id" }))
public class UserBookConnection extends BasicModel {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	public User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false)
	public Book book;

	@Enumerated(EnumType.STRING)
	public ConnectionType type;

	public enum ConnectionType {
		READ, READING, PLANNING_TO_READ
	}
}
