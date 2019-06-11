package domain.model;

import java.time.LocalDateTime;
import java.util.List;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class Ad {

	private static String searchTitleField = "title";
	private static String searchDescriptionField = "description";
	private static String searchPriceField = "price";
	private static String searchIdField = "id";
	private static String searchUserIDField = "userID";
	private static String searchCategoryIDField = "categoryID";
	private static String searchImageField = "images";
	private static String searchTimeField = "time";
	private static String searchNbVuesField = "nbVues";
	private static String searchDeletedField = "deleted";

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
		return searchTitleField;
	}

	public static String getDescriptionField() {
		return searchDescriptionField;
	}

	public static String getPriceField() {
		return searchPriceField;
	}

	public static String getIdField() {
		return searchIdField;
	}

	public static String getUserIDField() {
		return searchUserIDField;
	}

	public static String getCategoryIDField() {
		return searchCategoryIDField;
	}

	public static String getImageField() {
		return searchImageField;
	}

	public static String getTimeField() {
		return searchTimeField;
	}

	public static String getNbVuesField() {
		return searchNbVuesField;
	}

	public static String getDeletedField() {
		return searchDeletedField;
	}

	public static String getUsernameField() {
		return usernameField;
	}

}
