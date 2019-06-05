package api.rest;

import java.security.Principal;
import java.util.Optional;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.IDToken;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import api.msg.AdProducer;
import domain.model.Ad;
import domain.model.User;
import domain.service.AdService;
import domain.service.KeycloakService;
import lombok.extern.slf4j.Slf4j;


/**
 * Restful api for the ads.
 */
@ApplicationScoped
@Path("/classads")
@Slf4j
@Api(value = "classads", authorizations = {
	      @Authorization(value="sampleoauth", scopes = {})
})
public class AdEndpoint {

	@Inject
	private AdService adservice;

	@Inject
	private AdProducer adProducer;

	@Inject
	private KeycloakService kcService;

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
	public Response getAd(@PathParam("id") String strID, @Context HttpHeaders headers) {

		//We get the ad
		Optional<Ad> adOptional = adservice.getById(Long.parseLong(strID));
		if (adOptional.isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Couldn't extract the ad, the id may not exist").build();
		}
		Ad ad = adOptional.get();
		ad.setNbVues(ad.getNbVues()+1); //if the method is called the nb of vue rows
		adservice.update(ad);

		JsonObject json = adservice.createJsonRepresentation(ad);
		boolean auth = false;

		if (kcService.hasValidAuthentification(headers)) {
			String token = kcService.getToken(headers);
			User user = kcService.extractUserInfos(token);

			if (ad.getUserID().equals(user.getUserID())) {
				auth = true;
			}
		}

		json.addProperty(Ad.getAuthField(), auth);
		return Response.ok(json.toString()).build();
	}

	@Context SecurityContext securityContext;

	@GET
	@Path("/hola-secured")
	@Produces("text/plain")
	@ApiOperation("Returns a message that is only available for authenticated users")
	public String holaSecured() {
//	    // this will set the user id as userName
//	    // String userName = securityContext.getUserPrincipal().getName();
//
//	    if (securityContext.getUserPrincipal() instanceof KeycloakPrincipal) {
//	        @SuppressWarnings("unchecked")
//	        KeycloakPrincipal<KeycloakSecurityContext> kp = (KeycloakPrincipal<KeycloakSecurityContext>) securityContext.getUserPrincipal();
//
//
//	        // this is how to get the real userName (or rather the login name)
//	        String userName = kp.getKeycloakSecurityContext().getToken().getName();
//	        return "This is a Secured resource. You are logged as " + userName;
//	    } else {
//	    	try {
//	    		Principal p = securityContext.getUserPrincipal();
//
//	    		//String userName = securityContext.getUserPrincipal().getName();
//	    		return p.toString();
//	    	} catch (Exception e) {
//	    		return "Can't extract Name from security Context";
//	    	}
//	    }

		try {
			// obtain the caller principal.
			Principal callerPrincipal = securityContext.getUserPrincipal();

			return callerPrincipal.toString();

		} catch (Exception e) {
			return "Can't extract principal user";
		}






	}



	@PUT
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(String jsonStr, @Context HttpHeaders headers) {

		if (kcService.hasValidAuthentification(headers)) {
			String token = kcService.getToken(headers);
			User user = kcService.extractUserInfos(token);

			JsonObject json = new Gson().fromJson(jsonStr, JsonObject.class);
			Ad ad = adservice.createAdFromJson(json); //We create the ad

			//We extract the actual ad from the database
			Optional<Ad> adOptional = adservice.getById(ad.getId());
			if (adOptional.isEmpty()) {
				return Response.status(Response.Status.BAD_REQUEST).entity("The ad ID is incorrect").build();
			}
			Ad oldAd = adOptional.get();

			if (oldAd.getUserID().equals(user.getUserID())) {
				ad.setTime(oldAd.getTime());
				ad.setUserID(oldAd.getUserID());
				ad.setUsername(oldAd.getUsername());
				ad.setDeleted(oldAd.isDeleted());
				ad.setNbVues(oldAd.getNbVues());
				try {
					adservice.update(ad);
				}catch(IllegalArgumentException ex) {
					log.error(ex.toString());
					return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Some form of error occurred. Could not modifie "+ ad.toString()).build();
				}
				adProducer.send(ad);
				return Response.ok("the ad " + ad.getId() + " have been modified").build();
			}
			return Response.status(Response.Status.FORBIDDEN).entity("You don't have access to this resource").build();
		}
		return Response.status(Response.Status.FORBIDDEN).entity("There is no authorization header or the token is invalid").build();
	}


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
	public Response addNewAd(String jsonStr, @Context HttpHeaders headers) {

		if (kcService.hasValidAuthentification(headers)) {
			String token = kcService.getToken(headers);
			User user = kcService.extractUserInfos(token);

			JsonObject json = new Gson().fromJson(jsonStr, JsonObject.class);
			Ad ad = adservice.createAdFromJson(json); //We create the ad

			if (ad != null) {
				ad.setUserID(user.getUserID());
				ad.setUsername(user.getUsername());
				adservice.createAd(ad);
				adProducer.send(ad);
				return Response.ok(ad.getId(), MediaType.TEXT_PLAIN).build();
			} else {
				return Response.status(Response.Status.BAD_REQUEST).entity("Couldn't create ad, please check your parameters").build();
			}

		}
		return Response.status(Response.Status.FORBIDDEN).entity("There is no authorization header or the token is invalid").build();
	}

	/* Delete an ad according to its ID */
	@DELETE
	@Path("/ads/ad/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteAd(@PathParam("id") String strID, @Context HttpHeaders headers) {

		if (kcService.hasValidAuthentification(headers)) {
			String token = kcService.getToken(headers);
			User user = kcService.extractUserInfos(token);

			//Is expecting the id of the ad we want to delete
			long id = Long.parseLong(strID);

			Optional<Ad> popt = adservice.getById(id);
			if(popt.isEmpty()) {
				return Response.status(Response.Status.NOT_FOUND).entity("Error. There is no ad with ID "+ id).build();
			}
			else {
				Ad ad = popt.get();

				if (ad.getUserID().equals(user.getUserID())) {
					try {
						adservice.deleteAd(ad);
						adProducer.sendDelete(ad);
						return Response.ok("Deleted classadd "+ ad.toString()).build();
					} catch(IllegalArgumentException ex) {
						log.error(ex.toString());
						return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Some form of error occurred. Could not delete "+ ad.toString()).build();
					}
				}
				return Response.status(Response.Status.FORBIDDEN).entity("You don't have access to this resource").build();
			}

		}
		return Response.status(Response.Status.FORBIDDEN).entity("There is no authorization header or the token is invalid").build();
	}


}
