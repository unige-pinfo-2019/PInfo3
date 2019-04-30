package domain.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import domain.model.User;

@ApplicationScoped
@Transactional
public class UserServiceImpl implements UserService {
	
	@PersistenceContext (name="InmemoryPU")
	EntityManager em;

	@Override
	public boolean createUser(User u) {
		Optional<User> existing = this.getByName(u.getFirstName());
		if(!existing.isPresent()) {
			em.persist(u);
			return true;
		}
		return false;
	}
	
	@Override
	public List<User> getAll() {
		// List<User> users = em.createQuery("SELECT a FROM User a", User.class).getResultList();
		// return users;
		
		CriteriaBuilder qb = getEm().getCriteriaBuilder();
		CriteriaQuery<User> c = qb.createQuery(User.class);
		Root<User> adRoot = c.from(User.class);
		c.select(adRoot);
		return getEm().createQuery(c).getResultList();
		
	}

	@Override
	public Optional<User> getByName(String name) {
		//List<User> users = em.createQuery("SELECT a FROM User a WHERE LOWER(a.nom) = LOWER('"+name+"')", User.class).getResultList();
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> q = cb.createQuery(User.class);
		Root<User> c = q.from(User.class);
		
		ParameterExpression<String> p = cb.parameter(String.class);
		q.select(c).where(cb.equal(cb.lower(c.get("firstName")), cb.lower(p)));
		
		TypedQuery<User> query = em.createQuery(q);
		query.setParameter(p, name);
		List<User> results = query.getResultList();
		
		if(results.size() > 0) {
			return Optional.of(results.get(0));
		}
		return Optional.empty();
	}

	@Override
	public Optional<User> getById(long id) {
		// List<User> users = em.createNamedQuery("SELECT a FROM User a WHERE LOWER(a.id) = LOWER('"+id+"')", User.class).getResultList();
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> q = cb.createQuery(User.class);
		Root<User> c = q.from(User.class);
		
		ParameterExpression<Long> p = cb.parameter(Long.class);
		q.select(c).where(cb.equal(c.get("id"), p));
		
		TypedQuery<User> query = em.createQuery(q);
		query.setParameter(p, id);
		List<User> results = query.getResultList();
		
		if (results.size() > 0) {
			return Optional.of(results.get(0));
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
