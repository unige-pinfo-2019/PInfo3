package domain.model;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

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

	@ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name="cat_int")
    @Column(name="value")
    @CollectionTable(name="category_integer_attribute", joinColumns=@JoinColumn(name="cat_int_id"))
	private Map<String, Integer> categoryInt;				// integer attributes specific to the category

	@ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name="cat_bool")
    @Column(name="value")
    @CollectionTable(name="category_boolean_attribute", joinColumns=@JoinColumn(name="cat_bool_id"))
	private Map<String, Boolean> categoryBool;				// boolean attributes specific to the category

	@ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name="cat_string")
    @Column(name="value")
    @CollectionTable(name="category_string_attribute", joinColumns=@JoinColumn(name="cat_string_id"))
	private Map<String, String> categoryString;				// string attributes specific to the category

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
		String ret = newLine + title + " (Ad id = " + id + ")"+ newLine + newLine + description + newLine + newLine + "Prix : " + price;
		ret += newLine + "Other fields : " + categoryInt.toString() + categoryBool.toString() + categoryString.toString();
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

	public Map<String, Integer> getCategoryInt() {
		return categoryInt;
	}

	public Map<String, Boolean> getCategoryBool() {
		return categoryBool;
	}

	public Map<String, String> getCategoryString() {
		return categoryString;
	}

	public void setCategory(Map<String, Integer> categoryInt, Map<String, Boolean> categoryBool, Map<String, String> categoryString) {
		this.categoryInt = categoryInt;
		this.categoryBool = categoryBool;
		this.categoryString = categoryString;
	}


}
