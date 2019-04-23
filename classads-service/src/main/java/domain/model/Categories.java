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
	private static ArrayList<Map<String, Object>> categories = new ArrayList<Map<String, Object>>() {
		private static final long serialVersionUID = -9048579182940936998L;
	{
		add(new HashMap<String, Object>() {
			private static final long serialVersionUID = -4825294092867421792L;
		{
			put("categoryName", "General");
		}});
		add(new HashMap<String, Object>() {
			private static final long serialVersionUID = -4825294092867421792L;
		{
			put("categoryName", "Books");
			put("parent", null);
			put("authors", "");
			put("nbPages", 0);
		}});
		
		add(new HashMap<String, Object>() {
			private static final long serialVersionUID = 1132122537391987013L;
		{
			put("categoryName", "Math Books");
			put("parent", "Books");
			put("Theme", "");
		}});
		
		add(new HashMap<String, Object>() {
			private static final long serialVersionUID = -2194122124855841116L;
		{
			put("categoryName", "Computers");
			put("parent", null);
			put("size", 0);
			put("memory", 0);
		}});
		
		add(new HashMap<String, Object>() {
			private static final long serialVersionUID = 5685199085258040747L;
		{
			put("categoryName", "Bikes");
			put("parent", null);
			put("type", "");
			put("color", "");
		}});
		
	}};
	
	/***** Other attributes (automatically computed) *****/
	private static Map<Integer, Map<String, Object>> categoryAttributes = new HashMap<Integer, Map<String, Object>>();
	private static Map<Integer, Map<String, Object>> categoryStore = new HashMap<Integer, Map<String, Object>>();
	private static Map<String, Integer> categoryIndex = new HashMap<String, Integer>();
	
	/***** Static code (will be run once) to build the 3 previous attributes *****/
	static {
		int id = 0;
		for (Map<String, Object> map : categories) {
			
			Map<String, Object> newMap = new HashMap<String, Object>();
			Map<String, Object> newMapStore= new HashMap<String, Object>();
			for (String key : map.keySet()) {
				newMap.put(key, map.get(key));
				newMapStore.put(key, map.get(key));
			}
	
			newMap.remove("categoryName");
			newMap.remove("parent");
			categoryAttributes.put(id, newMap);
			
			categoryStore.put(id, newMapStore);
			categoryIndex.put((String) newMapStore.get("categoryName"), id);
			id += 1;
		}
	}
	
	/***** Custom getters and setters *****/
	public static Set<String> getCategories() {
		return categoryIndex.keySet();
	}
	
	public static Map<String, Object> getCategory(int categoryID) {
		return categoryStore.get(categoryID);
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

}

