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
	@ManyToMany
	public List<Dive> dives;
	
	@OneToMany(mappedBy = "uploader")
	public List<Picture> pictures;
	
	@ManyToMany
	public List<User> buddies;
	
	@OneToMany(mappedBy="to")
	public List<FriendRequest> receivedPendingRequests;

	@OneToMany(mappedBy="from")
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
    
    /**
     * send a friend request to another user
     * @param user
     * @param message
     * @return true if request sent, false if already a friend.
     */
    public boolean sendRequest(User user, String message) {
    	if( !this.buddies.contains(user) ) {
    		return false;
    	}
    	
    	FriendRequest request = new FriendRequest(this, user, message);
		request.save();
		
		this.sentPendingRequests.add(request);
		this.save();
		user.receivedPendingRequests.add(request);
		user.save();
		return true;
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
