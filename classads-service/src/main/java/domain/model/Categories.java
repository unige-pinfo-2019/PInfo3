package domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

@SuppressWarnings("serial")
public class Categories {
	
	private static ArrayList<Map<String, Object>> categories = new ArrayList<Map<String, Object>>() {{
		add(new HashMap<String, Object>() {{
			put("categoryID", 0);
			put("categoryName", "Books");
			put("parent", null);
			put("authors", "");
			put("nbPages", 0);
		}});
		
		add(new HashMap<String, Object>() {{
			put("categoryID", 3);
			put("categoryName", "Math Books");
			put("parent", "Books");
			put("Theme", "");
		}});
		
		add(new HashMap<String, Object>() {{
			put("categoryID", 1);
			put("categoryName", "Computers");
			put("parent", null);
			put("size", 0);
			put("memory", 0);
		}});
		
		add(new HashMap<String, Object>() {{
			put("categoryID", 2);
			put("categoryName", "Bikes");
			put("parent", null);
			put("type", "");
			put("color", "");
		}});
		
	}};
	
	private static Map<String, Map<String, Object>> categoryAttributes = new HashMap<String, Map<String, Object>>();
	private static Map<String, Map<String, Object>> categoryStore = new HashMap<String, Map<String, Object>>();
	static {
		for (Map<String, Object> map : categories) {
			String categoryName = (String) map.get("categoryName");
			
			Map<String, Object> newMap = new HashMap<String, Object>();
			Map<String, Object> newMapStore= new HashMap<String, Object>();
			for (String key : map.keySet()) {
				newMap.put(key, map.get(key));
				newMapStore.put(key, map.get(key));
			}
			newMap.remove("categoryName");
			newMap.remove("categoryID");
			newMap.remove("parent");
			categoryAttributes.put(categoryName, newMap);
			categoryStore.put(categoryName, newMapStore);
		}
	}
	

	
	public static String getAttributes(String categoryName) {
		Set<String> categoriesName = getCategories();
		if (categoriesName.contains(categoryName)) {
			
			JsonObject result = new JsonObject();
			String parent = categoryName;
			while (parent != null) {
				Map<String, Object> attributes = categoryAttributes.get(parent);
				
				for (Map.Entry<String, Object> entry : attributes.entrySet()) {
					result.addProperty(entry.getKey(), entry.getValue().toString());
				}
				parent = (String) categoryStore.get(parent).get("parent");
			}
			return result.toString();
		} else {
			return "This is not a category";
		}
		
	}
	
	public static String getCategoriesTreeView() {
		JsonArray result = new JsonArray();
		for (Map.Entry<String, Map<String, Object>> cat : categoryStore.entrySet()) {
			if (cat.getValue().get("parent") == null) {
				JsonObject newCat = new JsonObject();
				String categoryName = (String) cat.getValue().get("categoryName");
				newCat.addProperty("name", categoryName);
				
				JsonObject children = new JsonObject();
				int i = 0;
				for (Map.Entry<String, Map<String, Object>> cat1 : categoryStore.entrySet()) {
					if (cat1.getValue().get("parent") == categoryName) {
						children.addProperty(Integer.toString(i), (String) cat1.getValue().get("categoryName"));
						i += 1;
					}
				}
				newCat.add("children", children);
				
				result.add(newCat);
			}
			
		}
		return result.toString();
	}
	
	public static Set<String> getCategories() {
		return categoryAttributes.keySet();
	}
	
	public static Map<String, Map<String, Object>> getCategoryAttributes() {
		return categoryAttributes;
	}

}

