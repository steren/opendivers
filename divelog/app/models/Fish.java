package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import java.util.*;

@Entity
public class Fish extends CommentModel {
    
	/** URL of the fish on wikipedia */
	public String wikipediaURL;
	
	/** URL of the image of this fish on wikipedia */
	public String wikipediaImageURL;
	
	/** name of the fish, in latin */
	public String binomial;

	/** name of the fish, in English */
	private String name_en;
	
	/** name of the fish, in French */
	private String name_fr;
	
    public Blob imageOriginal;
    
    public Blob imageThumbnail;
    
	/** a list of tags from where the */
	@OneToMany(mappedBy = "fish") 
	public List<PictureFishTag> tags;

	
	public Fish(String wikipediaURL) {
		this.wikipediaURL = wikipediaURL;
	}
	
	public void setName(String name) {
		this.name_en = name;
	}
	
	public String getName() {
		return name_en;
	}
	
	public String toString() {
	    return getName();
	}
	
}
