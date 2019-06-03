package domain.service;

import java.util.List;
import java.util.Optional;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domain.model.Ad;

public interface AdService {
	
	public boolean createAd(Ad ad);
	
	public List<Ad> getAll();
	
	public List<Ad> getAllByCategory(int cat);
	
	public Optional<Ad> getByTitle(String title);
	
	public List<Ad> getByUser(long us);
	
	public Optional<Ad> getById(long id);
	
	public boolean update(Ad ad);
		
	public void deleteAd(Ad ad);
	
	public Ad createAdFromJson(JsonObject json);
	
	public JsonArray getJsonListAds(List<Ad> ads);
	
	public JsonObject createJsonRepresentation(Ad ad);

}
