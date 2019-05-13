package domain.service;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import com.google.gson.JsonObject;

import domain.model.AdSearchable;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class AdServiceImpl {
	
	public AdSearchable buildAdFromJson(JsonObject json) {
	    AdSearchable ad = new AdSearchable();
	    Map<String, Object> catAttr = new HashMap<>();
	    try {
	      if (json.has("id") && json.has(AdSearchable.getTitleField()) && json.has(AdSearchable.getDescriptionField()) && json.has(AdSearchable.getPriceField())) {
	        ad.setId(json.get("id").getAsLong());
	        json.remove("id");
	        
	        ad.setTitle(json.get(AdSearchable.getTitleField()).getAsString());
	        json.remove(AdSearchable.getTitleField());

	        ad.setDescription(json.get(AdSearchable.getDescriptionField()).getAsString());
	        json.remove(AdSearchable.getDescriptionField());

	        ad.setPrice(json.get(AdSearchable.getPriceField()).getAsFloat());
	        json.remove(AdSearchable.getPriceField());
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
