package domain.categories;

import com.google.gson.JsonObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MathBooks extends Books {
	
	private static final long serialVersionUID = 6632845671512550210L;
	private String theme = null;
	
	public static JsonObject getJSONAttributes() {
		JsonObject json = Books.getJSONAttributes();
		json.addProperty("theme", "text");
		return json;
	}
	

}
