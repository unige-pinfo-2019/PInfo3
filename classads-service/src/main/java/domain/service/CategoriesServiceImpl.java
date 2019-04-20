package domain.service;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domain.model.Categories;

@ApplicationScoped
public class CategoriesServiceImpl implements CategoriesService {
	
	private int getCategoryParent(int categoryID) {
		String parentName = (String) Categories.getCategoryStore().get(categoryID).get("parent");
		if (parentName == null) {
			return -1;
		}
		return Categories.getCategoryIndex().get(parentName);
	}

	@Override
	public String getAttributes(int categoryID) {
		JsonObject result = new JsonObject();
		
		try {
			int parent = categoryID;
			while (parent != -1) {
				Map<String, Object> attributes = Categories.getCategoryAttributes().get(parent);
				
				for (Map.Entry<String, Object> entry : attributes.entrySet()) {
					result.addProperty(entry.getKey(), entry.getValue().toString());
				}
				parent = getCategoryParent(parent);
			}
		} catch (Exception e) {
			System.err.println("Error in categoty ID");
		}
		
		return result.toString();
	}
	
	private JsonArray getChildren(int categoryID) {
		String categoryName = Categories.getCategoryName(categoryID);
		JsonArray children = new JsonArray();
		for (Map.Entry<Integer, Map<String, Object>> cat : Categories.getCategoryStore().entrySet()) {
			if ((String) cat.getValue().get("parent") == categoryName) {
				JsonObject child = new JsonObject();
				child.addProperty("name", (String) cat.getValue().get("categoryName"));
				child.add("children", getChildren(cat.getKey()));
				children.add(child);
			}
		}
		
		return children;		
	}

	@Override
	public JsonArray getCategoriesTreeView() {
		JsonArray tree = new JsonArray();
		for (Map.Entry<Integer, Map<String, Object>> cat : Categories.getCategoryStore().entrySet()) {
			if (cat.getValue().get("parent") == null) {
				JsonObject newCat = new JsonObject();
				newCat.addProperty("name", (String) cat.getValue().get("categoryName"));
				newCat.add("children", getChildren(cat.getKey()));
				
				tree.add(newCat);
			}
			
		}
		return tree;
	}

}
