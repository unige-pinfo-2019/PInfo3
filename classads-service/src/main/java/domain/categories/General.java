package domain.categories;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.gson.JsonObject;

import domain.model.Ad;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "GENERAL")
@DiscriminatorValue("GENERAL")
public class General extends Ad {
	
	private static final long serialVersionUID = -921278118126506709L;

	public static JsonObject getJSONAttributes() {
		JsonObject json = Ad.getJSONAttributes();
		return json;
	}
	
	@Override
	public boolean setParameters(JsonObject json, long userID) {
		return super.setMandatoryParameters(json, userID);
	}
	
	public Ad getNewInstance() {
		return new General();
	}
	
	public JsonObject getJsonValues() {
		JsonObject json = super.getJsonValues();
		return json;
	}
	

}
