package domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Represents classifier ads and manage storage in DB.
 */
@Slf4j
@Entity
@Table(name="AD")
@Data
public class Ad implements Serializable{

	private static final long serialVersionUID = 5725261213022717645L;

	private static String titleField = "title";
	private static String userIDField = "userID";
	private static String categoryIDField = "categoryID";
	private static String deletedField = "deleted";

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="AD_ID")
	private long id;

	@Column(name="TITLE")
	private String title;

	@Column(name="DESCRIPTION")
	@Lob
	private String description;

	@Column(name="PRICE")
	private float price;

	@Column(name="USER_ID")
	private String userID;
	
	@Column(name="USERNAME")
	private String username;
	
	@Column(name="USEREMAIL")
	private String userEmail;

	@Column(name="CATEGORY_ID")
	private int categoryID;
	
	@Column(name="CREATION_DATE")
	private String time = LocalDateTime.now().toString();
	
	@Column(name="NB_VUE")
	private int nbVues = 0;
	
	@Column(name="DELETED")
	private boolean deleted = false;
	
	@JsonInclude()
	@Transient
	private boolean auth = false;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "images_string_id", joinColumns = @JoinColumn(name = "Ad_id"))
    @Column(name = "images")
	private List<String> images;
	
	

	/***** Constructors *****/
	public Ad() {
		this.time = LocalDateTime.now().toString();
	}
	
	
	public Ad(String title, String description, float price, String userID, int categoryID, List<String> images) {
		this.title = title;
		this.description = description;
		this.price = price;
		this.userID = userID;
		this.categoryID = categoryID;
		this.images = images;
		this.time = LocalDateTime.now().toString();
		
	}
	
	public Ad(String title, String description, float price, String userID, int categoryID, List<String> images, String username) {
		this.title = title;
		this.description = description;
		this.price = price;
		this.userID = userID;
		this.categoryID = categoryID;
		this.images = images;
		this.time = LocalDateTime.now().toString();
		this.username = username;
	}
	
	/***** Utility methods *****/
	@Override
	public String toString() {
		String newLine = System.getProperty("line.separator");
		String res = "Ad ID : " + id + newLine;
		res += "Title : " + title + newLine + "Description : " + description + newLine + "Prix : " + price + newLine;
		res += "Category ID : " + categoryID + newLine;
		res += "Created by " + username + " (" + userID + ")" + newLine;
		return res;
	}

	/***** Getters and setters *****/
	public void setTime(String t) {
		try {
			time = LocalDateTime.parse(t).toString();
		}catch (Exception e) {
			log.error("Error setting the date from String, cannot parse the String");
		}
	}
	public void setTime(LocalDateTime t) {
		time = t.toString();
	}
	
	/***** Static Getters *****/
	public static String getTitleField() {
		return titleField;
	}

	public static String getUserIDField() {
		return userIDField;
	}

	public static String getCategoryIDField() {
		return categoryIDField;
	}

	public static String getDeletedField() {
		return deletedField;
	}

}