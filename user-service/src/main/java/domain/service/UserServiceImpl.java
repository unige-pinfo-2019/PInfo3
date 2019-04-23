package domain.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import domain.model.User;

@ApplicationScoped
@Transactional
public class UserServiceImpl implements UserService {
	
	@PersistenceContext (name="InmemoryPU")
	EntityManager em;

	@Override
	public boolean createUser(User u) {
		Optional<User> existing = this.getByName(u.getNom());
		if(!existing.isPresent()) {
			em.persist(u);
			return true;
		}
		return false;
	}
	
	@Override
	public List<User> getAll() {
		List<User> users = em.createQuery("SELECT a FROM User a", User.class).getResultList();
		return users;
	}

	@Override
	public Optional<User> getByName(String name) {
		List<User> users = em.createQuery("SELECT a FROM User a WHERE LOWER(a.nom) = LOWER('"+name+"')", User.class).getResultList();
		if(users.size() > 0) {
			return Optional.of(users.get(0));
		}
		return Optional.empty();
	}

	@Override
	public Optional<User> getById(long id) {
		List<User> users = em.createNamedQuery("SELECT a FROM User a WHERE LOWER(a.id) = LOWER('"+id+"')", User.class).getResultList();
		if (users.size() > 0) {
			return Optional.of(users.get(0));
		}
		return Optional.empty();
	}

	@Override
	public void deleteUser(User user) {
		em.remove(em.contains(user) ? user : em.merge(user));		
	}
	
	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	
}
