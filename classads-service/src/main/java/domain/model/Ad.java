package domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.JsonObject;

import lombok.Data;

/**
 * Represents classifier ads and manage storage in DB.
 */
@Entity
@Table(name="AD")
@Data
public abstract class Ad implements Serializable{

	private static final long serialVersionUID = 5725261213022717645L;
	
	private static String titleField = "title";
	private static String descriptionField = "description";
	private static String priceField = "price";

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="AD_ID")
	private long id;

	@Column(name="TITLE")
	private String title;

	@Column(name="DESCRIPTION")
	private String description;

	@Column(name="PRICE")
	private float price;

	@Column(name="CATEGORY_ID")
	private int categoryID;

	/***** Constructors *****/
	public Ad() {}

	/***** Manipulation *****/
	/* Returns attributes and their default values in a json format */
	public JsonObject getJSONAttributes() {
		JsonObject json = new JsonObject();
		json.addProperty(titleField, "text");
		json.addProperty(descriptionField, "text");
		json.addProperty(priceField, "number");
		return json;
	}

	/***** Utility methods *****/
	@Override
	public String toString() {
		String newLine = System.getProperty("line.separator");
		String res = "Ad ID : " + id + newLine;
		res += "Title : " + newLine + "Description : " + description + newLine + "Prix : " + price + newLine;
		res += "Category ID : " + categoryID + newLine; 
		return res;
	}

	/***** Getters and setters *****/
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
