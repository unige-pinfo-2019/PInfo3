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
@Table(name = "BOOKS")
@DiscriminatorValue("BOOKS")
public class Book extends Ad {
	
	private static final long serialVersionUID = 6632845671512550210L;
	private String authors = null;
	private String publisher = null;
	
	private static String authorsFieldName = "Authors";
	private static String publisherFieldName = "Publishers";
	
	public static JsonObject getJSONAttributes() {
		JsonObject json = Ad.getJSONAttributes();
		json.addProperty(authorsFieldName, "text");
		json.addProperty(publisherFieldName, "text");
		return json;
	}

	@Override
	public boolean setParameters(JsonObject json, long userID) {
		if (super.setMandatoryParameters(json, userID)) {
			if (json.has(authorsFieldName))
				this.setAuthors(json.get(authorsFieldName).getAsString());
			
			if (json.has(publisherFieldName))
				this.setPublisher(json.get(publisherFieldName).getAsString());
				
		}
		return false;
	}
	
	public Ad getNewInstance() {
		return new Book();
	}
	
	public JsonObject getJsonValues() {
		JsonObject json = super.getJsonValues();
		json.addProperty(authorsFieldName, this.authors);
		json.addProperty(publisherFieldName, this.publisher);
		return json;
	}
	

}
