package api;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import domain.model.Categories;
import domain.service.CategoriesService;

@Path("/")
public class CategoriesEndPoint {
	
	@Inject
	private CategoriesService catService;

	public void setCatService(CategoriesService catService) {
		this.catService = catService;
	}
	
	@GET
	@Path("categories/treeview")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCategoriesTreeView() {
		return catService.getCategoriesTreeView().toString();
	}
	
	@GET
	@Path("category/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCategoriesAttributes(@QueryParam("categoryID") int categoryID) {
		return catService.getAttributes(categoryID);
	}
	
	@GET
	@Path("categories/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCategories() {
		Gson gson = new Gson(); 
		return gson.toJson(Categories.getCategoryIndex()); 
	}
	

}
