package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import play.db.jpa.Model;

@MappedSuperclass
public class BasicModel extends Model {
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "inserted_at")
	public Date insertedAt;
	
	public BasicModel(){
		this.insertedAt = new Date();
	}
}
