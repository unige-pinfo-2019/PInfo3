package domain.service;

import com.google.gson.JsonArray;

public interface CategoriesService {
	
	public String getAttributes(int categoryID);
	public JsonArray getCategoriesTreeView();

}
