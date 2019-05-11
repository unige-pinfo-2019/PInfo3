package domain.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domain.model.Categories;
import domain.model.Categories1;
import domain.model.Category;
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
	
	public JsonObject getAttributes(int categoryID) {
		Category cat = Categories.getCategoryById(categoryID);
		JsonObject json =  null;
		try {
			Method m = cat.getClassName().getMethod("getJSONAttributes");
			json = (JsonObject) m.invoke(null); 
		} catch (NoSuchMethodException | SecurityException | InvocationTargetException | IllegalAccessException e) {
			log.error("Couldn't extract category attributes. "+e.getMessage());
		} 
		return json;
	}
	
//	private JsonObject concatenateJson(JsonObject json1, JsonObject json2) {
//		for (String key : json2.keySet()) {
//			json1.add(key, json1.get(key));
//		}
//		return json1;
//	}

//	//***** Overrided methods *****
//	@Override
//	public JsonObject getAttributes(int categoryID) {
//		JsonObject catAttributes = new JsonObject();
//		try {
//			//We check if the category ID is correct
//			Collection<Integer> indices = Categories1.getCategoryIndex().values();
//			if (!indices.contains(categoryID)) {
//				throw new IllegalArgumentException("Bad categoryID");
//			}
//			
//			//We start the category itself then we move back up to the parents until a root category
//			int parent = categoryID;
//			while (parent != -1) {
//				
//				//We get back the attributes 
//				Map<String, Object> attributes = Categories1.getCategoryAttributes().get(parent);
//				
//				//and add them to our json object
//				for (Map.Entry<String, Object> entry : attributes.entrySet()) {
//					catAttributes.addProperty(entry.getKey(), entry.getValue().toString());
//				}
//				//We move to the next parent
//				parent = getCategoryParent(parent);
//			}
//		} catch (Exception e) {
//			log.error("Error in categoty ID");
//			return null;
//		}
//		
//		JsonObject adAttributes = Ad.getAttributes();
//	
//		for (Entry<String, JsonElement> entry : catAttributes.entrySet()) {
//			adAttributes.add(entry.getKey(), entry.getValue());
//		}
//
//		
//		return adAttributes;
//	}
	
	@Override
	public JsonArray getCategoriesTreeView() {
		JsonArray tree = new JsonArray();
		
		//We go through our category to find the root categories
		for (Map.Entry<Integer, Map<String, Object>> cat : Categories1.getCategoryStore().entrySet()) {
			if (cat.getValue().get(Categories1.getParentField()) == null) {
				//When we find one, we add it to our json array with its children
				JsonObject newCat = new JsonObject();
				newCat.addProperty(nameField, (String) cat.getValue().get(Categories1.getCategoryNameField()));
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
		String parentName = (String) Categories1.getCategoryStore().get(categoryID).get(Categories1.getParentField());
		
		//We return either -1 or the index
		if (parentName == null) {
			return -1;
		}
		return Categories1.getCategoryIndex().get(parentName);
	}
	
	/* Returns a JsonArray with the children categories of a specified category */
	private JsonArray getChildren(int categoryID) {
		//We get the name of the category
		
		String categoryName = Categories1.getCategoryName(categoryID);
		JsonArray children = new JsonArray();

		//We look for all children in the categories
		for (Map.Entry<Integer, Map<String, Object>> cat : Categories1.getCategoryStore().entrySet()) {
			String parent = (String) cat.getValue().get(Categories1.getParentField());
			if (parent != null && parent.equals(categoryName)) {
				JsonObject child = new JsonObject();
				child.addProperty(nameField, (String) cat.getValue().get(Categories1.getCategoryNameField()));
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
