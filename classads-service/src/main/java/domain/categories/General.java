package domain.categories;

import com.google.gson.JsonObject;

import domain.model.Ad;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class General extends Ad {
	
	private static final long serialVersionUID = -921278118126506709L;

	public JsonObject getJSONAttributes() {
		JsonObject json = new JsonObject();
		return json;
	}
	

}
