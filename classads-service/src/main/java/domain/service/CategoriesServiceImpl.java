package domain.service;

import javax.enterprise.context.ApplicationScoped;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domain.model.Categories;
import domain.model.Category;


/**
 * Implementation of categories service. It is used to extract information about categories such as
 * a list of names, a tree view, a list of attributes for a specific category, ...
 *
 */
@ApplicationScoped
public class CategoriesServiceImpl implements CategoriesService {
	
	private String nameField = "name";
	private String childrenField = "children";
	
	public JsonObject getNameIndexCategories() {
		JsonObject json = new JsonObject();
		for (Category cat : Categories.getCategories()) {
			json.addProperty(cat.getCategoryName(), cat.getCategoryID());
		}
		return json;
	}

	@Override
	public JsonArray getCategoriesTreeView() {
		JsonArray tree = new JsonArray();
		
		for (Category cat : Categories.getCategories()) {
			if (cat.getParent() == null) {
				JsonObject newCat = new JsonObject();
				newCat.addProperty(nameField, cat.getCategoryName());
				newCat.add(childrenField, getChildren(cat.getCategoryName()));
				tree.add(newCat);
			}
		}
		
		return tree;
	}
	
	/***** Manipulation *****
	/* Returns a JsonArray with the children categories of a specified category */
	private JsonArray getChildren(String categoryName) {
		JsonArray children = new JsonArray();

		//We look for all children in the categories
		for (Category cat : Categories.getCategories()) {
			String parent = cat.getParent();
			if (parent != null && parent.equals(categoryName)) {
				JsonObject child = new JsonObject();
				child.addProperty(nameField, cat.getCategoryName());
				
				//We call the function recursively to get the children of the subcategory
				child.add(childrenField, getChildren(cat.getCategoryName()));
				children.add(child);
			}
		}
		
		return children;		
	}
	
	/***** Getters and setters *****/
	public String getNameField() {
		return nameField;
	}

	public String getChildrenField() {
		return childrenField;
	}

}