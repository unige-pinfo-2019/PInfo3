package domain.categories;

import com.google.gson.JsonObject;

import domain.model.Ad;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Books extends Ad {
	
	private static final long serialVersionUID = 6632845671512550210L;
	String authors = null;
	String publisher = null;
	
	public JsonObject getJSONAttributes() {
		JsonObject json = super.getJSONAttributes();
		json.addProperty("authors", "");
		json.addProperty("publisher", "");
		return json;
	}
	

}
