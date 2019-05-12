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
@Table(name = "STUDIOS")
@DiscriminatorValue("STUDIOS")
public class Studio extends Ad {
	
	private static final long serialVersionUID = 6632845671512550210L;
	private float flatSize = -1;
	private String address = null;
	private String type = null;
	
	private static String addressFieldName = "Adresse";
	private static String typeFieldName = "Type d'habitation";
	private static String flatSizeFieldName = "Taille du studio";
	
	public static JsonObject getJSONAttributes() {
		JsonObject json = Ad.getJSONAttributes();
		json.addProperty(flatSizeFieldName, "number");
		json.addProperty(addressFieldName, "text");
		json.addProperty(typeFieldName, "text");
		return json;
	}

	@Override
	public boolean setParameters(JsonObject json, long userID) {
		if (super.setMandatoryParameters(json, userID)) {
			if (json.has(flatSizeFieldName))
				this.setFlatSize(json.get(flatSizeFieldName).getAsFloat());
		
			if (json.has(addressFieldName))
				this.setAddress(json.get(addressFieldName).getAsString());
			
			if (json.has(typeFieldName))
				this.setAddress(json.get(typeFieldName).getAsString());
		
		}
		return false;
	}
	
	public Ad getNewInstance() {
		return new Studio();
	}
	
	public JsonObject getJsonValues() {
		JsonObject json = super.getJsonValues();
		json.addProperty(flatSizeFieldName, this.flatSize);
		json.addProperty(addressFieldName, this.address);
		json.addProperty(typeFieldName, this.type);
		return json;
	}
	

}
