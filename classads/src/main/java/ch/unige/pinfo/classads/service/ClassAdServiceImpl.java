package ch.unige.pinfo.classads.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import ch.unige.pinfo.classads.model.ClassAd;

@ApplicationScoped
@Transactional
public class ClassAdServiceImpl implements ClassAdService{
	
	@PersistenceContext(name="InmemoryPU")
	private EntityManager em;

	@Override
	public boolean createClassAd(ClassAd c) {
		Optional<ClassAd> existing = this.getByTitle(c.getTitre());
		if(!existing.isPresent()) {
			em.persist(c);
			return true;
		}
		return false;
	}

	@Override
	public List<ClassAd> getAll() {
		List<ClassAd> ClassAds = em.createQuery("SELECT a FROM ClassAd a", ClassAd.class).getResultList();
		return ClassAds;
	}

	@Override
	public Optional<ClassAd> getByTitle(String title) {
		List<ClassAd> ClassAds = em.createQuery("SELECT a FROM ClassAd a WHERE LOWER(a.titre) = LOWER('"+title+"')", ClassAd.class).getResultList();
		if(ClassAds.size() > 0) {
			return Optional.of(ClassAds.get(0));
		}
		
		return Optional.empty();
	}

	@Override
	public Optional<ClassAd> getById(long id) {
		List<ClassAd> ClassAds = em.createQuery("SELECT a FROM ClassAd a WHERE LOWER(a.id) = LOWER('"+id+"')", ClassAd.class).getResultList();
		if(ClassAds.size() > 0) {
			return Optional.of(ClassAds.get(0));
		}
		
		return Optional.empty();
	}

	@Override
	public void deleteClassAd(ClassAd classad) {
		em.remove(em.contains(classad) ? classad : em.merge(classad));
		
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	
}

