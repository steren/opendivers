package controllers;

import play.*;
import play.db.jpa.Blob;
import play.libs.Files;
import play.mvc.*;

import java.io.File;
import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
    	List<User> users = User.findAll();
    	
	    if(Security.isConnected()) {
	    	User currentUser = Security.connectedUser();
	    	
	    	List<FriendRequest> friendRequests = currentUser.receivedPendingRequests;
	    	renderArgs.put("friendRequests", friendRequests);
	    	
	    	List<User> buddies = currentUser.buddies;
	    	renderArgs.put("buddies", buddies);
	    }
	    
        render(users);
    }

    public static void explore() {
		List<Spot> spots = models.Spot.all().fetch();
		render(spots);
    }
    
	public static void search(String query) {
		String fullQueryString = "name:" + query; // + " OR country:" + query;
		//Query q = Search.search(fullQueryString, Spot.class);
		//List<Spot> spots = q.fetch();
		//render(query, spots);
		render("@index");
	}
	
	/**
	 * 
	 * @param id: ID of the user to send a friend request
	 */
	public static void sendFriendRequest(long id, String message) {
		User user = User.findById(id);
		User currentUser = Security.connectedUser();
		
		currentUser.sendRequest(user, message);
		
		index();
	}
	
	public static void acceptFriendRequest(long id) {
		User currentUser = Security.connectedUser();
		FriendRequest request = FriendRequest.findById(id);
		currentUser.respondRequest(request, FriendRequest.Status.ACCEPTED);
		index();
	}
    
	public static void rejectFriendRequest(long id) {
		User currentUser = Security.connectedUser();
		FriendRequest request = FriendRequest.findById(id);
		currentUser.respondRequest(request, FriendRequest.Status.REJECTED);
		index();
	}
	
	public static void renderThumbnail(long id) {
		Picture picture = Picture.findById(id);
		renderBinary(picture.imageOriginal.get());
	}
}