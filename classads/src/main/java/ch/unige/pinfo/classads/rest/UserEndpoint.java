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

import ch.unige.pinfo.classads.model.User;
import ch.unige.pinfo.classads.service.UserService;

@ApplicationScoped
@Path("/User")
public class UserEndpoint {
	@Inject
	private UserService userservice;
	
	public void setUserService(UserService us) {
		userservice = us;
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAll() {
		List<User> usl = userservice.getAll();
		
		Gson gson = new Gson();
		return gson.toJson(usl);
	}
	
	@POST
	@Path("/new")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String addNewUser(User u) {
		if(userservice.createUser(u)) {
			return "You inserted an ad ";
		} else {
			return "Error. This ad already exists";
		}
	}
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteUser(@QueryParam("id") String str_id) {
		long id = Long.parseLong(str_id);
		
		Optional<User> popt = userservice.getById(id);
		if (popt.isEmpty()) {
			return "Error. There is no user with ID " + id;
		}
		
		User u = popt.get();

		try {
			userservice.deleteUser(u);
			return "Deleted classadd "+ u.toString();
		} catch(IllegalArgumentException ex) {
			System.out.println(ex.toString());
			return "Some form of error occurred. Could not delete "+ u.toString();
		}
	}
	
}
