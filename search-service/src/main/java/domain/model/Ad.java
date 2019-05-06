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

	private Map<String, Integer> categoryInt;				// integer attributes specific to the category

	private Map<String, Boolean> categoryBool;				// boolean attributes specific to the category

	private Map<String, String> categoryString;				// string attributes specific to the category
	
	public void setCategory(Map<String, Integer> categoryInt, Map<String, Boolean> categoryBool, Map<String, String> categoryString) {
		this.categoryInt = categoryInt;
		this.categoryBool = categoryBool;
		this.categoryString = categoryString;
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
	
	public String toString() {
		String newLine = System.getProperty("line.separator");
		String ret = newLine + title + " (Ad id = " + id + ")"+ newLine + newLine + description + newLine + newLine + "Prix : " + price;
		ret += newLine + "Other fields : " + categoryInt.toString() + categoryBool.toString() + categoryString.toString();
		return ret;
	}

}
