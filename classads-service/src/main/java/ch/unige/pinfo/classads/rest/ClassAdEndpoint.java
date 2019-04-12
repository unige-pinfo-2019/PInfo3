package ch.unige.pinfo.classads.rest;

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

import ch.unige.pinfo.classads.model.ClassAd;
import ch.unige.pinfo.classads.service.ClassAdService;

@ApplicationScoped
@Path("/ClassAd")
public class ClassAdEndpoint {
	@Inject
	private ClassAdService classadservice;

	public void setClassAdService(ClassAdService cs) {
		classadservice = cs;
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAll() {
		//We get the ads back in a list
		List<ClassAd> ads = classadservice.getAll();
		
		//We can transform a List in a JSON Array, the result looks like :
		//[	{attr1:val1, attr2:val2, ...}, 
		//	{...}, 
		//	..., 
		//	{...}	]
		Gson gson = new Gson();
		return gson.toJson(ads);
	}
	
	@POST
	@Path("/new/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String addNewClassAd(ClassAd ad) {
		//Is expecting a JSON object : { attr1 : val1, attr2 : val2, ...}
		//where the attributes should be exactly the attributes of the class classAd
		if(classadservice.createClassAd(ad)) {
			return "You inserted an ad ";
		} else {
			return "Error. This ad already exists";
		}
	}

	@DELETE
	@Path("/delete/")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteClassAd(@QueryParam("id") String str_id) {
		//Is expecting the id of the ad we want to delete
		long id = Long.parseLong(str_id);

		Optional<ClassAd> popt = classadservice.getById(id);
		if(popt.isEmpty()) {
			return "Error. There is no ad with ID "+ id;
		}

		ClassAd c = popt.get();

		try {
			classadservice.deleteClassAd(c);
			return "Deleted classadd "+ c.toString();
		} catch(IllegalArgumentException ex) {
			System.out.println(ex.toString());
			return "Some form of error occurred. Could not delete "+ c.toString();
		}
	}
}
