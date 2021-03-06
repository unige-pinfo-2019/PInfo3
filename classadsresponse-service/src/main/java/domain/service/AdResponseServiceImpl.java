package domain.service;

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

import domain.model.AdResponse;
import domain.model.SortAdResponsesByDate;


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
		
		Root<AdResponse> t = aid.from(AdResponse.class);
		aid.select(t.get(AdResponse.getAdIDField()));
		
		
		ParameterExpression<Long> puid = cb.parameter(Long.class);

		aid.where(
				cb.equal(t.get(AdResponse.getUserIDField()), puid)
				);
		aid.groupBy(t.get(AdResponse.getAdIDField()));
		
		TypedQuery<Long> query = em.createQuery(aid);
		query.setParameter(puid, uid);
		
		List<Long> adids = query.getResultList();
		
		List<AdResponse> resultList = new ArrayList<>();
		
		for (Long adID: adids) {
			resultList.addAll(this.getResponsesFromiToj(uid, adID, 0, 1));
		}
		
		SortAdResponsesByDate adsComp = new SortAdResponsesByDate();
		resultList.sort(adsComp.reversed());
		
		return resultList;
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
}
