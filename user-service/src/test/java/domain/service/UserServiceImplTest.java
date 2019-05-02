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
		User caB = new User("Bbbb","BBBB",22, "bbb@bbb.com", "222-222-22-22");
		User caC = new User("Cccc","CCCC",33, "ccc@ccc.com", "333-333-33-33");
		User caD = new User("Dddd","DDDD",44, "ddd@ddd.com", "444-444-44-44");
		User caE = new User("Eeee","EEEE",55, "eee@eee.com", "555-555-55-55");
		User caF = new User("Ffff","FFFF",66, "fff@fff.com", "666-666-66-66");
		
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
	
	@Test
	public void testGetByName() {
		String p = "Johnny";
		String n = "Bravo";
		int a = 25;
		String e = "jb_le_bg@etu.unige.ch";
		String t = "0223062091";
		
		User user = new User(p, n, a, e, t);
		usi.createUser(user);
		
		Optional<User> caBDD = usi.getByName(p);
		
		if (caBDD.isEmpty()) {
			fail("Le prénom " + p + " n'a pas été trouvé dans la base de données.");
		}
	}
	
	@Test
	public void testGetById() {
		User caA = new User("Mmmm","MMMM",11, "mmm@mmm.com", "111-222-33-11");
		User caB = new User("Nnnn","NNNN",21, "nnn@nnn.com", "222-333-44-22");
		User caC = new User("Oooo","OOOO",31, "ooo@ooo.com", "333-444-55-33");
		User caD = new User("Pppp","PPPP",41, "ppp@ppp.com", "444-555-66-44");
		User caE = new User("Qqqq","QQQQ",51, "qqq@qqq.com", "555-666-77-55");
		User caF = new User("Rrrr","RRRR",61, "rrr@rrr.com", "666-777-88-66");
		
		usi.createUser(caA);
		usi.createUser(caB);
		usi.createUser(caC);
		usi.createUser(caD);
		usi.createUser(caE);
		usi.createUser(caF);
		
		User uP_byN = usi.getByName("Pppp").get();
		long idP = uP_byN.getId();
		
		User uP_byI = usi.getById(idP).get();
		
		if (!uP_byN.equals(uP_byI)) {
			fail("Le user Pppp ne semble pas avoir été retrouvé par son id." );
		}
	}
	
	@Test
	public void testDeleteUser() {
		User caA = new User("Gggg","GGGG",11, "ggg@ggg.com", "111-222-11-11");
		User caB = new User("Hhhh","HHHH",12, "hhh@hhh.com", "222-333-22-22");
		User caC = new User("Iiii","IIII",13, "iii@iii.com", "333-444-33-33");
		User caD = new User("Jjjj","JJJJ",14, "jjj@jjj.com", "444-555-44-44");
		User caE = new User("Kkkk","KKKK",15, "kkk@kkk.com", "555-666-55-55");
		User caF = new User("Llll","LLLL",16, "lll@lll.com", "666-777-66-66");
		
		usi.createUser(caA);
		usi.createUser(caB);
		usi.createUser(caC);
		usi.createUser(caD);
		usi.createUser(caE);
		usi.createUser(caF);
		
		usi.deleteUser(caC);
		
		List<User> lU = usi.getAll();
		
		for (User user: lU) {
			if (user.equals(caC)) {
				fail("Le user " + caC.getId() + " n'a pas été supprimé correctement.");
			}
		}
	}
	
}
