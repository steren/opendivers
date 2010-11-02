package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class FriendRequest extends Model {
	
	public enum Status {
		ACCEPTED, PENDING, REJECTED
	}
	
	@ManyToOne
	public User from;
	@ManyToOne
	public User to;
	public Date date;
	public String message;
	public Status status;
	
	public FriendRequest(User from, User to, String message) {
		this.from = from;
		this.to = to;
		this.date = new Date();
		this.message = message;
		this.status = Status.PENDING;
	}
}
