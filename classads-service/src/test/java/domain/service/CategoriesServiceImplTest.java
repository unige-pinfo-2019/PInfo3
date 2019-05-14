package domain.service;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import domain.model.Categories;
import eu.drus.jpa.unit.api.JpaUnit;

@ExtendWith(JpaUnit.class)
@ExtendWith(MockitoExtension.class)
public class CategoriesServiceImplTest {
	
	@Spy
	@PersistenceContext(unitName = "AdUTest")
	EntityManager em;
	
	@InjectMocks
	private CategoriesServiceImpl csi;
	
	@Test
	public void getCategoriesTreeViewTest() {
		//getCategoriesTreeView should return a array of json objects with a field name and a field children
		JsonArray json = csi.getCategoriesTreeView();
		for (JsonElement elt : json) {
			JsonObject cat = elt.getAsJsonObject();
			try {
				cat.get(csi.getNameField());
				cat.get(csi.getChildrenField());
			} catch (Exception e) {
				Assertions.fail("Tree view hasn't the right shape (impossible to extract name and children)");
			}
		}
	}
	
	@Test
	public void getNameIndexCategoriesTest() {
		JsonObject json = csi.getNameIndexCategories();
		
		//Test if there is not twice the same indice
		Set<Entry<String, JsonElement>> entrySet = json.entrySet();
		for(Map.Entry<String,JsonElement> entry : entrySet){
			for(Map.Entry<String,JsonElement> entry2 : entrySet){
				if (entry != entry2)
					Assertions.assertNotEquals(entry.getValue().getAsInt(), entry2.getValue().getAsInt());
			}
		}
		
		//Test if all category ID are present
		Set<Integer> indices = Categories.getCategoriesIndex();
		for(Map.Entry<String,JsonElement> entry : entrySet){
			Assertions.assertEquals(true, indices.contains(entry.getValue().getAsInt()));;
		}
	}
	
	

}
