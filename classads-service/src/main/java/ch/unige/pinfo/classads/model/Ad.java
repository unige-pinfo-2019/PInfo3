package ch.unige.pinfo.classads.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


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
	
	
	
	public Ad() {}
	
	public Ad(String title, String description, float price) {
		super();
		this.title = title;
		this.description = description;
		this.price = price;
	}
	
	@Override
	public String toString() {
		String NewLigne = System.getProperty("line.separator");
		String ret;
		ret = NewLigne + title + " (id = " + String.valueOf(id) + ")"+ NewLigne + NewLigne + description + NewLigne + NewLigne + "Prix : " + String.valueOf(price);
		return ret;
	}
	
	public String returnJSON() {
		return "{\"title\":\""+this.title+"\", \"description\":\"" + this.description + "\n, \"price\":\"" + this.price + "\"";
	}

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


}
