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
		
		System.out.println("Liste asIDs (taille: " + adids.size() + "):");
		for (Long adID: adids) {
			System.out.println("AD ID RECUPERE !!! " + adID);
		}
		SortAdResponsesByDate adsComp = new SortAdResponsesByDate();
		resultList.sort(adsComp.reversed());
		
		for (AdResponse loliloltest: resultList) {
			System.out.println(loliloltest.getTime().toString());
		}
		
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
		System.out.println("getResponsesFromiToj AAAA " + uid + " " + aid + " " + offset + " " + limit);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<AdResponse> q = cb.createQuery(AdResponse.class);
		Root<AdResponse> c = q.from(AdResponse.class);
		System.out.println("getResponsesFromiToj BBBB");

		q.select(c);
		ParameterExpression<Long> puid = cb.parameter(Long.class);
		ParameterExpression<Long> paid = cb.parameter(Long.class);
		System.out.println("getResponsesFromiToj CCCC");

		q.where(
				cb.equal(c.get(AdResponse.getAdIDField()), paid),
				cb.equal(c.get(AdResponse.getUserIDField()), puid)
		);
		q.orderBy(cb.desc(c.get(AdResponse.getTimeField())));
		
		System.out.println("getResponsesFromiToj DDDD");

		TypedQuery<AdResponse> query = em.createQuery(q);
		query.setParameter(puid, uid);
		query.setParameter(paid, aid);
		System.out.println("getResponsesFromiToj EEEE");
		return query.setFirstResult(offset).setMaxResults(limit).getResultList();
		
	}

	@Override
	public JsonArray getJsonListAdResponses(List<AdResponse> responses) {
		System.out.println("getJsonListAdResponses AAAA");
		JsonArray result = new JsonArray();
		for (AdResponse response: responses) {
			JsonObject jsonResponse = createJsonRepresentation(response);
			result.add(jsonResponse);
		}
		System.out.println("getJsonListAdResponses BBBB");

		return result;
	}

	@Override
	public JsonObject createJsonRepresentation(AdResponse response) {
		System.out.println("createJsonRepresentation " + "AAAA" + response.getId());
		JsonObject jsonResponse = new JsonObject();
		System.out.println("Jsonrep1");
		jsonResponse.addProperty(AdResponse.getIdField(), response.getId());
		System.out.println(response.getId());
		System.out.println("Jsonrep2");
		jsonResponse.addProperty(AdResponse.getAdIDField(), response.getAdID());
		System.out.println(response.getAdID());
		System.out.println("Jsonrep3");
		jsonResponse.addProperty(AdResponse.getUserIDField(), response.getUserID());
		System.out.println(response.getUserID());
		System.out.println("Jsonrep4");
		jsonResponse.addProperty(AdResponse.getResponseField(), response.getResponse());
		System.out.println(response.getResponse());
		System.out.println("Jsonrep5");
		jsonResponse.addProperty(AdResponse.getTimeField(), response.getTime().toString());
		System.out.println(response.getTime());
		System.out.println("Jsonrep6");
		jsonResponse.addProperty(AdResponse.getFlagField(), response.isFlag());
		System.out.println(response.isFlag());
		System.out.println("createJsonRepresentation " + "BBBB");
		return jsonResponse;
	}

}
