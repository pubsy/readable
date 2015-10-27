package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import play.db.jpa.Model;

@Entity
@Table(name = "books")
public class Book extends BasicModel {

	@Column(nullable = false)
	public String title;
	
	@Column(nullable = false)
	public String authorName;
	
	@Column(nullable = false)
	public String thumbnailUrl;
	
	@OneToMany(mappedBy = "book", cascade=CascadeType.REMOVE, fetch=FetchType.EAGER)
	public List<UserBookConnection> readers = new ArrayList<UserBookConnection>();

	public Book(String name, String authorName, String thumbnailUrl){
		this.title = name;
		this.authorName = authorName;
		this.thumbnailUrl = thumbnailUrl;
	}

}
