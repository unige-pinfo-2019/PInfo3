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
@Table(name = "COMPUTERS")
@DiscriminatorValue("COMPUTERS")
public class Computer extends Ad {
	
	private static final long serialVersionUID = 5972446884000830076L;
	private float size = -1;
	private String brand = null;
	private int memorySize = -1;
	
	private static String sizeFieldName = "Computer size";
	private static String brandFieldName = "Brand";
	private static String memorySizeFieldName = "Memory size";
	
	public static JsonObject getJSONAttributes() {
		JsonObject json = Ad.getJSONAttributes();
		json.addProperty(sizeFieldName, "number");
		json.addProperty(brandFieldName, "text");
		json.addProperty(memorySizeFieldName, "number");
		return json;
	}
	
	@Override
	public boolean setParameters(JsonObject json, long userID) {
		if (super.setMandatoryParameters(json, userID)) {
			if (json.has(sizeFieldName))
				this.setSize(json.get(sizeFieldName).getAsFloat());
			
			if (json.has(brandFieldName))
				this.setBrand(json.get(brandFieldName).getAsString());
			
			if (json.has(memorySizeFieldName))
				this.setMemorySize(json.get(memorySizeFieldName).getAsInt());
				
		}
		return false;
	}
	
	public Ad getNewInstance() {
		return new Computer();
	}
	
	public JsonObject getJsonValues() {
		JsonObject json = super.getJsonValues();
		json.addProperty(sizeFieldName, this.size);
		json.addProperty(brandFieldName, this.brand);
		json.addProperty(memorySizeFieldName, this.memorySize);
		return json;
	}
	
	
	
	
	
	
	

}
