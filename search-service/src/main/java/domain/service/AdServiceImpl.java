package domain.service;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import com.google.gson.JsonObject;

import domain.model.Ad;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class AdServiceImpl {
	
	public Ad buildAdFromJson(JsonObject json) {
	    Ad ad = new Ad();
	    Map<String, Object> catAttr = new HashMap<>();
	    try {
	      if (json.has("id") && json.has(Ad.getTitleField()) && json.has(Ad.getDescriptionField()) && json.has(Ad.getPriceField())) {
	        ad.setId(json.get("id").getAsLong());
	        json.remove("id");
	        
	        ad.setTitle(json.get(Ad.getTitleField()).getAsString());
	        json.remove(Ad.getTitleField());

	        ad.setDescription(json.get(Ad.getDescriptionField()).getAsString());
	        json.remove(Ad.getDescriptionField());

	        ad.setPrice(json.get(Ad.getPriceField()).getAsFloat());
	        json.remove(Ad.getPriceField());
	      } else {
	        throw new IllegalArgumentException();
	      }
	    } catch (IllegalArgumentException e) {
	      log.error("The mandatory fields (title, description, price) are missing");
	    }

	    for (String key : json.keySet()) {
	      catAttr.put(key, json.get(key));
	    }
	    ad.setCategoryAttributes(catAttr);
	    return ad;
	  }
}
