package domain.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import domain.model.Ad;
import domain.model.Categories;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

	@Override
	public Ad createAdFromJson(JsonObject json) {
		Ad ad = new Ad();
		//We need to decrypt the json object and instanciate the attributes of the ad
		try {
			//Some attributes are mandatory so they'll generate an exception if they don't exist
			
			//This includes the category id
			int categoryID = json.get("categoryID").getAsInt();
			
			//and the main attributes of an ad
			setMandatoryParameters(ad, json);
			
			//then, we try to set the parameters from the category
			setCategoryParameters(ad, categoryID, json);
			
			
		
		} catch (Exception e) {
			log.error("categoryID is missing or is not an Integer");
			return null;
		}
		return ad;
	}
	
	/***** Manipulation *****/
	private void setMandatoryParameters(Ad ad, JsonObject json) {
		try {
			ad.setTitle(json.get("title").getAsString());
			ad.setDescription(json.get("description").getAsString());
			ad.setPrice(json.get("price").getAsInt());
		} catch (Exception e) {
			log.error("Mandatory fields are missing (title, description or price)");
			return;
		}
	}
	
	private void setCategoryParameters(Ad ad, int categoryID, JsonObject json) {
		//For the attributes related to the category, we take the value if it exists or we assign the
		//default value
		Map<String, Object> attributes = Categories.getCategory(categoryID);
		Map<String, Integer> newAttributes_int = new HashMap<String, Integer>();
		Map<String, Boolean> newAttributes_bool = new HashMap<String, Boolean>();
		Map<String, String> newAttributes_string = new HashMap<String, String>();
		newAttributes_int.put("categoryID", categoryID);
		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String key = entry.getKey();
			try {
				JsonElement att = json.get(key);
				if (getType(att) == attributes.get(key).getClass()) {
					if(getType(att)== int.class) newAttributes_int.put(key, json.get(key).getAsInt());
					if(getType(att)== boolean.class) newAttributes_bool.put(key, json.get(key).getAsBoolean());
					if(getType(att)== String.class) newAttributes_string.put(key, json.get(key).getAsString());
				} else {
					if(attributes.get(key).getClass()== int.class) newAttributes_int.put(key, (Integer) attributes.get(key));
					if(attributes.get(key).getClass()== boolean.class) newAttributes_bool.put(key, (Boolean) attributes.get(key));
					if(attributes.get(key).getClass()== String.class) newAttributes_string.put(key, (String) attributes.get(key));
				}
			} catch (Exception e) {
				log.warn("Key " + key + " doesn't exist in json");
			}
		}
		
		ad.setCategory(newAttributes_int,newAttributes_bool, newAttributes_string);
	}
	
	/* Find the type of a JsonElement (either boolean, string or integer) */
	private Object getType(JsonElement var) {
		if (var.getAsJsonPrimitive().isBoolean()) {
			return Boolean.class;
		}
		else if (var.getAsJsonPrimitive().isString()) {
			return String.class;
		}
		else if (var.getAsJsonPrimitive().isNumber()) {
			return Integer.class;
		}
		return null;
	}
	
	
}

