package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import java.util.*;

@Entity
public class Picture extends Model {
    public String title;
    public String description;
    private Date uploadDate;

	@ManyToOne
    public User uploader; 
    
	public Blob image;

	@OneToMany(mappedBy = "picture", cascade = CascadeType.ALL)
	public List<PictureFishTag> tags;
	
    public Date getUploadDate() {
		return uploadDate;
	}
	
    public Picture(Blob image, User uploader) {
    	this.uploadDate = new Date();
    	this.image = image;
    	this.uploader = uploader;
    }
    
}
