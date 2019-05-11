package domain.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domain.model.Ad;
import domain.model.Categories;
import domain.model.Category;
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
		//log.info("Ad "+ ad.toString()+" created and stored in database");
		return true;
	}

	@Override
	public List<Ad> getAll() {
		CriteriaBuilder qb = getEm().getCriteriaBuilder();
		CriteriaQuery<Ad> c = qb.createQuery(Ad.class);
		Root<Ad> adRoot = c.from(Ad.class);
		c.select(adRoot);
		return getEm().createQuery(c).getResultList();
	}

	@Override
	public Optional<Ad> getByTitle(String title) {		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Ad> q = cb.createQuery(Ad.class);
		Root<Ad> c = q.from(Ad.class);
		
		ParameterExpression<String> p = cb.parameter(String.class);
		q.select(c).where(cb.equal(cb.lower(c.get(Ad.getTitleField())), cb.lower(p)));
		
		TypedQuery<Ad> query = em.createQuery(q);
		query.setParameter(p, title);
		List<Ad> results = query.getResultList();
		
		if (!results.isEmpty()) {
			return Optional.of(results.get(0));
		}
		return Optional.empty();
		
		
	}

	@Override
	public Optional<Ad> getById(long id) {		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Ad> q = cb.createQuery(Ad.class);
		Root<Ad> c = q.from(Ad.class);
		
		ParameterExpression<Long> p = cb.parameter(Long.class);
		q.select(c).where(cb.equal(c.get("id"), p));
		
		TypedQuery<Ad> query = em.createQuery(q);
		query.setParameter(p, id);
		List<Ad> results = query.getResultList();
		
		if(!results.isEmpty()) {
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


	@Override
	public Ad createAdFromJson(JsonObject json) {
		log.info("Enter the function createAdFromJson");
		Ad ad = null;
		try {
			if (hasValidCategoryID(json)) {
				log.info("Has category ID");
				int categoryID = json.get(Categories.getCategoryIDField()).getAsInt();
				Category cat = Categories.getCategoryById(categoryID);
				
				if (hasMandatoryParameters(json)) {
					log.info("has mandatory fields");
					
					try {

						ad = (Ad) cat.getClassName().getDeclaredConstructor().newInstance();
//						Method m = cat.getClassName().getMethod("getNewInstance");
//						ad = (Ad) m.invoke(ad);
						log.info("Ad created"+ad);
						if (!ad.setParameters(json, 0)) {
							throw new IllegalArgumentException();
						}
						
					} catch (SecurityException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
						log.error("Couldn't call method to instantiate attributes. "+e.getMessage());
					} catch (IllegalArgumentException e) {
						log.error("Couldn't extract properties from json");
					}
					
				} else {
					new IllegalArgumentException("Mandatory fields are missing");
				}
				
				
			} else {
				throw new IllegalArgumentException("Bad categoryID");
			}
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
		}
		return ad;
	}
	
	/***** Manipulation *****/
	private Boolean hasMandatoryParameters(JsonObject json) {
		if (!(json.has(Ad.getTitleField()) && json.has(Ad.getDescriptionField()) && json.has(Ad.getPriceField()))) {
			return false;
		}
		return true;
	}
	
	private boolean hasValidCategoryID(JsonObject json) {
		if (json.has(Categories.getCategoryIDField())) {
			Set<Integer> indices = Categories.getCategoriesID();
			if (indices.contains(json.get(Categories.getCategoryIDField()).getAsInt())) {
				return true;
			}
		} 
		return false;
	}
	

	@Override
	public JsonArray getJsonListAds(List<Ad> ads) {
		JsonArray result = new JsonArray();
		
		for (Ad ad : ads) {
			JsonObject jsonAd = ad.getJsonValues();
			result.add(jsonAd);
		}
		return result;
	}
	
	
}

