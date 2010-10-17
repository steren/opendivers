package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }

    
	public static void search(String query) {
		//TODO Steren : this query string construction is temporary, we should better handle this
		//String fullQueryString = "content:" + query + " OR tags:" + query + " OR category:" + query;
		//Query q = Search.search(fullQueryString, Insight.class);
		//List<Insight> insights = q.fetch();
		//render(query, insights);
		render("@index");
	}
    
}