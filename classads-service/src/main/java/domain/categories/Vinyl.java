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
@Table(name = "VINYLS")
@DiscriminatorValue("VINYLS")
public class Vinyl extends Ad {
	
	private static final long serialVersionUID = 6632845671512550210L;
	private String singers = null;
	private String album = null;
	
	private static String singersFieldName = "Artiste(s)";
	private static String albumFieldName = "Titre de l'album";
	
	public static JsonObject getJSONAttributes() {
		JsonObject json = Ad.getJSONAttributes();
		json.addProperty(singersFieldName, "text");
		json.addProperty(albumFieldName, "text");
		return json;
	}

	@Override
	public boolean setParameters(JsonObject json, long userID) {
		if (super.setMandatoryParameters(json, userID)) {
			if (json.has(singersFieldName))
				this.setSingers(json.get(singersFieldName).getAsString());
			
			if (json.has(albumFieldName))
				this.setAlbum(json.get(albumFieldName).getAsString());
		}
		return false;
	}
	
	public Ad getNewInstance() {
		return new Vinyl();
	}
	
	public JsonObject getJsonValues() {
		JsonObject json = super.getJsonValues();
		json.addProperty(singersFieldName, this.singers);
		json.addProperty(albumFieldName, this.album);
		return json;
	}
	

}
