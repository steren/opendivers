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
public class Dive extends Model {

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
	
	@ManyToMany(mappedBy = "dives")
	public List<Trip> trips;
	
	@ManyToMany
	public List<Fish> fishes;
	
	/** divers in this dive who have an account */
	@ManyToMany(mappedBy = "dives")
	public List<User> userDivers;
	
	
	@OneToMany(mappedBy="dive")
	public List<Picture> pictures;
	
	/** divers in this dive who don't have an account */
	// TODO
	//public List<nonUser> otherDivers;
	
	public Dive() {
		pictures = new ArrayList<Picture>();
		fishes = new ArrayList<Fish>();
	}
	
	/**
	 * Associate this dive with these fishes
	 * @param fishIdList : a comma separated integer list of fish ids
	 */
	public void setFishList(String fishIdList) {
		
		Logger.info(fishIdList);
		
		// read the string id, interpret them as int
		String[] idStringArray = fishIdList.split(",");
		
		String cleanFishIdList = "";
		List<Long> ids = new ArrayList<Long>();
		
		for (String fishStringid : idStringArray) {
			if(!fishStringid.isEmpty()) {
				ids.add( Long.parseLong(fishStringid.trim()) );
			}
		}
		
		// Create a final string for the query
        StringBuffer buffer = new StringBuffer();
        Iterator iter = ids.iterator();
        while (iter.hasNext()) {
        	buffer.append("'");
            buffer.append(iter.next());
            buffer.append("'");
            if (iter.hasNext()) {
                buffer.append(",");
            }
        }
        cleanFishIdList = buffer.toString();

        if(!cleanFishIdList.equals("")) {
        	this.fishes = Fish.find("select f from Fish f where f.id in (" + cleanFishIdList + ")").fetch();
        } else {
        	this.fishes = new ArrayList<Fish>();
        }

        Logger.info(this.fishes.toString());
	}
	
	public String toString() {
	    return date.toString();
	}
}
