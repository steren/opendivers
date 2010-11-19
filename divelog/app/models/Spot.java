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
	@OneToMany(mappedBy = "spot", cascade = CascadeType.ALL)
	public List<Dive> dives;

	public String toString() {
		return name + ", " + country;
	}

	public List<Picture> getPictures() {
		List<Picture> pictures = Picture.find("select p from Picture p " 
				+ "join p.dive d "
				+ "join d.spot s "
				+ "where s.id = ?"
				+ "order by d.date DESC",
				this.id).fetch(16);
		return pictures;
	}
	
}
