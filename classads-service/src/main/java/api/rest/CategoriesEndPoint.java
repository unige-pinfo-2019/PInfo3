package api.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

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
	
	/* Get a list of all the categories and their related indices */
	@GET
	@Path("index/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCategories() {
		Gson gson = new Gson(); 
		return gson.toJson(Categories.getCategoriesIndex()); 
	}
	

}
