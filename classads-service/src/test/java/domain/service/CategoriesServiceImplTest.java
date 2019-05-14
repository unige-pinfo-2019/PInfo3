package domain.service;

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
	
	

}
