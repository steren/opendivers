package controllers;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import models.Fish;
import models.Picture;
import models.Spot;
import models.User;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;


public class Spots extends Controller {

	public static final int NUMBER_OF_DIVER_TO_SHOW = 8;
	
	public static void index() {
		List<Spot> spots = models.Spot.all().fetch();
		render(spots);
	}

	public static void show(java.lang.Long id) {
		Spot spot = Spot.findById(id);
		
		int diveNumber = spot.getDiveNumber();
		List<Picture> pictures = spot.getPictures();
		
		List<User> divers = spot.getDivers();
		Collections.shuffle(divers);
		// Only return a subset of the divers
		divers.subList(0, Math.min(NUMBER_OF_DIVER_TO_SHOW, divers.size()));
		
		render(spot, diveNumber, pictures, divers);
	}
	
	/**
	 * Returns additional info to be displayed on the info window of a spot on the map
	 * @param id
	 */
	public static void getSpotAdditionalInfo(long id) {
		Spot spot = Spot.findById(id);
		renderText("<p>"+ spot.name + "</p>");
	}

}
