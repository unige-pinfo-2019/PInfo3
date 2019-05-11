package domain.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Assertions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import eu.drus.jpa.unit.api.JpaUnit;

@ExtendWith(JpaUnit.class)
@ExtendWith(MockitoExtension.class)
public class CategoriesServiceImplTest {
	
	@Spy
	@PersistenceContext(unitName = "AdUTest")
	EntityManager em;
	
	@InjectMocks
	private CategoriesServiceImpl csi;
	
	private void testMandatoryAttributes(int id) {
		//Every categories should have a title, a description and a price :
//		JsonObject json = csi.getAttributes(id);
//		try {
//			String title = json.get("title").getAsString();
//			String description = json.get("description").getAsString();
//			float price = json.get("price").getAsFloat();
//			
//			Assertions.assertEquals(0, price);
//			Assertions.assertEquals("", title);
//			Assertions.assertEquals("", description);
//		} catch (Exception e) {
//			Assertions.fail("Impossible to extract title, description or price");
//		}
	}
	
	@Test
	public void getAttributesTest() {
		//For each category, we check if it has the mandatory attributes
//		int nbCategories = Categories1.getCategories().size();
//		for (int i=0; i<nbCategories; i++) {
//			testMandatoryAttributes(i);
//		}
//		
//		//We can also test that the function returns null if the categoryID is wrong
//		JsonObject json = csi.getAttributes(-1);
//		Assertions.assertEquals(null, json);
	}
	
	@Test
	public void getCategoriesTreeViewTest() {
		//getCategoriesTreeView should return a array of json objects with a field name and a field children
//		JsonArray json = csi.getCategoriesTreeView();
//		for (JsonElement elt : json) {
//			JsonObject cat = elt.getAsJsonObject();
//			try {
//				cat.get(csi.getNameField());
//				cat.get(csi.getChildrenField());
//			} catch (Exception e) {
//				Assertions.fail("Tree view hasn't the right shape (impossible to extract name and children)");
//			}
//		}
	}
	
	

}
