package domain.service;

import com.google.gson.JsonObject;

import domain.model.Ad;

public interface AdService {
	
	public Ad buildAdFromJson(JsonObject json);

}
