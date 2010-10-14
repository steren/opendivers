package controllers;

import models.User;
import play.mvc.*;

public class Security extends Controller {

	public static void register() {
		render("Secure/register.html");
	}
	
	public static void registerNew(String email, String username, String password) {
		System.out.println("register:" + email + "/" + username + "/" + password);
		User user = new User(email, username, password);
		user.save();
		
		Application.index();
	}
	
    static void onDisconnected() {
        Application.index();
    }
    
    static void onAuthenticated() {
        Application.index();
    }

}
