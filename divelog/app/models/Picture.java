package models;

import play.*;
import play.db.jpa.*;
import play.libs.Images;

import javax.imageio.ImageIO;
import javax.persistence.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Entity
public class Picture extends Model {
	
	public static final int SIZE_NORMAL_X = 640;
	public static final int SIZE_NORMAL_Y = 480;
	
	public static final int SIZE_THUMBNAIL = 150;
	
	public static final int SIZE_ICON = 40;
	
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
    
	@OneToMany(mappedBy = "picture")
	public List<PictureFishTag> tags;
	
    public Date getUploadDate() {
		return uploadDate;
	}
	
    public Picture(Blob image, User uploader) {
    	this.uploadDate = new Date();
    	this.uploader = uploader;
    	
    	// save the original
    	this.imageOriginal = image;
    	
    	
    	
    	int originalX = 0;
		int originalY = 0;
		float ratio = 0;
    	
    	try {
			BufferedImage original = ImageIO.read(image.getFile());
			originalX = original.getWidth();
			originalY = original.getHeight();
			ratio = new Float(originalX) / new Float(originalY);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// We want normal image to be contained into 640 x 480
		int normalX = SIZE_NORMAL_X;
		int normalY = SIZE_NORMAL_Y;
		int cropOriginX = 0;
		int cropOriginY = 0;
		int cropEndX = 0;
		int cropEndY = 0;
		
		if(ratio > 1) {
			// max size = X, limit size = Y
			normalY = (int) (SIZE_NORMAL_X / ratio);
			cropOriginX = (int) ((normalX - normalY) / 2);
			cropEndX = normalY + cropOriginX;
			cropEndY = normalY;
		} else {
			// max size = Y, limit size = X
			normalX = (int) (SIZE_NORMAL_Y * ratio);
			cropOriginY = (int) ((normalY - normalX) / 2);
			cropEndY = normalX + cropOriginY;
			cropEndX = normalX;
		}
		
		File normal = new File(Blob.getStore(), image.getFile().getName() + "_normal");
		File thumbnail = new File(Blob.getStore(), image.getFile().getName() + "_thumb");
		File icon = new File(Blob.getStore(), image.getFile().getName() + "_icon");

		// resize to a normal size
    	Images.resize(image.getFile(), normal, normalX, normalY);
    	// generate a thumbnail
    	Images.crop(normal, thumbnail, cropOriginX, cropOriginY, cropEndX, cropEndY);
    	Images.resize(thumbnail, thumbnail, SIZE_THUMBNAIL, SIZE_THUMBNAIL);
    	// generate an icon from the thumbnail
    	Images.resize(thumbnail, icon, SIZE_ICON, SIZE_ICON);
    	
    	try {
        	this.imageNormal = new Blob();
			this.imageNormal.set(new FileInputStream(normal), image.type());
        	this.imageThumbnail = new Blob();
			this.imageThumbnail.set(new FileInputStream(thumbnail), image.type());
        	this.imageIcon = new Blob();
			this.imageIcon.set(new FileInputStream(icon), image.type());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
    
}
