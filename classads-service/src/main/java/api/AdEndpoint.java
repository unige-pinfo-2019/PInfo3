package api;

import java.util.HashMap;
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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import domain.model.Ad;
import domain.model.Categories;
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
		Gson gson = new Gson();
		return gson.toJson(ads);
	}
	
	/* Add a new ad in the DB */
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String addNewAd(String jsonStr) {
		JsonObject json = new Gson().fromJson(jsonStr, JsonObject.class);
		Ad ad = new Ad(); //We create the ad
		
		//We need to decrypt the json object and instanciate the attributes of the ad
		
		try {
			//Some attributes are mandatory so they'll generate an exception if they don't exist
			
			//This includes the category id
			int categoryID = json.get("categoryID").getAsInt();
			
			//and the main attributes of an ad
			try {
				ad.setTitle(json.get("title").getAsString());
				ad.setDescription(json.get("description").getAsString());
				ad.setPrice(json.get("price").getAsInt());
			} catch (Exception e) {
				log.error("Mandatory fields are missing (title, description or price)");
			}
			
			//For the attributes related to the category, we take the value if it exists or we assign the
			//default value
			Map<String, Object> attributes = Categories.getCategory(categoryID);
			Map<String, Object> newAttributes = new HashMap<>();
			newAttributes.put("categoryID", categoryID);
			for (Map.Entry<String, Object> entry : attributes.entrySet()) {
				String key = entry.getKey();
				try {
					JsonElement att = json.get(key);
					if (getType(att) == attributes.get(key).getClass()) {
						newAttributes.put(key, json.get(key));
					} else {
						newAttributes.put(key, attributes.get(key));
					}
				} catch (Exception e) {}
			}
			
			ad.setCategory(newAttributes);
			
			return "You've inserted an ad\n" + ad.toString()
			;
		} catch (Exception e) {
			log.error("categoryID is missing or is not an Integer");
		}
		
		return "Some error occured.";
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
	
	/***** Manipulation *****/
	
	/* Find the type of a JsonElement (either boolean, string or integer) */
	private Object getType(JsonElement var) {
		if (var.getAsJsonPrimitive().isBoolean()) {
			return Boolean.class;
		}
		else if (var.getAsJsonPrimitive().isString()) {
			return String.class;
		}
		else if (var.getAsJsonPrimitive().isNumber()) {
			return Integer.class;
		}
		return null;
	}
}
