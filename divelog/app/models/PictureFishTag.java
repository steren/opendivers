package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class PictureFishTag extends Model {

	@ManyToOne
	public Picture picture;
	
	@ManyToOne
	public Fish fish;

	/** the X coordinate of this tag, between 0 and 1*/
	public double x;
	/** the Y coordinate of this tag, between 0 and 1*/
	public double y;
	
	
}
