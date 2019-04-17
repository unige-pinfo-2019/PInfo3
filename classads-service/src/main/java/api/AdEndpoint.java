package api;

import java.util.List;
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

import domain.model.Ad;
import domain.service.AdService;

@ApplicationScoped
@Path("/classads")
public class AdEndpoint {
	@Inject
	private AdService adservice;

	public void setAdService(AdService cs) {
		adservice = cs;
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAll() {
		//We get the ads back in a list
		List<Ad> ads = adservice.getAll();
		
		//We can transform a List in a JSON Array, the result looks like :
		//[	{attr1:val1, attr2:val2, ...}, 
		//	{...}, 
		//	..., 
		//	{...}	]
		Gson gson = new Gson();
		return gson.toJson(ads);
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String addNewAd(Ad ad) {
		//Is expecting a JSON object : { attr1 : val1, attr2 : val2, ...}
		//where the attributes should be exactly the attributes of the class classAd
		if(adservice.createAd(ad)) {
			return "You inserted an ad ";
		} else {
			return "Error. This ad already exists";
		}
	}

	@DELETE
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteAd(@QueryParam("id") String str_id) {
		//Is expecting the id of the ad we want to delete
		long id = Long.parseLong(str_id);

		Optional<Ad> popt = adservice.getById(id);
		if(popt.isEmpty()) {
			return "Error. There is no ad with ID "+ id;
		}
		else {
			
			Ad ad;
			if (popt.isPresent()) {
				ad = popt.get();

				try {
					adservice.deleteAd(ad);
					return "Deleted classadd "+ ad.toString();
				} catch(IllegalArgumentException ex) {
					System.err.println(ex.toString());
					return "Some form of error occurred. Could not delete "+ ad.toString();
				}
			}
			return "Some form of error occurred.";
		}

		
	}
}
