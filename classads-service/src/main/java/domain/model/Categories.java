package domain.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import domain.categories.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Categories {
	
	private static String categoryIDField = "categoryID";
	private static Map<Integer, Category> categories = new HashMap<>();
	
	static {
		addNewCategory(new Category("General", 0, null, General.class));
		
		int id = 1;		
		addNewCategory(new Category("Ordinateurs", id++, null, Computer.class));
		addNewCategory(new Category("Vélos", id++, null, Bike.class));
		addNewCategory(new Category("Vinyles", id++, null, Vinyl.class));
		
		addNewCategory(new Category("Livres", id++, null, Book.class));
		addNewCategory(new Category("Livres de maths", id++, "Livres", MathBook.class));
		addNewCategory(new Category("Livres de physique", id++, "Livres", PhysicBook.class));
		
		addNewCategory(new Category("Immobilier", id++, null, Property.class));
		addNewCategory(new Category("Colocations", id++, "Immobilier", FlatShare.class));
		addNewCategory(new Category("Studios", id++, "Immobilier", Studio.class));
	}
	
	private static void addNewCategory(Category newCategory) {
		Categories.categories.put(newCategory.getCategoryID(), newCategory);
	}
	
	public static Collection<Category> getCategories() {
		return Categories.categories.values();
	}
	
	public static Set<Integer> getCategoriesID() {
		return categories.keySet();
	}
	
	public static Category getCategoryById(int id) {
		if (Categories.categories.containsKey(id))
			return Categories.categories.get(id);
		log.error("Try to get a category for which the ID doesn't exist");
		return null;
	}
	
	public static String getCategoryIDField() {
		return categoryIDField;
	}

}
