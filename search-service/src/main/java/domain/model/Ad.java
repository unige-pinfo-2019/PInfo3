package domain.model;

import java.time.LocalDateTime;
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
	private static String timeField = "time";
	private static String nbVuesField = "nbVues";
	private static String deletedField = "deleted";
	private static String usernameField = "username";

	private long id;

	private String title;

	private String description;

	private float price;

	private String userID;
	
	private String username;

	private int categoryID;
	
	private List<String> images;

	private String time;
	
	private int nbVues = 0;
	
	private boolean deleted = false;
	
	public Ad(long id, String title, String description, float price, String userID, int categoryID, List<String> images) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.price = price;
		this.userID = userID;
		this.categoryID = categoryID;
		this.images = images;
		this.time = LocalDateTime.now().toString();
	}
	
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

	public static String getTimeField() {
		return timeField;
	}

	public static String getNbVuesField() {
		return nbVuesField;
	}

	public static String getDeletedField() {
		return deletedField;
	}
	
	public static String getUsernameField() {
		return usernameField;
	}

}
