package ch.unige.pinfo.user.service;

import static org.junit.jupiter.api.Assertions.fail;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import ch.unige.pinfo.user.model.User;
import ch.unige.pinfo.user.service.UserServiceImpl;
import eu.drus.jpa.unit.api.JpaUnit;

@ExtendWith(JpaUnit.class)
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
	
	@Spy
	@PersistenceContext(unitName = "UserPUTest")
	EntityManager em;

	@InjectMocks
	private UserServiceImpl usi;

	
	@Test
	public void testCreateClassAd() {
		User user = new User("Martin", "Dupont", 23, "martin.dupont@etu.unige.ch", "022 539 13 83");
		
		int tailleInitiale = 0 ;//= cas.getEm().createQuery("SELECT a FROM ClassAd a", ClassAd.class).getResultList().size();
		usi.createUser(user);
		int tailleFinale = usi.getEm().createQuery("SELECT a FROM User a", User.class).getResultList().size();
		
		if (tailleFinale != 1) {
			fail("Le premier element na pas ete ajouté !");
		}
		
		tailleInitiale = usi.getEm().createQuery("SELECT a FROM User a", User.class).getResultList().size();
		usi.createUser(user);
		tailleFinale = usi.getEm().createQuery("SELECT a FROM User a", User.class).getResultList().size();
		
		if (tailleFinale != tailleInitiale) {
			fail("On a pu ajouter le deuxième élément alors qu'il était censé être le même que le premier !");
		}
	}

}
