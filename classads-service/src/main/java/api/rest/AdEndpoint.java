package api.rest;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import api.msg.AdProducer;
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
	
	@Inject
	private AdProducer adProducer;
	
	public void setAdService(AdService cs) {
		adservice = cs;
	}
	
	
	/* Get all classads */
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAll() {
		//We get the ads back in a list
		return adservice.getJsonListAds(adservice.getAll()).toString();
	}
	
	/* Add a new ad in the DB */
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String addNewAd(String jsonStr) {
		JsonObject json = new Gson().fromJson(jsonStr, JsonObject.class);
		Ad ad = adservice.createAdFromJson(json); //We create the ad
		if (ad != null) {
			adservice.createAd(ad);
			//adProducer.send(ad);
			return "You've inserted an ad\n" + ad.toString();
		} else {
			return "Unable to create ad. Please check your parameters.";
		}
		
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateAd(String jsonStr, @QueryParam("id") long id) {
		JsonObject json = new Gson().fromJson(jsonStr, JsonObject.class);
		Ad ad = adservice.createAdFromJson(json); //We create the ad
		if (adservice.updateAd(ad, id)) {
			return "Ad updated with success";
		} 
		return "Some error occured when updating the ad (maybe the id doesn't exist)";
	}
	
	@DELETE
	@Path("/all")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteAllAd() {
		List<Ad> ads = adservice.getAll();
		for (Ad ad : ads) {
			try {
				adservice.deleteAd(ad);
				//adProducer.sendDelete(ad);
				log.info("Deleted classad "+ ad.toString());
			} catch(IllegalArgumentException ex) {
				log.error(ex.toString());
				log.info("Some form of error occurred. Could not delete "+ ad.toString());
			}
		}
		return "Delete all ads finished";
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
				//adProducer.sendDelete(ad);
				return "Deleted classadd "+ ad.toString();
			} catch(IllegalArgumentException ex) {
				log.error(ex.toString());
				return "Some form of error occurred. Could not delete "+ ad.toString();
			}
		}

		
	}
	
	
}
