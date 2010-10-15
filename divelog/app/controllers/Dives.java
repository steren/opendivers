package controllers;

import java.util.List;
import models.Dive;
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
	
	public static void save(@Valid Dive dive) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", dive);
		}
    dive.save();
		flash.success(Messages.get("scaffold.created", "Dive"));
		index();
	}

	public static void update(@Valid Dive dive) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@edit", dive);
		}
		
      		dive = dive.merge();
		
		dive.save();
		flash.success(Messages.get("scaffold.updated", "Dive"));
		index();
	}

}
