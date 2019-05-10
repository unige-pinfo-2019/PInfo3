package domain.categories;

import com.google.gson.JsonObject;

import domain.model.Ad;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Books extends Ad {
	
	private static final long serialVersionUID = 6632845671512550210L;
	private String authors = null;
	private String publisher = null;
	
	public static JsonObject getJSONAttributes() {
		JsonObject json = Ad.getJSONAttributes();
		json.addProperty("authors", "text");
		json.addProperty("publisher", "text");
		return json;
	}
	

}
