package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class WaterType extends Model {
	public String label;
	
	public String toString() {
	    return label;
	}
}
