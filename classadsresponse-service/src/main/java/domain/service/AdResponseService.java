package domain.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domain.model.AdResponse;

public interface AdResponseService {
	
	public void createAdResponse(AdResponse adresp);
	
	public List<AdResponse> getResponsesFromiToj (long uid, long aid, int offset, int limit);
	
	public List<AdResponse> getByUser(long uid);

	public AdResponse createAdResponseFromJson(JsonObject json);
	
	public JsonArray getJsonListAdResponses(List<AdResponse> responses);
	
	public JsonObject createJsonRepresentation(AdResponse response);

}
