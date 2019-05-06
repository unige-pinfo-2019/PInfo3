package api.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
		return searchService.searchResquet(request).toString();
	}

}
