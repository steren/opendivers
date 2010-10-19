import java.util.Locale;

import play.*;
import play.jobs.*;
import play.test.*;
 
import models.*;
 
@OnApplicationStart
public class Bootstrap extends Job {
 
    public void doJob() {
    	
    	// Check if the database is empty
        if(User.count() == 0) {
        	
        	// Create the Countries
            Locale[] locales = Locale.getAvailableLocales();
            for (Locale locale : locales) {
                String code = locale.getCountry();
        	if (code != "") {
	              Country country = new Country(code);
	              country.save();
              }
            }
            
            // load some initial data
            Fixtures.load("initial-data.yml");
            
            Fixtures.load("initial-data-fishes.yml");
        }
        
    }
}
