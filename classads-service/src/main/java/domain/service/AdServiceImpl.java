package domain.service;

import java.util.Collection;
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

import com.google.gson.JsonArray;
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
		Ad ad = new Ad();
		//We need to decrypt the json object and instanciate the attributes of the ad
		try {
			//Some attributes are mandatory so they'll generate an exception if they don't exist
			
			//This includes the category id
			int categoryID = json.get(Categories.getCategoryIDField()).getAsInt();
			Collection<Integer> indices = Categories.getCategoryIndex().values();
			if (!indices.contains(categoryID)) {
				throw new IllegalArgumentException("Bad categoryID");
			}
			
			//and the main attributes of an ad
			if (setMandatoryParameters(ad, json)) {
				//then, we try to set the parameters from the category
				setCategoryParameters(ad, categoryID, json);
			} else {
				throw new IllegalArgumentException("Mandatory fields are missing");
			}
		
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
		return ad;
	}
	
	/***** Manipulation *****/
	private Boolean setMandatoryParameters(Ad ad, JsonObject json) {
		try {
			ad.setTitle(json.get(Ad.getTitleField()).getAsString());
			ad.setDescription(json.get(Ad.getDescriptionField()).getAsString());
			ad.setPrice(json.get(Ad.getPriceField()).getAsInt());
		} catch (Exception e) {
			log.error("Mandatory fields are missing (title, description or price)");
			return false;
		}
		return true;
	}
	
	private Boolean addAttributeToMap(String key, JsonObject json, Map<String, Object> attributes, Map<String, Integer> mapInt, Map<String, Boolean> mapBool, Map<String, String> mapString) {
		//Check if the key exist
		if (json.has(key)) {
			//Add the attributes in the right map
			JsonElement att = json.get(key);
			if (getType(att) == attributes.get(key).getClass()) {
				if(getType(att)== Integer.class) mapInt.put(key, json.get(key).getAsInt());
				if(getType(att)== Boolean.class) mapBool.put(key, json.get(key).getAsBoolean());
				if(getType(att)== String.class) mapString.put(key, json.get(key).getAsString());
			} else {
			//Add the default value if the attribute doesn't exist
				if(attributes.get(key).getClass()== Integer.class) mapInt.put(key, (Integer) attributes.get(key));
				if(attributes.get(key).getClass()== Boolean.class) mapBool.put(key, (Boolean) attributes.get(key));
				if(attributes.get(key).getClass()== String.class) mapString.put(key, (String) attributes.get(key));
			}
			return true;
		} else {
			return false;
		}
	}
	
	
	private void setCategoryParameters(Ad ad, int categoryID, JsonObject json) {
		//For the attributes related to the category, we take the value if it exists or we assign the
		//default value
		
		//Initialize maps
		Map<String, Object> attributes = Categories.getCategory(categoryID);
		Map<String, Integer> newAttributesInt = new HashMap<>();
		Map<String, Boolean> newAttributesBool = new HashMap<>();
		Map<String, String> newAttributesString = new HashMap<>();
		
		//Set the attributes for the category
		newAttributesInt.put(Categories.getCategoryIDField(), categoryID);
		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String key = entry.getKey();
			if (!addAttributeToMap(key, json, attributes, newAttributesInt, newAttributesBool, newAttributesString)) {
				log.warn("The category field "+key+" wasn't specified");
			}
		}
		
		ad.setCategory(newAttributesInt,newAttributesBool, newAttributesString);
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

	@Override
	public JsonArray getJsonListAds(List<Ad> ads) {
		JsonArray result = new JsonArray();
		
		for (Ad ad : ads) {
			JsonObject jsonAd = new JsonObject();
			jsonAd.addProperty(Ad.getTitleField(), ad.getTitle());
			jsonAd.addProperty(Ad.getDescriptionField(), ad.getDescription());
			jsonAd.addProperty(Ad.getPriceField(), ad.getPrice());
			for (Map.Entry<String, Integer> entryInt : ad.getCategoryInt().entrySet()) {
				jsonAd.addProperty(entryInt.getKey(), entryInt.getValue());
			}
			for (Map.Entry<String, Boolean> entryBool : ad.getCategoryBool().entrySet()) {
				jsonAd.addProperty(entryBool.getKey(), entryBool.getValue());
			}
			for (Map.Entry<String, String> entryString : ad.getCategoryString().entrySet()) {
				jsonAd.addProperty(entryString.getKey(), entryString.getValue());
			}
			result.add(jsonAd);
		}
		return result;
	}
	
	
}

