package domain.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public interface CategoriesService {
	
	public JsonObject getAttributes(int categoryID);
	public JsonObject getNameIndexCategories();
	public JsonArray getCategoriesTreeView();

}
