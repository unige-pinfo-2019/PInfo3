package domain.service;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import javax.enterprise.context.ApplicationScoped;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import domain.model.Ad;
import domain.model.Categories;
import lombok.extern.slf4j.Slf4j;


/**
 * Implementation of categories service. It is used to extract information about categories such as
 * a list of names, a tree view, a list of attributes for a specific category, ...
 *
 */
@ApplicationScoped
@Slf4j
public class CategoriesServiceImpl implements CategoriesService {
	
	private String nameField = "name";
	private String childrenField = "children";

	//***** Overrided methods *****
	@Override
	public JsonObject getAttributes(int categoryID) {
		JsonObject catAttributes = new JsonObject();
		try {
			//We check if the category ID is correct
			Collection<Integer> indices = Categories.getCategoryIndex().values();
			if (!indices.contains(categoryID)) {
				throw new IllegalArgumentException("Bad categoryID");
			}
			
			//We start the category itself then we move back up to the parents until a root category
			int parent = categoryID;
			while (parent != -1) {
				
				//We get back the attributes 
				Map<String, Object> attributes = Categories.getCategoryAttributes().get(parent);
				
				//and add them to our json object
				for (Map.Entry<String, Object> entry : attributes.entrySet()) {
					catAttributes.addProperty(entry.getKey(), entry.getValue().toString());
				}
				//We move to the next parent
				parent = getCategoryParent(parent);
			}
		} catch (Exception e) {
			log.error("Error in categoty ID");
			return null;
		}
		
		JsonObject adAttributes = Ad.getAttributes();
	
		for (Entry<String, JsonElement> entry : catAttributes.entrySet()) {
			adAttributes.add(entry.getKey(), entry.getValue());
		}

		
		return adAttributes;
	}
	
	@Override
	public JsonArray getCategoriesTreeView() {
		JsonArray tree = new JsonArray();
		
		//We go through our category to find the root categories
		for (Map.Entry<Integer, Map<String, Object>> cat : Categories.getCategoryStore().entrySet()) {
			if (cat.getValue().get(Categories.getParentField()) == null) {
				//When we find one, we add it to our json array with its children
				JsonObject newCat = new JsonObject();
				newCat.addProperty(nameField, (String) cat.getValue().get(Categories.getCategoryNameField()));
				newCat.add(childrenField, getChildren(cat.getKey()));
				tree.add(newCat);
			}
			
		}
		
		return tree;
	}
	
	//***** Manipulation *****
	/* Returns the index of the parent category or -1 if there is no parent */
	private int getCategoryParent(int categoryID) {
		//We get the parent
		String parentName = (String) Categories.getCategoryStore().get(categoryID).get(Categories.getParentField());
		
		//We return either -1 or the index
		if (parentName == null) {
			return -1;
		}
		return Categories.getCategoryIndex().get(parentName);
	}
	
	/* Returns a JsonArray with the children categories of a specified category */
	private JsonArray getChildren(int categoryID) {
		//We get the name of the category
		
		String categoryName = Categories.getCategoryName(categoryID);
		JsonArray children = new JsonArray();

		//We look for all children in the categories
		for (Map.Entry<Integer, Map<String, Object>> cat : Categories.getCategoryStore().entrySet()) {
			String parent = (String) cat.getValue().get(Categories.getParentField());
			if (parent != null && parent.equals(categoryName)) {
				JsonObject child = new JsonObject();
				child.addProperty(nameField, (String) cat.getValue().get(Categories.getCategoryNameField()));
				//We call the function recursively to get the children of the subcategory
				
				child.add(childrenField, getChildren(cat.getKey()));
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
