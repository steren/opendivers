package controllers;

import java.util.List;
import models.Dive;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;


public class Dives extends Controller {
	public static void index() {
		List<Dive> entities = models.Dive.all().fetch();
		render(entities);
	}

	public static void create(Dive entity) {
		render(entity);
	}

	public static void show(java.lang.Long id) {
    Dive entity = Dive.findById(id);
		render(entity);
	}

	public static void edit(java.lang.Long id) {
    Dive entity = Dive.findById(id);
		render(entity);
	}

	public static void delete(java.lang.Long id) {
    Dive entity = Dive.findById(id);
    entity.delete();
		index();
	}
	
	public static void save(@Valid Dive entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", entity);
		}
    entity.save();
		flash.success(Messages.get("scaffold.created", "Dive"));
		index();
	}

	public static void update(@Valid Dive entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@edit", entity);
		}
		
      		entity = entity.merge();
		
		entity.save();
		flash.success(Messages.get("scaffold.updated", "Dive"));
		index();
	}

}
