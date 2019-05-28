package domain.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

import domain.model.AdResponse;
import domain.model.SortAdResponsesByDate;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@ApplicationScoped
@Transactional
public class AdResponseServiceImpl implements AdResponseService {
	
	@PersistenceContext(name="AdResponsePU")
	private EntityManager em;

	@Override
	public void createAdResponse(AdResponse adresp) {
		em.persist(adresp);
	}

	@Override
	public List<AdResponse> getByUser(long uid) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> aid = cb.createQuery(Long.class);
		
		Root<AdResponse> T = aid.from(AdResponse.class);
		aid.select(T.get(AdResponse.getAdIDField()));
		
		
		ParameterExpression<Long> puid = cb.parameter(Long.class);

		aid.where(
				cb.equal(T.get(AdResponse.getUserIDField()), puid)
				);
		aid.groupBy(T.get(AdResponse.getAdIDField()));
		
		TypedQuery<Long> query = em.createQuery(aid);
		query.setParameter(puid, uid);
		
		List<Long> adids = query.getResultList();
		
		List<AdResponse> resultList = new ArrayList<AdResponse>();
		
		for (Long adID: adids) {
			resultList.addAll(this.getResponsesFromiToj(uid, adID, 0, 1));
		}
		
		SortAdResponsesByDate adsComp = new SortAdResponsesByDate();
		resultList.sort(adsComp.reversed());
		
		return resultList;
	}

	@Override
	public AdResponse createAdResponseFromJson(JsonObject json) {
		AdResponse adresp = new AdResponse();
		//We need to decrypt the json object and instanciate the attributes of the ad response
		try {
			adresp = new AdResponse(json.get(AdResponse.getAdIDField()).getAsLong(),
					json.get(AdResponse.getUserIDField()).getAsLong(),
					json.get(AdResponse.getResponseField()).getAsString(),
					json.get(AdResponse.getFlagField()).getAsBoolean());
		} catch (Exception e) {
			log.error("Mandatory fields are missing (userID, adID or response)");
			return null;
		}
		return adresp;
	}

	@Override
	public List<AdResponse> getResponsesFromiToj(long uid, long aid, int offset, int limit) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<AdResponse> q = cb.createQuery(AdResponse.class);
		Root<AdResponse> c = q.from(AdResponse.class);

		q.select(c);
		ParameterExpression<Long> puid = cb.parameter(Long.class);
		ParameterExpression<Long> paid = cb.parameter(Long.class);

		q.where(
				cb.equal(c.get(AdResponse.getAdIDField()), paid),
				cb.equal(c.get(AdResponse.getUserIDField()), puid)
		);
		q.orderBy(cb.desc(c.get(AdResponse.getTimeField())));
		

		TypedQuery<AdResponse> query = em.createQuery(q);
		query.setParameter(puid, uid);
		query.setParameter(paid, aid);
		return query.setFirstResult(offset).setMaxResults(limit).getResultList();
		
	}

	@Override
	public JsonArray getJsonListAdResponses(List<AdResponse> responses) {
		JsonArray result = new JsonArray();
		for (AdResponse response: responses) {
			JsonObject jsonResponse = createJsonRepresentation(response);
			result.add(jsonResponse);
		}

		return result;
	}

	@Override
	public JsonObject createJsonRepresentation(AdResponse response) {
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty(AdResponse.getIdField(), response.getId());
		jsonResponse.addProperty(AdResponse.getAdIDField(), response.getAdID());
		jsonResponse.addProperty(AdResponse.getUserIDField(), response.getUserID());
		jsonResponse.addProperty(AdResponse.getResponseField(), response.getResponse());
		jsonResponse.addProperty(AdResponse.getTimeField(), response.getTime().toString());
		jsonResponse.addProperty(AdResponse.getFlagField(), response.isFlag());
		return jsonResponse;
	}

}
