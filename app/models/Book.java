package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "books")
public class Book extends BasicModel {

	@Column(nullable = false)
	public String externalId;
	
	@Column(nullable = false)
	public String title;
	
	@Lob
	@Column(nullable = false)
	public String description;
	
	@Column(nullable = false)
	public String authorName;
	
	@Lob
	@Column(nullable = false, length=512)
	public String thumbnailUrl;

	@Lob
	@Column(nullable = false, length=512)
	public String smallThumbnailUrl;
	
	@OneToMany(mappedBy = "book", cascade=CascadeType.REMOVE, fetch=FetchType.EAGER)
	public List<UserBookConnection> readers = new ArrayList<UserBookConnection>();

	public Book(){
	}

}
