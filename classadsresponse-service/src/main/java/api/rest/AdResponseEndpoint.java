package api.rest;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import domain.model.AdResponse;
import domain.service.AdResponseService;

/**
 * Restful api for the ads responses.
 */
@ApplicationScoped
@Path("/classadsresponses")
public class AdResponseEndpoint {
	@Inject
	private AdResponseService adservice;

	public void setAdResponseService(AdResponseService cs) {
		adservice = cs;
	}

	@GET
	@Path("/users/{uid}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AdResponse> getResponsesByUser(@PathParam("uid") long id) {
		return adservice.getByUser(id);
	}

	@GET
	@Path("users/{uid}/ads/{aid}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AdResponse> getResponseByUserAndAd(
			@PathParam("uid") long uid,
			@PathParam("aid") long aid,
			@QueryParam("offset") int offset,
			@QueryParam("limit") int limit) {
		return adservice.getResponsesFromiToj(uid, aid, offset, limit);
	}


	@POST
	@Path("/users/{uid}/ads/{aid}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addNewResponse(AdResponse adresp) {
		if (adresp != null) {
			adservice.createAdResponse(adresp);
			return Response.ok(adresp.getId(), MediaType.TEXT_PLAIN).build();
		} else {
			return Response.status(Response.Status.BAD_REQUEST).entity("Couldn't create ad response, please check your parameters").build();
		}
	}

}
