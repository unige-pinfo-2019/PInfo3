package domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import domain.categories.Books;
import domain.categories.Computers;
import domain.categories.General;
import domain.categories.MathBooks;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Categories {
	
	private static ArrayList<Category> categories = new ArrayList<>();
	private static Map<Integer, Category> categoriesIndex = new HashMap<>();
	
	static {
		int id = 0;
		categories.add(new Category("General", id, null, General.class)); id++;
		categories.add(new Category("Books", id, null, Books.class)); id++;
		categories.add(new Category("Maths Books", id, null, MathBooks.class)); id++;
		categories.add(new Category("Computers", id, null, Computers.class)); id++;
		
		for (Category cat : categories) {
			categoriesIndex.put(cat.getCategoryID(), cat);
		}
	}
	
	public static ArrayList<Category> getCategories() {
		return Categories.categories;
	}
	
	public static Category getCategoryById(int id) {
		if (Categories.categoriesIndex.containsKey(id))
			return Categories.categoriesIndex.get(id);
		log.error("Try to get a category for which the ID doesn't exist");
		return null;
	}

}
