package domain.model;

import java.util.Map;

import lombok.Data;

@Data
public class Ad {

	private static String titleField = "title";
	private static String descriptionField = "description";
	private static String priceField = "price";

	private long id;

	private String title;

	private String description;

	private float price;

	private Map<String, Object> categoryAttributes;

	public static String getTitleField() {
		return titleField;
	}

	public static String getDescriptionField() {
		return descriptionField;
	}

	public static String getPriceField() {
		return priceField;
	}

	public String toString() {
		String newLine = System.getProperty("line.separator");
		String ret = newLine + title + " (Ad id = " + id + ")"+ newLine + newLine + description + newLine + newLine + "Prix : " + price;
		ret += newLine + "Other fields : " + categoryAttributes.toString();
		return ret;
	}

}
