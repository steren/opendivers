package models;

import play.*;
import play.data.validation.Email;
import play.data.validation.Password;
import play.data.validation.Required;
import play.db.jpa.*;
import play.libs.Crypto;

import javax.persistence.*;

import java.util.*;

@Entity
public class User extends Model {
	
	
	@Required @Email
    public String email;
	@Required
    public String userName;
	@Required @Password
    public String password;
    public boolean isAdmin;
    
	/** Date the user created his account */
	private Date crDate;
    
	/** Avatar of this user */
    public Blob avatar;
	
	/** the dives the user has made */
	@ManyToMany(cascade = CascadeType.ALL)
	public List<Dive> dives;
	
	@OneToMany(mappedBy = "uploader", cascade = CascadeType.ALL)
	public List<Picture> pictures;
	
	@ManyToMany(cascade = CascadeType.ALL)
	public List<User> buddies;
	
	@OneToMany(mappedBy="to", cascade=CascadeType.ALL)
	public List<FriendRequest> receivedPendingRequests;

	@OneToMany(mappedBy="from", cascade=CascadeType.ALL)
	public List<FriendRequest> sentPendingRequests;
	
    public User(String email, String userName, String password) {
        this.email = email;
        this.userName = userName;
        this.password = Crypto.passwordHash(password);
        this.crDate = new Date();
        
        this.buddies = new ArrayList<User>();
        this.receivedPendingRequests = new ArrayList<FriendRequest>();
        this.sentPendingRequests = new ArrayList<FriendRequest>();
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
    
    public void sendRequest(User user, String message) {
    	FriendRequest request = new FriendRequest(this, user, message);
		request.save();
		
		this.sentPendingRequests.add(request);
		this.save();
		user.receivedPendingRequests.add(request);
		user.save();
    }
    
    public void respondRequest(FriendRequest request, FriendRequest.Status response) {
    	request.status = response;
    	request.save();
    	
    	if(response.equals(FriendRequest.Status.ACCEPTED)) {
    		if(! this.buddies.contains(request.from)) {
    			this.buddies.add(request.from);
    			this.save();
    		}
    		if(! request.from.buddies.contains(this)) {
    			request.from.buddies.add(this);
    			request.from.save();
    		}
    	}
    	request.delete();
    	
    }
    
}
