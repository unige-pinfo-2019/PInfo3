package domain.service;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import domain.model.User;
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
	public void testCreateUser() {
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
	
	@Test
	public void testCreateUserValues() {
		String p = "Boulou";
		String n = "Bala";
		int a = 27;
		String e = "bb@etu.unige.ch";
		String t = "0223062091";
		
		User user = new User(p, n, a, e, t);
		usi.createUser(user);
		
		String query = "SELECT a FROM User a";
		int size = usi.getEm().createQuery(query, User.class).getResultList().size();
		User userBDD = usi.getEm().createQuery(query, User.class).getResultList().get(size-1);
		
		
		if (userBDD.getFirstName() != p) {
			fail("Le prenom a changé à la création du User.");
		} else if (userBDD.getLastName() != n) {
			fail("Le nom a changé à la création du User.");
		} else if (userBDD.getAge() != a) {
			fail("L'âge a changé à la création du User.");
		} else if (userBDD.getEmail() != e) {
			fail("L'email a changé à la création du User.");
		} else if (userBDD.getTel() != t) {
			fail("Le téléphone a changé à la création du User.");
		}
	}
	
	@Test
	public void testCreateClassAdId() {
		User caA = new User("Aaaa","AAAA",11, "aaa@aaa.com", "111-111-11-11");
		User caB = new User("Bbbb","BBBB",11, "bbb@bbb.com", "222-222-22-22");
		User caC = new User("Cccc","CCCC",11, "ccc@ccc.com", "333-333-33-33");
		User caD = new User("Dddd","DDDD",11, "ddd@ddd.com", "444-444-44-44");
		User caE = new User("Eeee","EEEE",11, "eee@eee.com", "555-555-55-55");
		User caF = new User("Ffff","FFFF",11, "fff@fff.com", "666-666-66-66");
		
		usi.createUser(caA);
		usi.createUser(caB);
		usi.createUser(caC);
		usi.createUser(caD);
		usi.createUser(caE);
		usi.createUser(caF);
		
		List<User> lU = usi.getEm().createQuery("SELECT a FROM User a", User.class).getResultList();
		
		for (int i=0; i<lU.size(); ++i) {
			for (int j=i+1; j<lU.size(); ++j) {
				if (lU.get(i).getId() == lU.get(j).getId()) {
					fail("Les users \"" + lU.get(i).getFirstName() + " " + lU.get(i).getLastName() + "\" et \"" + lU.get(j).getFirstName() + " " + lU.get(j).getLastName() + "\" ont le même id.");
				}
			}
		}
		
	}
	
}
