package domain.categories;

import com.google.gson.JsonObject;

import domain.model.Ad;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Computers extends Ad {
	
	private static final long serialVersionUID = 5972446884000830076L;
	private float size = -1;
	private String brand = null;
	private int memorySize = -1;
	
	public static JsonObject getJSONAttributes() {
		JsonObject json = Ad.getJSONAttributes();
		json.addProperty("size", "number");
		json.addProperty("brand", "text");
		json.addProperty("memorySize", "number");
		return json;
	}
	
	

}
