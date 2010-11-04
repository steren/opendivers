package models;

import play.*;
import play.data.validation.InPast;
import play.data.validation.Max;
import play.data.validation.Min;
import play.data.validation.Range;
import play.data.validation.Required;
import play.db.jpa.*;

import javax.persistence.*;

import org.hibernate.mapping.Array;

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
	/** has the value been set ? */
	public boolean maxDepthSet;
	
	@Range(min = 0, max = 3600)
	public long duration;
	/** has the value been set ? */
	public boolean durationSet;	
	
	public double airTemperature;
	/** has the value been set ? */
	public boolean airTemperatureSet;
	
	public double waterSurfaceTemperature;
	/** has the value been set ? */
	public boolean waterSurfaceTemperatureSet;
	
	public double waterBottomTemperature;
	/** has the value been set ? */
	public boolean waterBottomTemperatureSet;
	
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
	
	
	@OneToMany(mappedBy="dive", cascade=CascadeType.ALL)
	public List<Picture> pictures;
	
	/** divers in this dive who don't have an account */
	// TODO
	//public List<nonUser> otherDivers;
	
	public Dive() {
		pictures = new ArrayList<Picture>();
		fishes = new ArrayList<Fish>();
	}
	
	public String toString() {
	    return date.toString();
	}
}
