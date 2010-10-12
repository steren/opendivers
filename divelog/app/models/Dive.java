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

	public double maxDepth;
	public long duration;
	
	public double airTemperature;
	public double waterSurfaceTemperature;
	public double waterBottomTemperature;
	
	@ManyToMany
	public List<DiveType> type;
	@ManyToOne
	public WaterType water;
	
	/** was it a dive dedicated to training ? */
	public boolean training;
	
	@ManyToOne
	public Center center;
	
	@ManyToMany(mappedBy = "dives", cascade = CascadeType.ALL)
	public List<Trip> trips;
	
	/** divers in this dive who have an account */
	@ManyToMany(mappedBy = "dives", cascade = CascadeType.ALL)
	public List<User> userDivers;
	
	/** divers in this dive who don't have an account */
	// TODO
	//public List<nonUser> otherDivers;
	
	public String toString() {
	    return date.toString();
	}
}
