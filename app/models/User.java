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

@Entity
@Table(name = "users")
public class User extends BasicModel {

	@Column(unique=true, nullable = false)
	public String username;

	@OneToMany(mappedBy = "user", cascade=CascadeType.REMOVE, fetch=FetchType.EAGER)
	@Where(clause = "type = 'READ'")
	public List<UserBookConnection> readBooks = new ArrayList<UserBookConnection>();

	@OneToMany(mappedBy = "user", cascade=CascadeType.REMOVE, fetch=FetchType.EAGER)
	@Where(clause = "type = 'PLANNING_TO_READ'")
	public List<UserBookConnection> planningToReadBooks = new ArrayList<UserBookConnection>();

	public User(String username) {
		this.username = username;
	}

	public static User findByUserName(String username) {
		return User.find("byUsername", username).first();
	}
}
