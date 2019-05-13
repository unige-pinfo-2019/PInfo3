package domain.service;

import com.google.gson.JsonArray;

import domain.model.AdSearchable;

public interface SearchService {
	
	public void insertAd(AdSearchable ad);
	public void updateAd(AdSearchable ad);
	public AdSearchable getAdById(String id);
	public void deleteAdById(String id);
	
	public JsonArray searchResquet(String request);

}
