package domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Represents all possible categories, their names and their attributes are hard coded in the ArrayList
 * categories.
 *
 */
public class Categories {
	
	/***** Attributes *****/
	private static ArrayList<Map<String, Object>> categoriesList = new ArrayList<>();
	private static String categoryNameField = "categoryName";
	private static String parentField = "parent";
	
	/***** Other attributes (automatically computed) *****/
	private static Map<Integer, Map<String, Object>> categoryAttributes = new HashMap<>();
	private static Map<Integer, Map<String, Object>> categoryStore = new HashMap<>();
	private static Map<String, Integer> categoryIndex = new HashMap<>();
	
	/***** Constructors *****/
	private Categories() {}
	
	/***** Defining the list of categories *****/	
	static {
		
		addCategory("General", null);
		addCategory("Books", null, "authors", "", "nbPages", 0);
		addCategory("Math Books", "Books", "Theme", "");
		addCategory("Computers", null, "size", 0, "memory", 0);
		addCategory("Bikes", null, "type", "", "color", "");
		
	
		int id = 0;
		for (Map<String, Object> map : categoriesList) {
			
			Map<String, Object> newMap = new HashMap<>();
			Map<String, Object> newMapStore= new HashMap<>();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();
				newMap.put(key, map.get(key));
				newMapStore.put(key, map.get(key));
			}
	
			newMap.remove(categoryNameField);
			newMap.remove(parentField);
			categoryAttributes.put(id, newMap);
			
			categoryStore.put(id, newMapStore);
			categoryIndex.put((String) newMapStore.get(categoryNameField), id);
			id += 1;
		}
	}
	
	/***** Manipulation *****/
	private static void addCategory(String categoryName, String parent, Object... objects) {
		Map<String, Object> attributes = new HashMap<>();
		attributes.put(categoryNameField, categoryName);
		attributes.put(parentField, parent);
		
		for (int i=0; i < objects.length; i=i+2) {
			attributes.put((String)objects[i], objects[i+1]);
		}
		categoriesList.add(attributes);
		
	}
	

	/***** Custom getters and setters *****/
	public static Set<String> getCategories() {
		return categoryIndex.keySet();
	}
	
	public static Map<String, Object> getCategory(int categoryID) {
		return categoryAttributes.get(categoryID);
	}
	
	public static String getCategoryName(int categoryID) {
		return (String) Categories.getCategoryStore().get(categoryID).get("categoryName");
	}
	
	/***** Getters and setters *****/
	public static Map<Integer, Map<String, Object>> getCategoryAttributes() {
		return categoryAttributes;
	}
	
	public static Map<Integer, Map<String, Object>> getCategoryStore() {
		return categoryStore;
	}

	public static Map<String, Integer> getCategoryIndex() {
		return categoryIndex;
	}
	
	public static String getCategoryNameField() {
		return categoryNameField;
	}

	public static String getParentField() {
		return parentField;
	}

}

