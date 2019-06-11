package domain.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import domain.model.AdResponse;
import eu.drus.jpa.unit.api.JpaUnit;


@ExtendWith(JpaUnit.class)
@ExtendWith(MockitoExtension.class)
class AdResponseServiceImplTest {
	
	@Spy
	@PersistenceContext(unitName = "AdResponseUTest")
	EntityManager em;
	
	@InjectMocks
	private AdResponseServiceImpl ars;
	
	
	@Test
	void testGetResponsesFromiToj() {
		int offset = 3;
		int limit = 5;
		int maxi = 10;
		
		List<AdResponse> respList = new ArrayList<AdResponse>();
		for (int i = 0; i<maxi; ++i) {
			AdResponse resp0 = new AdResponse(100, 0 , "Response"+i+"User0", true);
			respList.add(resp0);
			ars.createAdResponse(resp0);
			
			AdResponse resp1 = new AdResponse(100, 0, "Response"+i+"User1", false);
			respList.add(resp1);
			ars.createAdResponse(resp1);
		}
		List<AdResponse> expected = new ArrayList<AdResponse>();
		for (int i=0; i<limit; ++i) {
			expected.add(respList.get(respList.size()-1-offset-i));
		}
		
		List<AdResponse> actual = ars.getResponsesFromiToj(0, 100, offset, limit);
		Assertions.assertEquals(expected, actual);
		
	}
	
	@Test
	void testGetByUser() {
		int maxMsgs = 3;
		int maxUsrs = 5;
		boolean flagParam = true;
		int usrId = maxUsrs+1;
		
		List<AdResponse> respList = new ArrayList<AdResponse>();
		
		for (int i = 0; i < maxMsgs; ++i) {
			for (int j = 0 ; j < maxUsrs; ++j) {
				AdResponse resp1 = new AdResponse(100, j+1 , "Response"+i+"G1", flagParam);
				ars.createAdResponse(resp1);
				
				AdResponse resp2 = new AdResponse(150, maxUsrs+j+1 , "Response"+i+"G2", flagParam);
				ars.createAdResponse(resp2);
				
				
				AdResponse resp3 = new AdResponse(100, maxUsrs+j+1 , "Response"+i+"G3", flagParam);
				ars.createAdResponse(resp3);
				
				if (i == maxMsgs-1 && j == 0) {
					respList.add(resp3);
					respList.add(resp2);
				}	
			}
		}
		
		
		
		List<AdResponse> actual = ars.getByUser(usrId);
		Assertions.assertEquals(respList, actual);
		
	}
	
	

}
