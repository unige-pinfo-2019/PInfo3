package domain.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
		return em.createQuery("SELECT a FROM Ad a", Ad.class).getResultList();

	}

	@Override
	public Optional<Ad> getByTitle(String title) {
		List<Ad> ads = em.createQuery("SELECT a FROM Ad a WHERE LOWER(a.title) = LOWER('"+title+"')", Ad.class).getResultList();
		if(ads.size() > 0) {
			return Optional.of(ads.get(0));
		}
		
		return Optional.empty();
	}

	@Override
	public Optional<Ad> getById(long id) {
		List<Ad> ads = em.createQuery("SELECT a FROM Ad a WHERE LOWER(a.id) = LOWER('"+id+"')", Ad.class).getResultList();
		if(ads.size() > 0) {
			return Optional.of(ads.get(0));
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

