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
@Table(name = "FLAT_SHARES")
@DiscriminatorValue("FLAT_SHARES")
public class FlatShare extends Property {
	
	private static final long serialVersionUID = 6632845671512550210L;
	private int nbCoMates = -1;
	private float roomSize = -1;
	
	private static String nbCoMatesFieldName = "Nombre de colocataires";
	private static String roomSizeFieldName = "Taille de la chambre";
	
	public static JsonObject getJSONAttributes() {
		JsonObject json = Property.getJSONAttributes();
		json.addProperty(nbCoMatesFieldName, "number");
		json.addProperty(roomSizeFieldName, "number");
		return json;
	}

	@Override
	public boolean setParameters(JsonObject json, long userID) {
		if (super.setMandatoryParameters(json, userID)) {
			if (json.has(nbCoMatesFieldName))
				this.setNbCoMates(json.get(nbCoMatesFieldName).getAsInt());
			
			if (json.has(roomSizeFieldName))
				this.setRoomSize(json.get(roomSizeFieldName).getAsFloat());
			
		}
		return false;
	}
	
	public Ad getNewInstance() {
		return new FlatShare();
	}
	
	public JsonObject getJsonValues() {
		JsonObject json = super.getJsonValues();
		json.addProperty(nbCoMatesFieldName, this.nbCoMates);
		json.addProperty(roomSizeFieldName, this.roomSize);
		return json;
	}
	

}
