package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
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
    
}