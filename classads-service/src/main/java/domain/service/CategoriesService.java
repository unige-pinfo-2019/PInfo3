package domain.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public interface CategoriesService {
	
	public JsonArray getCategoriesTreeView();
	public JsonObject getNameIndexCategories();

}
