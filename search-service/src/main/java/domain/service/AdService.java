package domain.service;

import com.google.gson.JsonObject;

import domain.model.AdSearchable;

public interface AdService {
	
	public AdSearchable buildAdFromJson(JsonObject json);

}
