package domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.google.gson.JsonObject;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Represents classifier ads and manage storage in DB.
 */
@Entity
@Table(name="AD")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="TYPE", discriminatorType = DiscriminatorType.STRING, length=20)
@Slf4j
@Data
public abstract class Ad implements Serializable{

	private static final long serialVersionUID = 5725261213022717645L;

	private static String titleField = "title";
	private static String descriptionField = "description";
	private static String priceField = "price";
	private static String idField = "id";
	private static String userIDField = "userID";

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

	@Column(name="USER_ID")
	private long userID;

	@Column(name="CATEGORY_ID")
	private int categoryID;

	/***** Constructors *****/
	public Ad() {}

	/***** Manipulation *****/
	public static JsonObject getJSONAttributes() {		
		return new JsonObject();
	}
	
	
	public abstract boolean setParameters(JsonObject json, long userID);
	public abstract Ad getNewInstance();
	
	public JsonObject getJsonValues() {
		JsonObject jsonAd = new JsonObject();
		jsonAd.addProperty(idField, this.getId());
		jsonAd.addProperty(Ad.getTitleField(), this.getTitle());
		jsonAd.addProperty(Ad.getDescriptionField(), this.getDescription());
		jsonAd.addProperty(Ad.getPriceField(), this.getPrice());
		jsonAd.addProperty(Ad.getUserIDField(), this.getUserID());
		return jsonAd;
	}
	
	public boolean setMandatoryParameters(JsonObject json, long userID) {
		try {
			this.setTitle(json.get(titleField).getAsString());
			this.setDescription(json.get(descriptionField).getAsString());
			this.setPrice(json.get(priceField).getAsFloat());
			this.setUserID(userID);
		} catch (IllegalArgumentException e) {
			log.error("Mandatory fields couldn't be extracted");
			return false;
		}
		return true;
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

	public static String getUserIDField() {
		return userIDField;
	}


}
