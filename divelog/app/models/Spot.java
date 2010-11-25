package models;

import play.*;
import play.data.validation.*;
import play.db.jpa.*;

import javax.persistence.*;

import java.util.*;

@Entity
public class Spot extends Model {

	@Required
	public String name;

	public double latitude;
	public double longitude;

	@ManyToOne
	@Required
	public Country country;
	
	/** dives done in this spot */
	@OneToMany(mappedBy = "spot")
	public List<Dive> dives;

	/** Store a pre-computed list of fishes seen on this spot */
	@ManyToMany
	public List<Fish> fishes;
	
	public Spot() {
		this.fishes = new ArrayList<Fish>();
	}
	
	public String toString() {
		return name + ", " + country;
	}

	/** Get all pictures taken at this spot */
	public List<Picture> getPictures() {
		List<Picture> pictures = Picture.find("select p from Picture p " 
				+ "join p.dive d "
				+ "join d.spot s "
				+ "where s.id = ? "
				+ "order by d.date DESC",
				this.id).fetch(16);
		return pictures;
	}

	/** get the total list of divers you dove here */
	public List<User> getDivers() {
		List<User> divers = new ArrayList<User>();
		for(Dive dive : this.dives) {
			for(User user : dive.userDivers) {
				if(!divers.contains(user)) {
					divers.add(user);
				}
			}
		}
		
		return divers;
	}
	
	public void addFishes(List<Fish> diveFishes) {
		for(Fish fish : diveFishes) {
			if(!fishes.contains(fish)) {
				fishes.add(fish);
			}
		}
		this.save();
	}
	
	public int getDiveNumber() {
		return this.dives.size();
	}
	
}
