package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Comment extends CommentModel {
    
	@ManyToOne
	public CommentModel item;
	
	public User creator;
	private Date crDate;
	public String message;
	
    public Date getCrDate() {
		return crDate;
	}
	
}
