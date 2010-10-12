package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import java.util.*;

@Entity
public class Dive extends Model {

	/** date of the dive */
	public Date date;
	
	/** location of this dive */
	@ManyToOne
	public Spot spot;

	public long maxDepth;
	public long duration;
	
	/** divers in this dive who have an account */
	@ManyToMany(mappedBy = "dives", cascade = CascadeType.ALL)
	public List<User> userDivers;
	
	/** divers in this dive who don't have an account */
	// TODO
	//public List<nonUser> otherDivers;
	
}
