package domain.service;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import com.google.gson.JsonObject;

import domain.model.Ad;
import domain.model.AdSearchable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class AdSearchableServiceImpl implements AdSearchableService {
	
	public AdSearchable convertAdToAdSearchable(Ad ad) {
		AdSearchable newAd = new AdSearchable();
		Map<String, Object> catAttr = new HashMap<>();
		
		JsonObject json = ad.getJsonValues();
		
		try {
	      if (json.has(Ad.getTitleField()) && json.has(Ad.getDescriptionField()) && json.has(Ad.getPriceField())) {        
	    	newAd.setTitle(json.get(Ad.getTitleField()).getAsString());
	        json.remove(Ad.getTitleField());

	        newAd.setDescription(json.get(Ad.getDescriptionField()).getAsString());
	        json.remove(Ad.getDescriptionField());

	        newAd.setPrice(json.get(Ad.getPriceField()).getAsFloat());
	        json.remove(Ad.getPriceField());
	      } else {
	        throw new IllegalArgumentException();
	      }
	    } catch (IllegalArgumentException e) {
	      log.error("The mandatory fields (title, description, price) are missing");
	    }
		
		newAd.setId(ad.getId());
		
		for (String key : json.keySet()) {
	      catAttr.put(key, json.get(key));
	    }
	    newAd.setCategoryAttributes(catAttr);
		return newAd;
	}

}
