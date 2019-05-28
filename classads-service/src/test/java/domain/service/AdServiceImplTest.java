package domain.service;


import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import domain.model.Ad;
import domain.model.Categories;
import eu.drus.jpa.unit.api.JpaUnit;


@ExtendWith(JpaUnit.class)
@ExtendWith(MockitoExtension.class)
public class AdServiceImplTest {
	
	@Spy
	@PersistenceContext(unitName = "AdUTest")
	EntityManager em;

	@InjectMocks
	private AdServiceImpl as;
	
	private String title = "Charger iPhone";
	private String description = "Works with Android (not a joke)";
	private float price = (float) 120;
	private int categoryID = 1;
	private int userID = 100;
	private ArrayList<String> images = new ArrayList<String>() {
		private static final long serialVersionUID = -273727299296242150L;
	{
		add("Image1");
		add("Image2");
		add("Image3");
	}};
	private Ad adExample = new Ad(title, description, price, userID, categoryID, images);
	
	@Test
	public void testCreateAd() {
		//We create an ad
		Ad ad = new Ad("Any title", "Any description", (float) 12.50, 0, 0, new ArrayList<String>());
		
		//At the beginning, the DB is empty but we get its size and we insert the ad
		int tailleInitiale = as.getEm().createQuery("SELECT a FROM Ad a", Ad.class).getResultList().size();
		as.createAd(ad);
		
		//Then, we check if the size has increased by 1
		int tailleFinale = as.getEm().createQuery("SELECT a FROM Ad a", Ad.class).getResultList().size();
		if (tailleFinale != tailleInitiale+1) {
			fail("We cannot insert the ad in the DB");
		}
		
	}
	
	@Test
	public void testCreateAdValues() {
		//We create a new ad and insert it in the DB
		Ad ad = adExample;
		as.createAd(ad);
		
		//Then, we extract the ad from the DB
		String query = "SELECT a FROM Ad a";
		int size = as.getEm().createQuery(query, Ad.class).getResultList().size();
		Ad adInDB = as.getEm().createQuery(query, Ad.class).getResultList().get(size-1);

		//And, we check if it has the same values
		Assertions.assertEquals(title, adInDB.getTitle());
		Assertions.assertEquals(price, adInDB.getPrice());
		Assertions.assertEquals(description, adInDB.getDescription());
		Assertions.assertEquals(categoryID, adInDB.getCategoryID());
		Assertions.assertEquals(userID, adInDB.getUserID());
		
		List<String> imgs = adInDB.getImages();
		Assertions.assertEquals(images.size(), imgs.size());
		for (int i=0; i<imgs.size(); i++) {
			Assertions.assertEquals(images.get(i), imgs.get(i));
		}
	}
	
	@Test
	public void testCreateClassAdId() {
		
		//We create some ads and add them in the DB
		for (int i=0; i<5; i++) {
			as.createAd(new Ad("Title"+i, "Description"+i, (float)i*10, 0, 0, new ArrayList<String>()));
		}
		
		//We extract the ads
		List<Ad> ads = as.getEm().createQuery("SELECT a FROM Ad a", Ad.class).getResultList();
		
		//We check if all ads have a different ID
		for (int i=0; i<ads.size(); ++i) {
			for (int j=i+1; j<ads.size(); ++j) {
				if (ads.get(i).getId() == ads.get(j).getId()) {
					fail("Ads \"" + ads.get(i).getTitle() + "\" and \"" + ads.get(j).getTitle() + "\" have the same id.");
				}
			}
		}
		
	}

	@Test
	public void testGetByTitle() {
		//We create a new ad and insert it in the DB
		Ad ad = adExample;
		as.createAd(ad);
		
		//We check if we can get by the title
		Optional<Ad> adInDB = as.getByTitle(title);
		
		if (adInDB.isEmpty()) {
			fail("Title " + title + " has not been resolved in the DB.");
		}
	}
	
	@Test
	public void testGetById() {
		//We create a new ad and insert it in the DB
		Ad ad = adExample;
		as.createAd(ad);
		
		//We check if we can get by the title
		Ad adInDB = as.getByTitle(title).get();
		long id = adInDB.getId();
		
		Optional<Ad> adById = as.getById(id);
		if (adById.isEmpty()) {
			fail("ID " + id + " has not been resolved in the DB.");
		}
	}
	
