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
		
		
		Integer trololo = Integer.valueOf(35);
		if (x.equals(trololo))
			fail("Un user ne peut pas être comparé à un entier.");
		if (!(x.equals(x)))
			fail("La méthode equals n'est pas réflexive.");
		if (x.equals(y) != y.equals(x))
			fail("La méthode equals n'est pas symétrique.");
		if (x.equals(y) && y.equals(z) && !x.equals(z))
			fail("La méthode equals n'est pas transitive.");
		
		if (!x.equals(y)) {
			fail("Deux users semblent différents alors qu'ils ne le devraient pas.");
		}
		if (x.hashCode() != y.hashCode()) {
			fail("Le hashcode ne donne pas le même résultat que equals.");
		}
		
		User u = new User("Jon","SNOW",21, "j.snow@got.com", "003-994-10-24");
		User v = new User("Jon","SNOW",21, "j.snow@got.com", "003-994-10-24");
		User w = new User("Boba","FETT",43, "j.fett@sw.com", "077-726-52-73");
		User t = new User();
		
		if (u.equals(w))
			fail("Deux users différents sont les mêmes pour la méthode equals.");
		if (v.equals(t) || t.equals(v))
			fail("Deux users différents sont les mêmes pour la méthode equals.");
		if (v.hashCode() == t.hashCode())
			fail("Le hashcode ne donne pas le même résultat que equals.");
		if (t.equals(null))
			fail("Un user non null est semblable à null.");
		
		
		v.setId(0);
		w.setId(1);
		
		v.setFirstName("sgjk");
		w.setFirstName("sgjk");
		if (v.equals(w) || w.equals(v))
			fail("Deux users différents sont les mêmes pour la méthode equals.");
		
		v.setLastName("ashsdgnh");
		w.setLastName("ashsdgnh");
		if (v.equals(w) || w.equals(v))
			fail("Deux users différents sont les mêmes pour la méthode equals.");
		
		v.setAge(35);
		w.setAge(35);
		if (v.equals(w) || w.equals(v))
			fail("Deux users différents sont les mêmes pour la méthode equals.");
		
		v = new User();
		w = new User();
		
		v.setId(0);
		w.setId(1);
		
		v.setFirstName("sgjk");
		w.setFirstName("sgjk");
		if (v.equals(w) || w.equals(v))
			fail("Deux users différents sont les mêmes pour la méthode equals.");
		
		v.setLastName("ashsdgnh");
		w.setLastName("ashsdgnh");
		if (v.equals(w) || w.equals(v))
			fail("Deux users différents sont les mêmes pour la méthode equals.");
		
		v.setAge(35);
		w.setAge(35);
		if (v.equals(w) || w.equals(v))
			fail("Deux users différents sont les mêmes pour la méthode equals.");
		
	}
	
	@Test
	void testToString() {
		
		User x = new User("Jon","SNOW",21, "j.snow@got.com", "003-994-10-24");
		x.setId(0);
		
		String newLine = System.getProperty("line.separator");
		String ret = newLine + "SNOW Jon (User id = 0)";
		ret += newLine + newLine + "Age : 21"; 
		ret += newLine + newLine + "e-mail : j.snow@got.com";
		ret += newLine + newLine + "tel : 003-994-10-24";
		
		if (!x.toString().equals(ret))
			fail("toString() ne fait pas ce qu'on attend.");
		
	}

}
