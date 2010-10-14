package models;

import play.*;
import play.db.jpa.*;
import play.libs.Crypto;

import javax.persistence.*;

import java.util.*;

@Entity
public class User extends Model {
	
    public String email;
    public String userName;
    public String password;
    public boolean isAdmin;
    
	/** Date the user created his account */
	private Date crDate;
    
	/** the dives the user has made */
	@ManyToMany(cascade = CascadeType.ALL)
	public List<Dive> dives;
	
	@OneToMany(mappedBy = "uploader", cascade = CascadeType.ALL)
	public List<Picture> pictures;
	
	@ManyToMany(cascade = CascadeType.ALL)
	public List<User> buddies;
	
    public User(String email, String userName, String password) {
        this.email = email;
        this.userName = userName;
        this.password = Crypto.passwordHash(password);
    }
    
    /**
     * Authenticate a user given his email and password.
     * @param email
     * @param password
     * @return true if authenticated, false otherwise
     */
    public static boolean connect(String email, String password) {
    	User user = find("email=? and password=?", email, Crypto.passwordHash(password)).first();
    	if (user!=null) {
    		return true;
    	}
    	return false;
    }
	
    public Date getCrDate() {
		return crDate;
	}

    
}
