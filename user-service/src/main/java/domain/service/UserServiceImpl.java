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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@Transactional
public class UserServiceImpl implements UserService {
	
	@PersistenceContext (name="UsersPU")
	EntityManager em;

	@Override
	public boolean createUser(User u) {
		User us = new User(u);
		Optional<User> existing = this.getByName(us.getFirstName());
		if(!existing.isPresent()) {
			em.persist(us);
			log.info("user " + us.toString() + " added to database");
			return true;
		}
		return false;
	}
	
	@Override
	public List<User> getAll() {		
		CriteriaBuilder qb = getEm().getCriteriaBuilder();
		CriteriaQuery<User> c = qb.createQuery(User.class);
		Root<User> adRoot = c.from(User.class);
		c.select(adRoot);
		return getEm().createQuery(c).getResultList();
		
	}

	@Override
	public Optional<User> getByName(String name) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> q = cb.createQuery(User.class);
		Root<User> c = q.from(User.class);
		
		ParameterExpression<String> p = cb.parameter(String.class);
		q.select(c).where(cb.equal(cb.lower(c.get("firstName")), cb.lower(p)));
		
		TypedQuery<User> query = em.createQuery(q);
		query.setParameter(p, name);
		List<User> results = query.getResultList();
		if(!results.isEmpty()) {
			return Optional.of(results.get(0));
		}
		return Optional.empty();
	}

	@Override
	public Optional<User> getById(long id) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> q = cb.createQuery(User.class);
		Root<User> c = q.from(User.class);
		
		ParameterExpression<Long> p = cb.parameter(Long.class);
		q.select(c).where(cb.equal(c.get("id"), p));
		
		TypedQuery<User> query = em.createQuery(q);
		query.setParameter(p, id);
		List<User> results = query.getResultList();
		
		if (!results.isEmpty()) {
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
