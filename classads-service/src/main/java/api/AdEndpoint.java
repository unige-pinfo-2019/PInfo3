package api;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import domain.model.Ad;
import domain.service.AdService;
import lombok.extern.slf4j.Slf4j;


/**
 * Restful api for the ads.
 */
@ApplicationScoped
@Path("/classads")
@Slf4j
public class AdEndpoint {
	@Inject
	private AdService adservice;

	public void setAdService(AdService cs) {
		adservice = cs;
	}

	/* Get all classads */
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAll() {
		//We get the ads back in a list
		List<Ad> ads = adservice.getAll();
		JsonArray result = new JsonArray();
		
		for (Ad ad : ads) {
			JsonObject jsonAd = new JsonObject();
			jsonAd.addProperty("title", ad.getTitle());
			jsonAd.addProperty("description", ad.getDescription());
			jsonAd.addProperty("price", ad.getPrice());
			for (Map.Entry<String, Integer> entryInt : ad.getCategoryInt().entrySet()) {
				jsonAd.addProperty(entryInt.getKey(), entryInt.getValue());
			}
			for (Map.Entry<String, Boolean> entryBool : ad.getCategoryBool().entrySet()) {
				jsonAd.addProperty(entryBool.getKey(), entryBool.getValue());
			}
			for (Map.Entry<String, String> entryString : ad.getCategoryString().entrySet()) {
				jsonAd.addProperty(entryString.getKey(), entryString.getValue());
			}
			result.add(jsonAd);
		}
		
		return result.toString();
	}
	
	/* Add a new ad in the DB */
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String addNewAd(String jsonStr) {
		JsonObject json = new Gson().fromJson(jsonStr, JsonObject.class);
		Ad ad = adservice.createAdFromJson(json); //We create the ad
		
		if (ad != null && adservice.createAd(ad)) {
			return "You've inserted an ad\n" + ad.toString();
		} else {
			return "This ad already exists";
		}
		
	}

	/* Delete an ad according to its ID */
	@DELETE
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteAd(@QueryParam("id") String strID) {
		//Is expecting the id of the ad we want to delete
		long id = Long.parseLong(strID);

		Optional<Ad> popt = adservice.getById(id);
		if(popt.isEmpty()) {
			return "Error. There is no ad with ID "+ id;
		}
		else {
			Ad ad = popt.get();

			try {
				adservice.deleteAd(ad);
				return "Deleted classadd "+ ad.toString();
			} catch(IllegalArgumentException ex) {
				log.error(ex.toString());
				return "Some form of error occurred. Could not delete "+ ad.toString();
			}
		}

		
	}
	
	
}
