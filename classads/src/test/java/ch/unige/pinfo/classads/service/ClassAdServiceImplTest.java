package ch.unige.pinfo.classads.service;


import static org.junit.jupiter.api.Assertions.fail;

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
		
		int tailleInitiale = 0 ;//= cas.getEm().createQuery("SELECT a FROM ClassAd a", ClassAd.class).getResultList().size();
		cas.createClassAd(ca);
		int tailleFinale = cas.getEm().createQuery("SELECT a FROM ClassAd a", ClassAd.class).getResultList().size();
		
		if (tailleFinale != 1) {
			fail("Le premier element na pas ete ajouté !");
		}
		
		tailleInitiale = cas.getEm().createQuery("SELECT a FROM ClassAd a", ClassAd.class).getResultList().size();
		cas.createClassAd(ca);
		tailleFinale = cas.getEm().createQuery("SELECT a FROM ClassAd a", ClassAd.class).getResultList().size();
		
		if (tailleFinale != tailleInitiale) {
			fail("On a pu ajouter le deuxième élément alors qu'il était censé être le même que le premier !");
		}
	}
	/*
	@Test
	public void testGetAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetByTitle() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetById() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteClassAd() {
		fail("Not yet implemented");
	}
	*/

}
