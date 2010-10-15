package controllers;

import models.User;
import play.libs.Crypto;
import play.mvc.*;

public class Security extends Secure.Security {

    static void onDisconnected() {
        Application.index();
    }
    
    static void onAuthenticated() {
        Application.index();
    }

    static boolean authenticate(String email, String password) {
    	User user = User.find("email=? and password=?", email, Crypto.passwordHash(password)).first();
    	if (user!=null) {
    		return true;
    	}
    	return false;
    }
    
    static User connectedUser() {
    	User user = User.find("byEmail", connected()).first();
    	if (user!=null) {
    		return user;
    	}
    	return null;
    }
    
    static String connectedUserName() {
    	return connectedUser().userName;
    }
}
