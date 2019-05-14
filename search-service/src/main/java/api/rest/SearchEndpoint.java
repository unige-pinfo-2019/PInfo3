package api.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonArray;

import domain.service.SearchService;

@ApplicationScoped
@Path("/search")
public class SearchEndpoint {
	
	@Inject
	private SearchService searchService;
	
	public void setAdService(SearchService cs) {
		searchService = cs;
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getRequest(@QueryParam("request") String request) {
		JsonArray json = searchService.searchRequest(request);
		if (json != null)
			return json.toString();
		return "[]";
	}
	
	@DELETE
	@Path("/{toId}")
	public void deleteAds(@PathParam("toId") long toId) {
		for (long id=0; id<toId; id++) {
			searchService.deleteAdById(Long.toString(id));
		}
	}


}
