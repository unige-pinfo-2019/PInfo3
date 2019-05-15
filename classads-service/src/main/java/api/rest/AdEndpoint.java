package api.rest;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

	@GET
	@Path("/ads/ad/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAd(@PathParam("id") String strID) {
		Optional<Ad> ad = adservice.getById(Long.parseLong(strID));
		if (ad.isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Couldn't extract the ad, the id may not exist").build();
		}
		return Response.ok(adservice.createJsonRepresentation(ad.get()).toString()).build();


	@GET
	@Path("/categories/{cID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllByCategory(@PathParam("cID") int cid) {
		return adservice.getJsonListAds(adservice.getAllByCategory(cid)).toString();
	}

	/* Add a new ad in the DB */
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response addNewAd(String jsonStr) {
		JsonObject json = new Gson().fromJson(jsonStr, JsonObject.class);
		Ad ad = adservice.createAdFromJson(json); //We create the ad
		if (ad != null) {
			adservice.createAd(ad);
			adProducer.send(ad);
			return Response.ok(ad.getId(), MediaType.TEXT_PLAIN).build();
		} else {
			return Response.status(Response.Status.BAD_REQUEST).entity("Couldn't create ad, please check your parameters").build();
		}

	}

	/* Delete an ad according to its ID */
	@DELETE
	@Path("/ads/ad/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteAd(@PathParam("id") String strID) {
		//Is expecting the id of the ad we want to delete
		long id = Long.parseLong(strID);

		Optional<Ad> popt = adservice.getById(id);
		if(popt.isEmpty()) {
			return Response.status(Response.Status.NOT_FOUND).entity("Error. There is no ad with ID "+ id).build();
		}
		else {
			Ad ad = popt.get();

			try {
				adservice.deleteAd(ad);
				adProducer.sendDelete(ad);
				return Response.ok("Deleted classadd "+ ad.toString()).build();
			} catch(IllegalArgumentException ex) {
				log.error(ex.toString());
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Some form of error occurred. Could not delete "+ ad.toString()).build();
			}
		}


	}


}
