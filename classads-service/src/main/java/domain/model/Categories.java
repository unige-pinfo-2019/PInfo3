package domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
public class Categories {
	
	private static ArrayList<Map<String, Object>> categories = new ArrayList<Map<String, Object>>() {{
		add(new HashMap<String, Object>() {{
			put("categoryName", "Books");
			put("parent", null);
			put("authors", "");
			put("nbPages", 0);
		}});
		
		add(new HashMap<String, Object>() {{
			put("categoryName", "Math Books");
			put("parent", "Books");
			put("Theme", "");
		}});
		
		add(new HashMap<String, Object>() {{
			put("categoryName", "Computers");
			put("parent", null);
			put("size", 0);
			put("memory", 0);
		}});
		
		add(new HashMap<String, Object>() {{
			put("categoryName", "Bikes");
			put("parent", null);
			put("type", "");
			put("color", "");
		}});
		
	}};
	
	private static Map<Integer, Map<String, Object>> categoryAttributes = new HashMap<Integer, Map<String, Object>>();
	private static Map<Integer, Map<String, Object>> categoryStore = new HashMap<Integer, Map<String, Object>>();
	private static Map<String, Integer> categoryIndex = new HashMap<String, Integer>();
	

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
	
	public static Set<String> getCategories() {
		return categoryIndex.keySet();
	}
	
	public static String getCategoryName(int categoryID) {
		return (String) Categories.getCategoryStore().get(categoryID).get("categoryName");
	}
	
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

