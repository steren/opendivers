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

    static boolean authenticate(String username, String password) {
    	User user = User.find("userName=? and password=?", username, Crypto.passwordHash(password)).first();
    	if (user!=null) {
    		return true;
    	}
    	return false;
    }
}
