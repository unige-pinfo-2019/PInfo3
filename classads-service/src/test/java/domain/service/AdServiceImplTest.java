package domain.service;


import static org.junit.jupiter.api.Assertions.fail;

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

import com.google.gson.JsonObject;

import domain.model.Ad;
import domain.service.AdServiceImpl;
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
	private Ad adExample = new Ad(title, description, price);

	
	@Test
	public void testCreateAd() {
		//We create an ad
		Ad ad = new Ad("Any title", "Any description", (float) 12.50);
		
		//At the beginning, the DB is empty but we get its size and we insert the ad
		int tailleInitiale = as.getEm().createQuery("SELECT a FROM Ad a", Ad.class).getResultList().size();
		as.createAd(ad);
		
		//Then, we check if the size has increased by 1
		int tailleFinale = as.getEm().createQuery("SELECT a FROM Ad a", Ad.class).getResultList().size();
		if (tailleFinale != tailleInitiale+1) {
			fail("We cannot insert the ad in the DB");
		}
		
		//Now, we check that we cannot insert the same ad twice
		tailleInitiale = as.getEm().createQuery("SELECT a FROM Ad a", Ad.class).getResultList().size();
		as.createAd(ad);
		
		tailleFinale = as.getEm().createQuery("SELECT a FROM Ad a", Ad.class).getResultList().size();
		if (tailleFinale != tailleInitiale) {
			fail("We've inserted the same ad twice");
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
		if (adInDB.getTitle() != title) {
			fail("Title is different in the DB");
		} else if (adInDB.getDescription() != description) {
			fail("Description is different in the DB");
		} else if (adInDB.getPrice() != price) {
			fail("Price is different in the DB");
		}
	}
	
	@Test
	public void testCreateClassAdId() {
		
		//We create some ads and add them in the DB
		for (int i=0; i<5; i++) {
			as.createAd(new Ad("Title"+i, "Description"+i, (float)i*10));
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
			as.createAd(new Ad("Title"+i, "Description"+i, (float)i*10));
		}
		as.createAd(adExample);
		as.deleteAd(adExample);
		
		List<Ad> ads = as.getAll();
		
		for (Ad ad: ads) {
			if (ad.getTitle() == title) {
				fail("Coudn't delete ad properly");
			}
		}
	}
	
	@Test
	public void createAdFromJsonTest() {
		JsonObject json;
		Ad ad;
		
		//Test to decrypt an ad with a bad category ID
		json = new JsonObject();
		json.addProperty("categoryID", -1);
		ad = as.createAdFromJson(json);
		Assertions.assertEquals(null, ad);
		
		//Test to decrypt an ad with a right categoryID but not all mandatory parameters
		json = new JsonObject();
		json.addProperty("categoryID", 0);
		json.addProperty("title", "Any title");
		json.addProperty("price", 10);
		ad = as.createAdFromJson(json);
		Assertions.assertEquals(null, ad);
		
		//Test to decrypt an ad with the right mandatory field but not all category fields
		//For category 1 which is Books, fields are authors and nbPages
		json = new JsonObject();
		json.addProperty("categoryID", 1);
		json.addProperty("title", "Bel-ami");
		json.addProperty("description", "Interessing book");
		json.addProperty("price", 20);
		json.addProperty("authors", "Guy de Maupassant");
		ad = as.createAdFromJson(json);
		Assertions.assertNotEquals(null, ad);
		Assertions.assertEquals(ad.getClass(), adExample.getClass());
		
		//Test to decrypt an ad with the right mandatory field but not all category fields
		//For category 1 which is Books, fields are authors and nbPages
		json = new JsonObject();
		json.addProperty("categoryID", 1);
		json.addProperty("title", "Bel-ami");
		json.addProperty("description", "Interessing book");
		json.addProperty("price", 20);
		json.addProperty("authors", "Guy de Maupassant");
		json.addProperty("nbPages", 394);
		ad = as.createAdFromJson(json);
		Assertions.assertNotEquals(null, ad);
		Assertions.assertEquals(ad.getClass(), adExample.getClass());
		
	
	}
	
}
