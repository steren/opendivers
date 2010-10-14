package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class CommentModel extends Model {
	
	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
	public List<Comment> comment;
    
}
