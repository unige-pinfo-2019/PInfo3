package domain.model;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.JsonObject;

/**
 * Represents classifier ads and manage storage in DB.
 */
@Entity
@Table(name="AD")
public class Ad implements Serializable{

	private static final long serialVersionUID = 5725261213022717645L;
	
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
	
	@Transient
	private Map<String, Object> category;

	/***** Constructors *****/
	public Ad() {}
	
	public Ad(String title, String description, float price) {
		super();
		this.title = title;
		this.description = description;
		this.price = price;
	}
	
	/***** Manipulation *****/
	/* Returns attributes and their default values in a json format */
	public static JsonObject getAttributes() {
		JsonObject json = new JsonObject();
		json.addProperty("title", "");
		json.addProperty("description", "");
		json.addProperty("price", 0);
		return json;
	}
	
	/***** Utility methods *****/
	@Override
	public String toString() {
		String newLine = System.getProperty("line.separator");
		String ret;
		ret = newLine + title + " (Ad id = " + Long.toString(id) + ")"+ newLine + newLine + description + newLine + newLine + "Prix : " + String.valueOf(price);
		ret += newLine + "Other fields : " + category.toString();
		return ret;
	}
	
	/***** Getters and setters *****/
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Map<String, Object> getCategory() {
		return category;
	}

	public void setCategory(Map<String, Object> category) {
		this.category = category;
	}


}
