package domain.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class Ad {

	private static String titleField = "title";
	private static String descriptionField = "description";
	private static String priceField = "price";
	private static String idField = "id";
	private static String userIDField = "userID";
	private static String categoryIDField = "categoryID";
	private static String imageField = "images";

	private long id;

	private String title;

	private String description;

	private float price;

	private long userID;

	private int categoryID;
	
	public Ad(long id, String title, String description, float price, long userID, int categoryID, List<String> images) {
		this.title = title;
		this.description = description;
		this.price = price;
		this.userID = userID;
		this.categoryID = categoryID;
		this.images = images;
		
	}
	

	private List<String> images;

	public static String getTitleField() {
		return titleField;
	}

	public static String getDescriptionField() {
		return descriptionField;
	}

	public static String getPriceField() {
		return priceField;
	}

	public static String getIdField() {
		return idField;
	}

	public static String getUserIDField() {
		return userIDField;
	}

	public static String getCategoryIDField() {
		return categoryIDField;
	}
	
	public static String getImageField() {
		return imageField;
	}
}
