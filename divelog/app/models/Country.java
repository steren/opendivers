package models;

import play.*;
import play.db.jpa.*;
import play.i18n.Lang;

import javax.persistence.*;

import java.util.*;

@Entity
public class Country extends Model {

	public String code;
	
	@OneToMany(mappedBy = "country")
	public List<Spot> spots;
	
	public Country(String code) {
		this.code = code;
	}
	
	/** return the name of this country, in the language of the current user */
	public String getName() {
		Locale locale = new Locale(Lang.get(), code);
		return locale.getDisplayCountry(locale);
	}
	
	public String toString() {
	    return getName();
	}
	
}
