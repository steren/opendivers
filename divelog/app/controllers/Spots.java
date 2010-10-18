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

}
