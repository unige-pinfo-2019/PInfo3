package domain.categories;

import com.google.gson.JsonObject;

import domain.model.Ad;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class General extends Ad {
	
	private static final long serialVersionUID = -921278118126506709L;

	public static JsonObject getJSONAttributes() {
		JsonObject json = Ad.getJSONAttributes();
		return json;
	}
	

}
