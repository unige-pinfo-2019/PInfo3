package api;

import java.util.Map.Entry;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import domain.model.Ad;
import domain.model.Categories;
import domain.service.CategoriesService;

/**
 * Restful api for the categories.
 */
@Path("/categories")
public class CategoriesEndPoint {
	
	@Inject
	private CategoriesService catService;

	public void setCatService(CategoriesService catService) {
		this.catService = catService;
	}
	
	/* Get the tree view of the categories */
	@GET
	@Path("treeview/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCategoriesTreeView() {
		return catService.getCategoriesTreeView().toString();
	}
	
	/* Get all attributes (including those for ad class) of a specific category */
	@GET
	@Path("all/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCategoriesAttributes(@QueryParam("categoryID") int categoryID) {
		JsonObject adAttributes = Ad.getAttributes();
		JsonObject catAttributes = catService.getAttributes(categoryID);
		
		for (Entry<String, JsonElement> entry : catAttributes.entrySet()) {
			adAttributes.add(entry.getKey(), entry.getValue());
		}


		return adAttributes.toString();
	}
	
	/* Get a list of all the categories and their related indices */
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCategories() {
		Gson gson = new Gson(); 
		return gson.toJson(Categories.getCategoryIndex()); 
	}
	

}
