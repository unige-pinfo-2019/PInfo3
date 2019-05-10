package domain.model;

import lombok.Getter;

@Getter
public class Category {
	
	private String categoryName;
	private int categoryID;
	private String parent;
	private Class<?> className;
	
	public Category(String categoryName, int categoryID, String parent, Class<?> className) {
		this.categoryName = categoryName;
		this.categoryID = categoryID;
		this.parent = parent;
		this.className = className;
	}

}

	
