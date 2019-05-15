package domain.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Categories {
	
	/***** Static fields and code *****/
	private static String categoryIDField = "categoryID";
	private static Map<Integer, Category> categoriesList = new HashMap<>();
	
	static {
		String parent;
		addNewCategory(new Category("General", 0, null));
		
		int id = 1;		
		addNewCategory(new Category("Ordinateurs", id++, null));
		addNewCategory(new Category("Vélos", id++, null));
		addNewCategory(new Category("Vinyles", id++, null));
		addNewCategory(new Category("Livres", id++, null));
		
		parent = "Immobilier";
		addNewCategory(new Category(parent, id++, null));
		addNewCategory(new Category("Colocations", id++, parent));
		addNewCategory(new Category("Studios", id++, parent));
	}
	
	/***** Constructors *****/
	private Categories() {}
	
	/***** Manipulation *****/
	private static void addNewCategory(Category newCategory) {
		Categories.categoriesList.put(newCategory.getCategoryID(), newCategory);
	}
	
	/***** Getters and setters *****/
	public static Collection<Category> getCategories() {
		return Categories.categoriesList.values();
	}
	
	public static String getCategoryIDField() {
		return categoryIDField;
	}
	
	public static Set<Integer> getCategoriesIndex() {
		return categoriesList.keySet();
	}

}


