package domain.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.ElementCollection;

import lombok.Data;

@Data
public class AdSearchable {
	
	private static String titleField = "title";
	private static String descriptionField = "description";
	private static String priceField = "price";

	private long id;

	private String title;

	private String description;

	private float price;

	private Map<String, Object> categoryAttributes = new HashMap<>();

	public static String getTitleField() {
		return titleField;
	}

	public static String getDescriptionField() {
		return descriptionField;
	}

	public static String getPriceField() {
		return priceField;
	}

}
