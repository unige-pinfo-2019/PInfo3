package domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
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
	private static String imageField = "images";
	
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
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "images_string_id", joinColumns = @JoinColumn(name = "Ad_id"))
    @Column(name = "images")
	private List<String> images;

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
		
		jsonAd.add(Ad.getImageField(), this.getImagesInJson());
		return jsonAd;
	}
	
	public boolean setMandatoryParameters(JsonObject json, long userID) {
		try {
			this.setTitle(json.get(titleField).getAsString());
			this.setDescription(json.get(descriptionField).getAsString());
			this.setPrice(json.get(priceField).getAsFloat());
			this.setUserID(userID);
			this.setImagesFromJson(json.get(imageField).getAsJsonArray());
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

	public static String getImageField() {
		return imageField;
	}
	
	public void setImagesFromJson(JsonArray J_array) {
		images= new ArrayList<String>();
		for(int i = 0; i < J_array.size(); i++){
		    images.add(J_array.get(i).getAsString());
		}	
	}
	JsonArray getImagesInJson() {
		JsonArray J = new JsonArray();
		for(int i=0; i<images.size(); i++) J.add(images.get(i));
		return J;
	}
	


}
