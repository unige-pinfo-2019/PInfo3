package domain.service;

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
		q.select(c).where(cb.equal(c.get(Ad.getCategoryIDField()), p));
		
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
	public List<Ad> getByUser(long us) {		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Ad> q = cb.createQuery(Ad.class);
		Root<Ad> c = q.from(Ad.class);
		
		ParameterExpression<Long> p = cb.parameter(Long.class);
		q.select(c).where(cb.equal(c.get(Ad.getUserIDField()), p));
		
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
	public boolean update(Ad ad) {
		Ad a = em.find(Ad.class, ad.getId());
		if (a == null) {
			throw new IllegalArgumentException("Classads does not exist : " + ad.getId());
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

	@Override
	public Ad createAdFromJson(JsonObject json) {
		//We need to decrypt the json object and instanciate the attributes of the ad
		Ad ad;
		
		try {				// if the id is given in the jsonObjet, we use it to initialise an ad (typically for PUT querrys)
			long id = json.get(Ad.getIdField()).getAsLong();
			ad = new Ad(id);
		}catch (Exception e) {	// else we let the Id be automatiquely generated
			ad = new Ad();
		}
		try {				// if the Date is given we change the automatically generated time
			ad.setTime(json.get(Ad.getTimeField()).getAsString());
		}catch (Exception e) { /* if the Date is not given we use the generated time */}
		
		try {
			//Some attributes are mandatory so they'll generate an exception if they don't exist
			
			//This includes the category id
			int categoryID = json.get(Categories.getCategoryIDField()).getAsInt();
			Set<Integer> indices = Categories.getCategoriesIndex();
			if (!indices.contains(categoryID)) {
				throw new IllegalArgumentException("Bad categoryID");
			}
			ad.setCategoryID(categoryID);
			
			//and the main attributes of an ad
			if (!setMandatoryParameters(ad, json)) {
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
			ad.setPrice(json.get(Ad.getPriceField()).getAsFloat());
			ad.setUserID(json.get(Ad.getUserIDField()).getAsLong());
			ad.setImagesFromJson(json.get(Ad.getImageField()).getAsJsonArray());
		} catch (Exception e) {
			log.error("Mandatory fields are missing (title, description, price, userID or image)");
			return false;
		}
		return true;
	}

	@Override
	public JsonArray getJsonListAds(List<Ad> ads) {
		JsonArray result = new JsonArray();
		for (Ad ad : ads) {
			JsonObject jsonAd = createJsonRepresentation(ad);
			result.add(jsonAd);
		}
		return result;
	}
	
	public JsonObject createJsonRepresentation(Ad ad) {
		JsonObject jsonAd = new JsonObject();
		jsonAd.addProperty(Ad.getIdField(), ad.getId());
		jsonAd.addProperty(Ad.getTitleField(), ad.getTitle());
		jsonAd.addProperty(Ad.getDescriptionField(), ad.getDescription());
		jsonAd.addProperty(Ad.getPriceField(), ad.getPrice());
		jsonAd.addProperty(Ad.getUserIDField(), ad.getUserID());
		jsonAd.addProperty(Categories.getCategoryIDField(), ad.getCategoryID());
		jsonAd.add(Ad.getImageField(), getImagesInJson(ad));
		jsonAd.addProperty(Ad.getTimeField(), ad.getTime());
		jsonAd.addProperty(Ad.getNbVuesField(), ad.getNbVues());
		
		return jsonAd;
	}
	
	
	public JsonArray getImagesInJson(Ad ad) {
		JsonArray json = new JsonArray();
		for(int i=0; i<ad.getImages().size(); i++) json.add(ad.getImages().get(i));
		return json;
	}
	
	
}

