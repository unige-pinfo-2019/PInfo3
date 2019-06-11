package domain.service;


import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
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

import domain.model.Ad;
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
	private String userID = "100";
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
		Ad ad = new Ad("Any title", "Any description", (float) 12.50, "0", 0, new ArrayList<String>());

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
			as.createAd(new Ad("Title"+i, "Description"+i, (float)i*10, "0", 0, new ArrayList<String>()));
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

		Optional<Ad> adInDB2 = as.getByTitle("false title");
		if(adInDB2.isPresent()) {
			fail("false title has not been resolved in the DB.");
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
		Optional<Ad> adById2 = as.getById(id+1);
		if (adById2.isPresent()) {
			fail("ID " + id+1 + " has been resolved in the DB when it shouldm't have.");
		}
	}
	@Test
	public void testupdate() {
		//We create a new ad and insert it in the DB
		Ad ad = adExample;
		as.createAd(ad);
		ad.setPrice((float)12.3);
		as.update(ad);


		Optional<Ad> adById = as.getById(ad.getId());
		if (adById.isEmpty()) {
			fail("ID " + ad.getId() + " has not been resolved in the DB.");
		}
		Assertions.assertEquals((float)12.3, adById.get().getPrice());

		ad.setDeleted(true);
		try {
			as.update(ad);
			fail("The ad " + ad.getId()+ " was updated after being deleted");
		}catch(IllegalArgumentException ex) {
			// we expect an exception
		}
		Ad ad2 = new Ad();
		try {
			as.update(ad2);
			fail("an unexisting ad was updated");
		}catch(IllegalArgumentException ex) {
			// we expect an exception
		}

		String t = ad2.getTime();

		ad2.setTime("not a date string");
		Assertions.assertEquals(t, ad2.getTime());
		//the time shouldn't have change


		LocalDateTime time = LocalDateTime.now();
		ad2.setTime(time);
		Assertions.assertEquals(time, LocalDateTime.parse(ad2.getTime()));


	}

	@Test
	public void testDeleteClassAd() {
		//We create some ads and add them in the DB
		for (int i=0; i<5; i++) {
			as.createAd(new Ad("Title"+i, "Description"+i, (float)i*10, "0", 0, new ArrayList<String>()));
		}
		as.createAd(adExample);
		as.deleteAd(adExample);

		List<Ad> ads = as.getAll();

		for (Ad ad: ads) {
			if (ad.getTitle() == title && !ad.isDeleted()) {
				fail("Coudn't delete ad properly");
			}
		}
		as.deleteAd(new Ad());
	}


	@Test
	public void testGetAllByCategory() {
		//We create some ads and add them in the DB
		for (int i=1; i<6; i++) {
			as.createAd(new Ad("Title"+i+5, "Description"+i+5, (float)(1+i*0.1), "0", (i%2), new ArrayList<String>()));
		}

		List<Ad> l1 = as.getAllByCategory(0);
		List<Ad> l2 = as.getAllByCategory(1);

		Assertions.assertEquals(2, l1.size());
		Assertions.assertEquals(3, l2.size());

	}

	@Test
	public void testGetByUser() {
		//We create some ads and add them in the DB
		for (int i=1; i<6; i++) {
			as.createAd(new Ad("Title"+i+5, "Description"+i+5, (float)(1+i*0.1), Integer.toString((i%3)), 0, new ArrayList<String>()));
		}

		List<Ad> l1 = as.getByUser("0");
		List<Ad> l2 = as.getByUser("1");
		List<Ad> l3 = as.getByUser("2");

		Assertions.assertEquals(1, l1.size());
		Assertions.assertEquals(2, l2.size());
		Assertions.assertEquals(2, l3.size());
	}

}
