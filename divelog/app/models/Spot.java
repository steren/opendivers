package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import java.util.*;

@Entity
public class Spot extends Model {

	public String Name;

	public double latitude;
	public double longitude;

	@ManyToOne
	public Country country;
	
	/** dives done in this spot */
	@OneToMany(mappedBy = "spot", cascade = CascadeType.ALL)
	public List<Dive> dives;
	
}
