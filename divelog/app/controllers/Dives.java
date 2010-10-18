package controllers;

import java.util.ArrayList;
import java.util.List;
import models.Dive;
import models.Spot;
import models.User;
import play.mvc.Before;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;

public class Dives extends Controller {
	
	@Before(unless="show")
	static void checkAuthentification() {
	    if(session.get("user") == null) {
	    	Application.index();
	    }
	}
	
	public static void index() {
		User currentUser = Security.connectedUser();
		
		renderArgs.put("dives", currentUser.dives);
		render();
	}

	public static void create(Dive dive) {
		render(dive);
	}

	public static void show(java.lang.Long id) {
		Dive dive = Dive.findById(id);
		render(dive);
	}

	public static void edit(java.lang.Long id) {
		Dive dive = Dive.findById(id);
		render(dive);
	}

	public static void delete(java.lang.Long id) {
		Dive dive = Dive.findById(id);
		dive.delete();
		index();
	}
	
	public static void save(@Valid Dive dive, @Valid Spot spot, long spotId) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", dive, spot);
		}
		
		// if Id is specified, use this, otherwise, create a new spot 
		if(spotId != 0) {
			dive.spot = Spot.findById(spotId);
		} else {
			spot.save();
			dive.spot = spot;
		}
		dive.save();
		
		User currentUser = Security.connectedUser();
		if(currentUser.dives == null) {
			currentUser.dives = new ArrayList<Dive>();
		}
		currentUser.dives.add(dive);
		currentUser.save();

		flash.success(Messages.get("scaffold.created", "Dive"));
		show(dive.id);
	}

	public static void update(@Valid Dive dive, @Valid Spot spot, long spotId) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@edit", dive);
		}
		
      	dive = dive.merge();
		
		dive.save();
		flash.success(Messages.get("scaffold.updated", "Dive"));
		show(dive.id);
	}

}
