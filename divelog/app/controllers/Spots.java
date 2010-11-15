package controllers;

import java.util.List;
import models.Spot;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;


public class Spots extends Controller {
	public static void index() {
		List<Spot> spots = models.Spot.all().fetch();
		render(spots);
	}

	public static void show(java.lang.Long id) {
		Spot spot = Spot.findById(id);
		render(spot);
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
