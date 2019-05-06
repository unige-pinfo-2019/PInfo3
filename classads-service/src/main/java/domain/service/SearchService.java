package domain.service;

import domain.model.Ad;

public interface SearchService {
	
	public void insertAd(Ad ad);
	public Ad getAdById(String id);
}
