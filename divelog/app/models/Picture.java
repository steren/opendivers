package models;

import play.*;
import play.db.jpa.*;
import play.libs.Images;

import javax.imageio.ImageIO;
import javax.persistence.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Entity
public class Picture extends Model {
	
	public static final int SIZE_NORMAL_X = 640;
	public static final int SIZE_NORMAL_Y = 480;
	
	public static final int SIZE_THUMBNAL_X = 180;
	public static final int SIZE_THUMBNAL_Y = 180;
	
	public static final int SIZE_ICON_X = 60;
	public static final int SIZE_ICON_Y = 60;
	
    public String title;
    public String description;
    private Date uploadDate;
    
    public Blob imageOriginal;
    
    public Blob imageNormal;

    public Blob imageThumbnail;
    
    public Blob imageIcon;
    
    @ManyToOne
    public Dive dive;
    
	@ManyToOne
    public User uploader; 
    
	@OneToMany(mappedBy = "picture", cascade = CascadeType.ALL)
	public List<PictureFishTag> tags;
	
    public Date getUploadDate() {
		return uploadDate;
	}
	
    public Picture(Blob image, User uploader) {
    	this.uploadDate = new Date();
    	this.uploader = uploader;
    	
    	// save the original
    	this.imageOriginal = image;
    	
    	File normal = new File(Blob.getStore(), image.getFile().getName() + "_normal");
    	File thumbnail = new File(Blob.getStore(), image.getFile().getName() + "_thumb");
    	File icon = new File(Blob.getStore(), image.getFile().getName() + "_icon");
    	
    	try {
			BufferedImage original = ImageIO.read(image.getFile());
			int originalImageWidth = original.getWidth();
			int originalImageHeight = original.getHeight();
			float ratio = new Float(originalImageWidth) / new Float(originalImageHeight);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	// resize to a normal size
    	Images.resize(image.getFile(), normal, SIZE_NORMAL_X, SIZE_THUMBNAL_Y);
    	// generate a thumbnail
    	Images.resize(normal, thumbnail, SIZE_NORMAL_X, SIZE_NORMAL_Y);
    	// generate an icon from the thumbnail
    	Images.resize(thumbnail, icon, SIZE_ICON_X, SIZE_ICON_Y);
    }
    
}
