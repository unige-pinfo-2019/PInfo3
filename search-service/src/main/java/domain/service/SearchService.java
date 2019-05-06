package domain.service;

import com.google.gson.JsonArray;

import domain.model.Ad;

public interface SearchService {
	
	public void insertAd(Ad ad);
	public void updateAd(Ad ad);
	public Ad getAdById(String id);
	public void deleteAdById(String id);
	public JsonArray searchResquet(String request);
}
