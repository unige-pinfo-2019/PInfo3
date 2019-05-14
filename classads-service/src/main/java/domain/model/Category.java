package domain.model;

import lombok.Getter;

@Getter
public class Category {
	
	private String categoryName;
	private int categoryID;
	private String parent;
	
	public Category(String categoryName, int categoryID, String parent) {
		this.categoryName = categoryName;
		this.categoryID = categoryID;
		this.parent = parent;
	}

}
