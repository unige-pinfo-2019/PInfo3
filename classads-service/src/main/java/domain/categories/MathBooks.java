package domain.categories;

import com.google.gson.JsonObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MathBooks extends Books {
	
	private static final long serialVersionUID = 6632845671512550210L;
	String theme = null;
	
	public JsonObject getJSONAttributes() {
		JsonObject json = super.getJSONAttributes();
		json.addProperty("theme", "");
		return json;
	}
	

}