	@Test
	public void testDeleteClassAd() {
		//We create some ads and add them in the DB
		for (int i=0; i<5; i++) {
			as.createAd(new Ad("Title"+i, "Description"+i, (float)i*10, 0, 0, new ArrayList<String>()));
		}
		as.createAd(adExample);
		as.deleteAd(adExample);
		
		List<Ad> ads = as.getAll();
		
		for (Ad ad: ads) {
			if (ad.getTitle() == title && !ad.isDeleted()) {
				fail("Coudn't delete ad properly");
			}
		}
	}
	
	@Test 
	public void getJsonListAds() {
		//To test if we generate the right json for a list of ads
		
		//First, we create 2 ads
		
		Ad ad1 = new Ad("Livre de Maupassant", "Livre utilisé au collège pour un cours de français", 10, 0, 0, new ArrayList<String>());
		Ad ad2 = new Ad("Vélo bleu", "VTT de mon frere devenu trop petit pour lui", 100, 0, 0, new ArrayList<String>());
		
		//Then, we create a list
		List<Ad> ads = new ArrayList<Ad>();
		ads.add(ad1);
		ads.add(ad2);

		JsonArray json = as.getJsonListAds(ads);
		
		//Test if the json array has 2 json objects
		Assertions.assertEquals(2, json.size());
		
		//Test if we can extract the mandatory fields from these objects
		for (JsonElement jsonAtt : json) {
			if (!jsonAtt.getAsJsonObject().has(Ad.getTitleField())) 
				Assertions.fail("Coudn't extract title from json");
			if (!jsonAtt.getAsJsonObject().has(Ad.getDescriptionField())) 
				Assertions.fail("Coudn't extract description from json");
			if (!jsonAtt.getAsJsonObject().has(Ad.getPriceField())) 
				Assertions.fail("Coudn't extract price from json");
			if (!jsonAtt.getAsJsonObject().has(Categories.getCategoryIDField())) 
				Assertions.fail("Coudn't extract categoryID from json"); 
		}	
			
	}
	
	@Test
	public void createAdFromJsonTest() {
		JsonObject json;
		Ad ad;
		
		//Test to decrypt an ad with a bad category ID
		json = new JsonObject();
		json.addProperty(Categories.getCategoryIDField(), -1);
		ad = as.createAdFromJson(json);
		Assertions.assertEquals(null, ad);
		
		//Test to decrypt an ad with a right categoryID but not all mandatory parameters
		json = new JsonObject();
		json.addProperty(Categories.getCategoryIDField(), 0);
		json.addProperty(Ad.getTitleField(), "Any title");
		json.addProperty(Ad.getPriceField(), 10);
		json.addProperty(Ad.getUserIDField(), 0);
		json.add(Ad.getImageField(), new JsonArray());
		ad = as.createAdFromJson(json);
		Assertions.assertEquals(null, ad);
		
		//Test to decrypt an ad with the right mandatory field but not all category fields
		//For category 1 which is Books, fields are authors and nbPages
		json = new JsonObject();
		json.addProperty(Categories.getCategoryIDField(), 1);
		json.addProperty(Ad.getTitleField(), "Bel-ami");
		json.addProperty(Ad.getDescriptionField(), "Interessing book");
		json.addProperty(Ad.getPriceField(), 20);
		json.addProperty(Ad.getUserIDField(), 0);
		json.add(Ad.getImageField(), new JsonArray());
		ad = as.createAdFromJson(json);
		Assertions.assertNotEquals(null, ad);
		Assertions.assertEquals(ad.getClass(), adExample.getClass());
		
	
	}
	
	@Test
	public void testGetAllByCategory() {
		//We create some ads and add them in the DB
		for (int i=1; i<6; i++) {
			as.createAd(new Ad("Title"+i+5, "Description"+i+5, (float)(1+i*0.1), 0, (i%2), new ArrayList<String>()));
		}
		
		List<Ad> l1 = as.getAllByCategory(0);
		List<Ad> l2 = as.getAllByCategory(1);
		
		Assertions.assertEquals(2, l1.size());
		Assertions.assertEquals(3, l2.size());
		
	}
	
}
