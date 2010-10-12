package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;


@Entity
public class Trip extends Model {

	public Date startDate;
	public Date endDate;
	
	@ManyToOne
	public User creator;

	@ManyToMany
	public List<Dive> dives;
	
	@ManyToMany
	public List<Country> countries;
	
}
