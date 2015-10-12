package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(name = "books")
public class Book extends BasicModel {

	@Column(nullable = false)
	public String title;

	public Book(String name){
		this.title = name;
	}

}
