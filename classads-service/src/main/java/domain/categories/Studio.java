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
public class Studio extends Property {
	
	private static final long serialVersionUID = 6632845671512550210L;
	private float flatSize = -1;
	
	private static String flatSizeFieldName = "Taille du studio";
	
	public static JsonObject getJSONAttributes() {
		JsonObject json = Property.getJSONAttributes();
		json.addProperty(flatSizeFieldName, "number");
		return json;
	}

	@Override
	public boolean setParameters(JsonObject json, long userID) {
		if (super.setMandatoryParameters(json, userID)) {
			if (json.has(flatSizeFieldName))
				this.setFlatSize(json.get(flatSizeFieldName).getAsFloat());
		}
		return false;
	}
	
	public Ad getNewInstance() {
		return new Studio();
	}
	
	public JsonObject getJsonValues() {
		JsonObject json = super.getJsonValues();
		json.addProperty(flatSizeFieldName, this.flatSize);
		return json;
	}
	

}
