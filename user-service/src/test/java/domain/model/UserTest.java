package domain.model;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

class UserTest {

	@Test
	void testUser() {
		
		User user = new User("Billy","MANDY",15, "billymandy@yahoo.com", "039-846-71-23");
		
		if (user.getFirstName() != "Billy") 
			fail("Erreur. Le contructeur de user n'attribue pas correctement le prénom.");
		
		if (user.getLastName() != "MANDY") 
			fail("Erreur. Le contructeur de user n'attribue pas correctement le nom.");
		
		if (user.getAge() != 15) 
			fail("Erreur. Le contructeur de user n'attribue pas correctement l'âge.");
		
		if (user.getEmail() != "billymandy@yahoo.com") 
			fail("Erreur. Le contructeur de user n'attribue pas correctement l'e-mail.");
		
		if (user.getTel() != "039-846-71-23") 
			fail("Erreur. Le contructeur de user n'attribue pas correctement le numéro de téléphone.");
		
	}

	@Test
	void testEquals() {
		
		User x = new User("Jon","SNOW",21, "j.snow@got.com", "003-994-10-24");
		x.setId(0);
		User y = new User("Jon","SNOW",21, "j.snow@got.com", "003-994-10-24");
		y.setId(0);
		User z = new User("Jon","SNOW",21, "j.snow@got.com", "003-994-10-24");
		z.setId(0);
		
		
		if (!(x.equals(x)))
			fail("La méthode equals n'est pas réflexive.");
		if (x.equals(y) != y.equals(x))
			fail("La méthode equals n'est pas symétrique.");
		if (x.equals(y) && y.equals(z) && !x.equals(z))
			fail("La méthode equals n'est pas transitive.");
		
		if (!x.equals(y)) {
			fail("Deux users semblent différents alors qu'ils ne le devraient pas.");
			if (x.hashCode() != y.hashCode()) {
				fail("Le hashcode ne donne pas le même résultat que equals.");
			}
		}
		
		
		User u = new User("Jon","SNOW",21, "j.snow@got.com", "003-994-10-24");
		User v = new User("Jon","SNOW",21, "j.snow@got.com", "003-994-10-24");
		User w = new User("Boba","FETT",43, "j.fett@sw.com", "077-726-52-73");
		
		if (u.equals(w))
			fail("Deux users différents sont les mêmes pour la méthode equals.");
		
	}

}
