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

@ApplicationScoped
@Transactional
public class AdServiceImpl implements AdService{

	@PersistenceContext(name="ClassAdsPU")
	private EntityManager em;

	@Override
	public boolean createAd(Ad ad) {
		Optional<Ad> existing = this.getByTitle(ad.getTitle());
		if(!existing.isPresent()) {
			em.persist(ad);
			return true;
		}
		return false;
	}

	@Override
	public List<Ad> getAll() {
		//return em.createQuery("SELECT a FROM Ad a", Ad.class).getResultList();
		CriteriaBuilder qb = getEm().getCriteriaBuilder();
		CriteriaQuery<Ad> c = qb.createQuery(Ad.class);
		Root<Ad> adRoot = c.from(Ad.class);
		c.select(adRoot);
		return getEm().createQuery(c).getResultList();
	}

	@Override
	public Optional<Ad> getByTitle(String title) {
//		List<Ad> ads = em.createQuery("SELECT a FROM Ad a WHERE LOWER(a.title) = LOWER('"+title+"')", Ad.class).getResultList();
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Ad> q = cb.createQuery(Ad.class);
		Root<Ad> c = q.from(Ad.class);
		
		ParameterExpression<String> p = cb.parameter(String.class);
		q.select(c).where(cb.equal(cb.lower(c.get("title")), cb.lower(p)));
		
		TypedQuery<Ad> query = em.createQuery(q);
		query.setParameter(p, title);
		List<Ad> results = query.getResultList();
		
		if (results.size() > 0) {
			return Optional.of(results.get(0));
		}
		return Optional.empty();
		
		
	}

	@Override
	public Optional<Ad> getById(long id) {
		// List<Ad> ads = em.createQuery("SELECT a FROM Ad a WHERE LOWER(a.id) = LOWER('"+id+"')", Ad.class).getResultList();
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Ad> q = cb.createQuery(Ad.class);
		Root<Ad> c = q.from(Ad.class);
		
		ParameterExpression<Long> p = cb.parameter(Long.class);
		q.select(c).where(cb.equal(c.get("id"), p));
		
		TypedQuery<Ad> query = em.createQuery(q);
		query.setParameter(p, id);
		List<Ad> results = query.getResultList();
		
		if(results.size() > 0) {
			return Optional.of(results.get(0));
		}
		
		return Optional.empty();
	}

	@Override
	public void deleteAd(Ad ad) {
		em.remove(em.contains(ad) ? ad : em.merge(ad));
		
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	
}

