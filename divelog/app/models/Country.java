package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import java.util.*;

@Entity
public class Country extends Model {

	public String name;
	
	@OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
	public List<Spot> spots;
	
}
