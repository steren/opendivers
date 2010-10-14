package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import java.util.*;

@Entity
public class Fish extends CommentModel {
    
	/** name of the fish, in latin */
	public String name;
	
	/** a list of tags from where the */
	@OneToMany(mappedBy = "fish", cascade = CascadeType.ALL) 
	public List<PictureFishTag> tags;
	
}
