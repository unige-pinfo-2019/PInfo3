package domain.service;


import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

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
	private AdServiceImpl cas;

	
	@Test
	public void testCreateAd() {
		Ad ad = new Ad("Un titre original", "En fait je nétais pas tres inspiré pour le titre", (float) 12.50);
		
		int tailleInitiale = cas.getEm().createQuery("SELECT a FROM Ad a", Ad.class).getResultList().size();
		cas.createAd(ad);
		int tailleFinale = cas.getEm().createQuery("SELECT a FROM Ad a", Ad.class).getResultList().size();
		
		if (tailleFinale != tailleInitiale+1) {
			fail("Le premier element na pas ete ajouté !");
		}
		
		tailleInitiale = cas.getEm().createQuery("SELECT a FROM Ad a", Ad.class).getResultList().size();
		cas.createAd(ad);
		tailleFinale = cas.getEm().createQuery("SELECT a FROM Ad a", Ad.class).getResultList().size();
		
		if (tailleFinale != tailleInitiale) {
			fail("On a pu ajouter le deuxième élément alors qu'il était censé être le même que le premier !");
		}
	}
	
	@Test
	public void testCreateAdValues() {
		String t = "Chargeur iPhone";
		String d = "Fonctionne avec Android (c'est pas une blague)";
		float p = (float) 120;
		Ad ad = new Ad(t, d, p);
		cas.createAd(ad);
		
		String query = "SELECT a FROM Ad a";
		int size = cas.getEm().createQuery(query, Ad.class).getResultList().size();
		Ad caBDD = cas.getEm().createQuery(query, Ad.class).getResultList().get(size-1);

		if (caBDD.getTitle() != t) {
			fail("Le titre est différent après ajout de l'annonce.");
		} else if (caBDD.getDescription() != d) {
			fail("La description est différente après ajout de l'annonce.");
		} else if (caBDD.getPrice() != p) {
			fail("Le prix est différent après ajout de l'annonce");
		}
	}
	
	@Test
	public void testCreateClassAdId() {
		Ad caA = new Ad("AAAA", "aaaaa", (float) 111);
		Ad caB = new Ad("BBBB", "bbbbb", (float) 222);
		Ad caC = new Ad("CCCC", "ccccc", (float) 333);
		Ad caD = new Ad("DDDD", "ddddd", (float) 444);
		Ad caE = new Ad("EEEE", "eeeee", (float) 555);
		Ad caF = new Ad("FFFF", "fffff", (float) 666);
		
		cas.createAd(caA);
		cas.createAd(caB);
		cas.createAd(caC);
		cas.createAd(caD);
		cas.createAd(caE);
		cas.createAd(caF);
		
		List<Ad> lCA = cas.getEm().createQuery("SELECT a FROM Ad a", Ad.class).getResultList();
		
		for (int i=0; i<lCA.size(); ++i) {
			for (int j=i+1; j<lCA.size(); ++j) {
				if (lCA.get(i).getId() == lCA.get(j).getId()) {
					fail("Les annonces \"" + lCA.get(i).getTitle() + "\" et \"" + lCA.get(j).getTitle() + "\" ont le même id.");
				}
			}
		}
		
	}
	/*
	@Test
	public void testGetAll() {
		fail("Not yet implemented");
	}
	*/
	@Test
	public void testGetByTitle() {
		String t = "Chargeur Android";
		String d = "Fonctionne avec Android et même Xperia";
		float p = (float) 50;
		Ad ad = new Ad(t, d, p);
		cas.createAd(ad);
		
		Optional<Ad> caBDD = cas.getByTitle(t);
		
		if (caBDD.isEmpty()) {
			fail("Le titre " + t + " n'a pas été trouvé dans la base de données.");
		}
	}
	
	@Test
	public void testGetById() {
		Ad caM = new Ad("MMMM", "mmmmm", (float) 1);
		Ad caN = new Ad("NNNN", "nnnnn", (float) 2);
		Ad caO = new Ad("OOOO", "ooooo", (float) 3);
		Ad caP = new Ad("PPPP", "ppppp", (float) 4);
		Ad caQ = new Ad("QQQQ", "qqqqq", (float) 5);
		Ad caR = new Ad("RRRR", "rrrrr", (float) 6);
		
		cas.createAd(caM);
		cas.createAd(caN);
		cas.createAd(caO);
		cas.createAd(caP);
		cas.createAd(caQ);
		cas.createAd(caR);
		
		Ad caP_byT = cas.getByTitle("PPPP").get();
		long idP = caP_byT.getId();
		
		Ad caP_byI = cas.getById(idP).get();
		
		if (caP_byT.getTitle() != caP_byI.getTitle() || caP_byT.getDescription() != caP_byI.getDescription() || caP_byT.getPrice() != caP_byI.getPrice()) {
			fail("L'annonce \"PPPP\" ne semble pas avoir été retrouvée par son id.");
		}
	}
	
	@Test
	public void testDeleteClassAd() {
		Ad caG = new Ad("GGGG", "ggggg", (float) 11);
		Ad caH = new Ad("HHHH", "hhhhh", (float) 22);
		Ad caI = new Ad("IIII", "iiiii", (float) 33);
		Ad caJ = new Ad("JJJJ", "jjjjj", (float) 44);
		Ad caK = new Ad("KKKK", "kkkkk", (float) 55);
		Ad caL = new Ad("LLLL", "lllll", (float) 66);
		
		cas.createAd(caG);
		cas.createAd(caH);
		cas.createAd(caI);
		cas.createAd(caJ);
		cas.createAd(caK);
		cas.createAd(caL);
		
		cas.deleteAd(caI);
		
		List<Ad> lCA = cas.getAll();
		
		for (Ad ad: lCA) {
			if (ad.getTitle() == caI.getTitle()) {
				fail("L'élément caI n'a pas été supprimé correctement.");
			}
		}
	}
	
}
