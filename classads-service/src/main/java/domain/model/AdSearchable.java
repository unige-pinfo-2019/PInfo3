package domain.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class AdSearchable {

	private long id;

	private String title;

	private String description;

	private float price;

	private long userID;

	private int categoryID;
	
	private List<String> images;

	private String time;
	
	private int nbVues = 0;
	
	private boolean deleted = false;
	
	public void createAd(Ad ad) {
		this.id = ad.getId();
		this.title = ad.getTitle();
		this.description = ad.getDescription();
		this.price = ad.getPrice();
		this.userID = ad.getUserID();
		this.categoryID = ad.getCategoryID();
		this.images = ad.getImages();
		this.time = ad.getTime().toString();
		this.nbVues = ad.getNbVues();
		this.deleted = ad.isDeleted();
	}

}
