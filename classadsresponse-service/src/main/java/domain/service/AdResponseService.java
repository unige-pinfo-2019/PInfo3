package domain.service;

import java.util.List;

import domain.model.AdResponse;

public interface AdResponseService {
	
	public void createAdResponse(AdResponse adresp);
	
	public List<AdResponse> getResponsesFromiToj (long uid, long aid, int offset, int limit);
	
	public List<AdResponse> getByUser(long uid);

}
