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
@Table(name = "MATH_BOOKS")
@DiscriminatorValue("MATH_BOOKS")
public class MathBook extends Book {
	
	private static final long serialVersionUID = 6632845671512550210L;
	private String theme = null;
	
	private static String themeFieldName = "Sujet";
	
	public static JsonObject getJSONAttributes() {
		JsonObject json = Book.getJSONAttributes();
		json.addProperty(themeFieldName, "text");
		return json;
	}
	
	@Override
	public boolean setParameters(JsonObject json, long userID) {
		if (super.setMandatoryParameters(json, userID)) {
			if (json.has(themeFieldName)) 
				this.setTheme(json.get(themeFieldName).getAsString());
		}
		return false;
	}
	
	public Ad getNewInstance() {
		return new MathBook();
	}
	
	public JsonObject getJsonValues() {
		JsonObject json = super.getJsonValues();
		json.addProperty(themeFieldName, this.theme);
		return json;
	}
	

}
