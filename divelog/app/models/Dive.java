package models;

import play.*;
import play.data.validation.InPast;
import play.data.validation.Max;
import play.data.validation.Min;
import play.data.validation.Range;
import play.data.validation.Required;
import play.db.jpa.*;

import javax.persistence.*;

import java.text.DateFormat;
import java.util.*;

@Entity
public class Dive extends CommentModel {

	/** date of the dive */
	@Required @InPast
	public Date date;
	
	/** location of this dive */
	@ManyToOne
	public Spot spot;

	public double maxDepth;
	@Range(min = 0, max = 3600)
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
	
	@ManyToMany
	public List<Fish> fishes;
	
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
