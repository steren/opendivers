package controllers;

import java.util.List;
import models.Dive;
import models.Spot;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;


public class Dives extends Controller {
	public static void index() {
		List<Dive> dives = models.Dive.all().fetch();
		render(dives);
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
	
	public static void save(@Valid Dive dive, long spotId, String spotName) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", dive);
		}

		Spot spot;
		// if Id is specified, use this, otherwise, create a new spot 
		if(spotId != 0) {
			spot = Spot.findById(spotId);
		} else {
			Spot existingSpot = Spot.find("byName", spotName).first();
			if(existingSpot != null) {
				spot = existingSpot;
			} else {
				spot = new Spot();
				spot.name = spotName;
				spot.save();
			}
		}
		dive.spot = spot;

		dive.save();

		flash.success(Messages.get("scaffold.created", "Dive"));
		show(dive.id);
	}

	public static void update(@Valid Dive dive, long spotId, String spotName) {
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
