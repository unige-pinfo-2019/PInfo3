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
@Table(name = "BIKES")
@DiscriminatorValue("BIKES")
public class Bike extends Ad {
	
	private static final long serialVersionUID = 6632845671512550210L;
	private String color = null;
	private String type = null;
	
	private static String colorFieldName = "Color";
	private static String typeFieldName = "VTT/VTC/Electrique";
	
	public static JsonObject getJSONAttributes() {
		JsonObject json = Ad.getJSONAttributes();
		json.addProperty(colorFieldName, "text");
		json.addProperty(typeFieldName, "text");
		return json;
	}

	@Override
	public boolean setParameters(JsonObject json, long userID) {
		if (super.setMandatoryParameters(json, userID)) {
			if (json.has(colorFieldName))
				this.setColor(json.get(colorFieldName).getAsString());
			
			if (json.has(typeFieldName))
				this.setType(json.get(typeFieldName).getAsString());
		
		}
		return false;
	}
	
	public Ad getNewInstance() {
		return new Bike();
	}
	
	public JsonObject getJsonValues() {
		JsonObject json = super.getJsonValues();
		json.addProperty(colorFieldName, this.color);
		json.addProperty(typeFieldName, this.type);
		return json;
	}
	

}
