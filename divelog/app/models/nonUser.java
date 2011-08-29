package models;

import play.*;
import play.db.jpa.*;
import play.libs.Crypto;

import javax.persistence.*;

import java.util.*;

@Entity
public class nonUser extends Model {
	
    public String email;
    public String password;
    public String fullname;
    
	/** Date the user created his account */
	private Date crdate; // private because must be read-only.
    
	/** the dives the user has made */
	@ManyToMany
	public List<Dive> dives;
	
	
    public nonUser(String email, String password, String fullname) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
    }
    
    /**
     * Authenticate a user given his email and password.
     * @param email
     * @param password
     * @return true if authenticated, false otherwise
     */
    public static boolean connect(String email, String password) {
    	nonUser user = find("email=? and password=?", email, Crypto.passwordHash(password)).first();
    	if (user!=null) {
    		return true;
    	}
    	return false;
    }
	
    public Date getCrdate() {
		return crdate;
	}

    
}
