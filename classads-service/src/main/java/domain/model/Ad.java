package domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.JsonArray;

import lombok.Data;

/**
 * Represents classifier ads and manage storage in DB.
 */
@Entity
@Table(name="AD")
@Data
public class Ad implements Serializable{

	private static final long serialVersionUID = 5725261213022717645L;

	private static String titleField = "title";
	private static String descriptionField = "description";
	private static String priceField = "price";
	private static String idField = "id";
	private static String userIDField = "userID";
	private static String categoryIDField = "categoryID";
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
	
	public Ad(String title, String description, float price, long userID, int categoryID, ArrayList<String> images) {
		this.title = title;
		this.description = description;
		this.price = price;
		this.userID = userID;
		this.categoryID = categoryID;
		this.images = images;
		
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
	
	
	public void setImagesFromJson(JsonArray J_array) {
		images= new ArrayList<String>();
		for(int i = 0; i < J_array.size(); i++){
		    images.add(J_array.get(i).getAsString());
		}	
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

	public static String getCategoryIDField() {
		return categoryIDField;
	}
	
	public static String getImageField() {
		return imageField;
	}

	public static String getIdField() {
		return idField;
	}	


}