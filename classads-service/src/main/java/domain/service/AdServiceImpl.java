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


import domain.model.Ad;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@Transactional
public class AdServiceImpl implements AdService{

	@PersistenceContext(name="ClassAdsPU")
	private EntityManager em;

	@Override
	public boolean createAd(Ad ad) {
		em.persist(ad);
		log.info("Ad "+ ad.toString()+" created and stored in database");
		return true;
	}

	@Override
	public List<Ad> getAll() {
		CriteriaBuilder qb = getEm().getCriteriaBuilder();
		CriteriaQuery<Ad> c = qb.createQuery(Ad.class);
		Root<Ad> adRoot = c.from(Ad.class);
		c.select(adRoot);
		c.where(qb.equal(adRoot.get(Ad.getDeletedField()), false));
		return getEm().createQuery(c).getResultList();
	}
	
	@Override
	public List<Ad> getAllByCategory(int cat) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Ad> q = cb.createQuery(Ad.class);
		Root<Ad> c = q.from(Ad.class);
		
		ParameterExpression<Integer> p = cb.parameter(Integer.class);
		q.select(c).where(
				cb.equal(c.get(Ad.getCategoryIDField()), p),
				cb.equal(c.get(Ad.getDeletedField()), false)
				);
		TypedQuery<Ad> query = em.createQuery(q);
		query.setParameter(p, cat);	
		
		return query.getResultList();
	}

	@Override
	public Optional<Ad> getByTitle(String title) {		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Ad> q = cb.createQuery(Ad.class);
		Root<Ad> c = q.from(Ad.class);
		
		ParameterExpression<String> p = cb.parameter(String.class);
		q.select(c).where(
				cb.equal(cb.lower(c.get(Ad.getTitleField())), cb.lower(p)),
				cb.equal(c.get(Ad.getDeletedField()), false)
				);
		
		TypedQuery<Ad> query = em.createQuery(q);
		query.setParameter(p, title);
		List<Ad> results = query.getResultList();
		
		if (!results.isEmpty()) {
			return Optional.of(results.get(0));
		}
		return Optional.empty();
		
		
	}
	
	@Override
	public List<Ad> getByUser(long us) {		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Ad> q = cb.createQuery(Ad.class);
		Root<Ad> c = q.from(Ad.class);
		
		ParameterExpression<Long> p = cb.parameter(Long.class);
		q.select(c).where(
				cb.equal(c.get(Ad.getUserIDField()), p),
				cb.equal(c.get(Ad.getDeletedField()), false)
				);
		
		TypedQuery<Ad> query = em.createQuery(q);
		query.setParameter(p, us);
		return query.getResultList();
		
	}

	@Override
	public Optional<Ad> getById(long id) {		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Ad> q = cb.createQuery(Ad.class);
		Root<Ad> c = q.from(Ad.class);
		
		ParameterExpression<Long> p = cb.parameter(Long.class);
		q.select(c).where(
				cb.equal(c.get("id"), p),
				cb.equal(c.get(Ad.getDeletedField()), false));
		
		TypedQuery<Ad> query = em.createQuery(q);
		query.setParameter(p, id);
		List<Ad> results = query.getResultList();
		
		if(!results.isEmpty()) {
			return Optional.of(results.get(0));
		}
		
		return Optional.empty();
	}
	
	
	
	
	@Override
	public boolean update(Ad ad) {
		Ad a = em.find(Ad.class, ad.getId());
		if (a == null || a.isDeleted()) {
			throw new IllegalArgumentException("Classad " +ad.getId() + " does not exist or was deleted.");
		}
		ad.setTime(a.getTime());
		em.merge(ad);
		return true;
	}
	

	@Override
	public void deleteAd(Ad ad) {
		Ad a = em.find(Ad.class, ad.getId());
		if (a != null) {
			a.setDeleted(true);
			em.merge(a);
		}
		
		//em.remove(em.contains(ad) ? ad : em.merge(ad));
		
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
}

