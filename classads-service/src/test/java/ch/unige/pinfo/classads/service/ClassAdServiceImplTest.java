package ch.unige.pinfo.classads.service;



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

import ch.unige.pinfo.classads.model.ClassAd;
import ch.unige.pinfo.classads.service.ClassAdServiceImpl;
import eu.drus.jpa.unit.api.JpaUnit;


@ExtendWith(JpaUnit.class)
@ExtendWith(MockitoExtension.class)
public class ClassAdServiceImplTest {
	
	@Spy
	@PersistenceContext(unitName = "ClassAdUTest")
	EntityManager em;

	@InjectMocks
	private ClassAdServiceImpl cas;

	
	@Test
	public void testCreateClassAd() {
		ClassAd ca = new ClassAd("Un titre original", "En fait je nétais pas tres inspiré pour le titre", (float) 12.50);
		
		int tailleInitiale = cas.getEm().createQuery("SELECT a FROM ClassAd a", ClassAd.class).getResultList().size();
		cas.createClassAd(ca);
		int tailleFinale = cas.getEm().createQuery("SELECT a FROM ClassAd a", ClassAd.class).getResultList().size();
		
		if (tailleFinale != tailleInitiale+1) {
			fail("Le premier element na pas ete ajouté !");
		}
		
		tailleInitiale = cas.getEm().createQuery("SELECT a FROM ClassAd a", ClassAd.class).getResultList().size();
		cas.createClassAd(ca);
		tailleFinale = cas.getEm().createQuery("SELECT a FROM ClassAd a", ClassAd.class).getResultList().size();
		
		if (tailleFinale != tailleInitiale) {
			fail("On a pu ajouter le deuxième élément alors qu'il était censé être le même que le premier !");
		}
	}
	
	@Test
	public void testCreateClassAdValues() {
		String t = "Chargeur iPhone";
		String d = "Fonctionne avec Android (c'est pas une blague)";
		float p = (float) 120;
		ClassAd ca = new ClassAd(t, d, p);
		cas.createClassAd(ca);
		
		String query = "SELECT a FROM ClassAd a";
		int size = cas.getEm().createQuery(query, ClassAd.class).getResultList().size();
		ClassAd caBDD = cas.getEm().createQuery(query, ClassAd.class).getResultList().get(size-1);

		if (caBDD.getTitre() != t) {
			fail("Le titre est différent après ajout de l'annonce.");
		} else if (caBDD.getDescription() != d) {
			fail("La description est différente après ajout de l'annonce.");
		} else if (caBDD.getPrix() != p) {
			fail("Le prix est différent après ajout de l'annonce");
		}
	}
	
	@Test
	public void testCreateClassAdId() {
		ClassAd caA = new ClassAd("AAAA", "aaaaa", (float) 111);
		ClassAd caB = new ClassAd("BBBB", "bbbbb", (float) 222);
		ClassAd caC = new ClassAd("CCCC", "ccccc", (float) 333);
		ClassAd caD = new ClassAd("DDDD", "ddddd", (float) 444);
		ClassAd caE = new ClassAd("EEEE", "eeeee", (float) 555);
		ClassAd caF = new ClassAd("FFFF", "fffff", (float) 666);
		
		cas.createClassAd(caA);
		cas.createClassAd(caB);
		cas.createClassAd(caC);
		cas.createClassAd(caD);
		cas.createClassAd(caE);
		cas.createClassAd(caF);
		
		List<ClassAd> lCA = cas.getEm().createQuery("SELECT a FROM ClassAd a", ClassAd.class).getResultList();
		
		for (int i=0; i<lCA.size(); ++i) {
			for (int j=i+1; j<lCA.size(); ++j) {
				if (lCA.get(i).getId() == lCA.get(j).getId()) {
					fail("Les annonces \"" + lCA.get(i).getTitre() + "\" et \"" + lCA.get(j).getTitre() + "\" ont le même id.");
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
		ClassAd ca = new ClassAd(t, d, p);
		cas.createClassAd(ca);
		
		Optional<ClassAd> caBDD = cas.getByTitle(t);
		
		if (caBDD.isEmpty()) {
			fail("Le titre " + t + " n'a pas été trouvé dans la base de données.");
		}
	}
	
	@Test
	public void testGetById() {
		ClassAd caM = new ClassAd("MMMM", "mmmmm", (float) 1);
		ClassAd caN = new ClassAd("NNNN", "nnnnn", (float) 2);
		ClassAd caO = new ClassAd("OOOO", "ooooo", (float) 3);
		ClassAd caP = new ClassAd("PPPP", "ppppp", (float) 4);
		ClassAd caQ = new ClassAd("QQQQ", "qqqqq", (float) 5);
		ClassAd caR = new ClassAd("RRRR", "rrrrr", (float) 6);
		
		cas.createClassAd(caM);
		cas.createClassAd(caN);
		cas.createClassAd(caO);
		cas.createClassAd(caP);
		cas.createClassAd(caQ);
		cas.createClassAd(caR);
		
		ClassAd caP_byT = cas.getByTitle("PPPP").get();
		long idP = caP_byT.getId();
		
		ClassAd caP_byI = cas.getById(idP).get();
		
		if (caP_byT.getTitre() != caP_byI.getTitre() || caP_byT.getDescription() != caP_byI.getDescription() || caP_byT.getPrix() != caP_byI.getPrix()) {
			fail("L'annonce \"PPPP\" ne semble pas avoir été retrouvée par son id.");
		}
	}
	
	@Test
	public void testDeleteClassAd() {
		ClassAd caG = new ClassAd("GGGG", "ggggg", (float) 11);
		ClassAd caH = new ClassAd("HHHH", "hhhhh", (float) 22);
		ClassAd caI = new ClassAd("IIII", "iiiii", (float) 33);
		ClassAd caJ = new ClassAd("JJJJ", "jjjjj", (float) 44);
		ClassAd caK = new ClassAd("KKKK", "kkkkk", (float) 55);
		ClassAd caL = new ClassAd("LLLL", "lllll", (float) 66);
		
		cas.createClassAd(caG);
		cas.createClassAd(caH);
		cas.createClassAd(caI);
		cas.createClassAd(caJ);
		cas.createClassAd(caK);
		cas.createClassAd(caL);
		
		cas.deleteClassAd(caI);
		
		List<ClassAd> lCA = cas.getAll();
		
		for (ClassAd ca: lCA) {
			if (ca.getTitre() == caI.getTitre()) {
				fail("L'élément caI n'a pas été supprimé correctement.");
			}
		}
	}
	
}
