package controllers;

import play.*;
import play.db.jpa.Blob;
import play.libs.Files;
import play.mvc.*;

import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import javax.imageio.ImageIO;

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
		// TODO use search engine
		//String fullQueryString = "name:" + query; // + " OR country:" + query;
		//Query q = Search.search(fullQueryString, Spot.class);
		//List<Spot> spots = q.fetch();
		
		List<Spot> spots = Spot.find( "byNameLike", "%" + query.toLowerCase() + "%").fetch(50);
		int count = spots.size();
		render(query, count, spots);
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

	public static void renderNormalPicture(long id) {
		Picture picture = Picture.findById(id);
		renderBinary(picture.imageNormal.get());
	}
	
	public static void renderThumbnailPicture(long id) {
		Picture picture = Picture.findById(id);
		renderBinary(picture.imageThumbnail.get());
	}

	public static void renderIconPicture(long id) {
		Picture picture = Picture.findById(id);
		renderBinary(picture.imageIcon.get());
	}
	
	/**
	 * AJAX Suggests spots from an input text
	 * @param term : input text entered by the user
	 */
	public static void spotSuggest(String term) {
		// TODO: use search engine
		List<Spot> spots = Spot.find( "byNameLike", "%" + term.toLowerCase() + "%").fetch(50);
		render(spots);
	}
	
	public static void createFish(String wikipediaURL, String name, String binomial, String wikipediaImageURL) {

		/*
		InputStream input = null;
		FileOutputStream writeFile = null;

		try {
			URL url = new URL("http://www.google.fr/images/logos/ps_logo2.png");
			URLConnection connection = url.openConnection();
			int fileLength = connection.getContentLength();

			if (fileLength == -1) {
				Logger.error("Invalide URL or file.");
				return;
			}

			input = connection.getInputStream();
			writeFile = new FileOutputStream("img");
			byte[] buffer = new byte[1024];
			int read;

			while ((read = input.read(buffer)) > 0) {
				writeFile.write(buffer, 0, read);
			}
			writeFile.flush();
		} catch (IOException e) {
			Logger.error("Error while trying to download the file.");
			e.printStackTrace();
		} finally {
			try {
				writeFile.close();
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		*/

		// TODO check if the fish already doesn't exist.
		Fish fish = new Fish(wikipediaURL);
		fish.setName(name);
		fish.binomial = binomial;
		fish.wikipediaImageURL = wikipediaImageURL;
		fish.save();
		
		render(fish);
	}

}
