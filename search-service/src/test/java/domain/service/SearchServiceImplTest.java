package domain.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.smitajit.elasticsearch.rest.mock.ESRestMockCore;
import com.github.smitajit.elasticsearch.rest.mock.runner.ESRestMockRunner;

import domain.model.Ad;

@ExtendWith(MockitoExtension.class)
@RunWith(ESRestMockRunner.class)
public class SearchServiceImplTest {
	
	@InjectMocks
	private SearchServiceImpl searchService;  
	
	private RestHighLevelClient client;
	
	@Before
    public void setup() {
        client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
        searchService.setClient(client);
    }
	
	private Ad getDefaultAd() {
		Ad ad = new Ad();
		ad.setTitle("Any title");
		ad.setDescription("Any description");
		ad.setPrice((float)(10));
		ad.setId((long)(100));
		
		Map<String, Integer> mapInt = new HashMap<>();
		Map<String, String> mapString = new HashMap<>();
		Map<String, Boolean> mapBool = new HashMap<>();
		
		ad.setCategory(mapInt, mapBool, mapString);
		
		return ad;
	}
	
	@Test 
	public void buildMapTest() {
		Ad ad = getDefaultAd();
		Map<String, Object> dataMap = searchService.buildMapFromAd(ad);
		
		//Testing mandatory fields
		if (!dataMap.containsKey("id")) {
			Assertions.fail("Field 'id' is missing from the map");
		} else {
			Assertions.assertEquals(ad.getId(), (long)(dataMap.get("id")));
		}
		if (!dataMap.containsKey(Ad.getTitleField())) {
			Assertions.fail("Field 'title' is missing from the map");
		} else {
			Assertions.assertEquals(ad.getTitle(), (String)(dataMap.get(Ad.getTitleField())));
		}
		if (!dataMap.containsKey(Ad.getDescriptionField())) {
			Assertions.fail("Field 'description' is missing from the map");
		} else {
			Assertions.assertEquals(ad.getDescription(), (String)(dataMap.get(Ad.getDescriptionField())));
		}
		if (!dataMap.containsKey(Ad.getPriceField())) {
			Assertions.fail("Field 'price' is missing from the map");
		} else {
			Assertions.assertEquals(ad.getPrice(), (float)(dataMap.get(Ad.getPriceField())));
		}
	}
	
	@Test 
	public void buildAdTest() {
		Ad ad = getDefaultAd();
		Map<String, Object> dataMap = searchService.buildMapFromAd(ad);
		Ad newAd = searchService.buildAdFromMap(dataMap);
		
		//Testing mandatory fields
		Assertions.assertEquals(ad.getTitle(), newAd.getTitle());
		Assertions.assertEquals(ad.getDescription(), newAd.getDescription());
		Assertions.assertEquals(ad.getPrice(), newAd.getPrice());
		Assertions.assertEquals(ad.getId(), newAd.getId());
		
	}
	
	@Test
	public void insertAdTest() {
		
		Ad ad = getDefaultAd(); 
		 
		ESRestMockCore.newBuilder()
				.forMethod("POST")
				.forEndPoint("/posts/"+Long.toString(ad.getId()))
				.expectResponse(200, "", ContentType.APPLICATION_JSON)
				.build();
		
		try {
			searchService.insertAd(ad);
		} catch (Exception e) {
			Assertions.fail("Exception thrown in inserting an ad");
		}
        
	}
	
	@Test
	public void updateAdTest() {
		
		Ad ad = getDefaultAd(); 		 
		ESRestMockCore.newBuilder()
				.forMethod("PUT")
				.forEndPoint("/posts/"+Long.toString(ad.getId()))
				.build();
		
		try {
			searchService.updateAd(ad);
		} catch (Exception e) {
			Assertions.fail("Exception thrown : "+e.getMessage());
		}
        
	}
	
	@Test
	public void getAdTest() {
		
		Ad ad = getDefaultAd(); 		 
		ESRestMockCore.newBuilder()
				.forMethod("GET")
				.forEndPoint("/posts/"+Long.toString(ad.getId()))
				.build();
		
		try {
			searchService.getAdById(Long.toString(ad.getId()));
		} catch (Exception e) {
			Assertions.fail("Exception thrown : "+e.getMessage());
		}
        
	}
	
	@Test
	public void deleteAdTest() {
		
		Ad ad = getDefaultAd(); 		 
		ESRestMockCore.newBuilder()
				.forMethod("DELETE")
				.forEndPoint("/posts/"+Long.toString(ad.getId()))
				.build();
		
		try {
			searchService.deleteAdById(Long.toString(ad.getId()));
		} catch (Exception e) {
			Assertions.fail("Exception thrown : "+e.getMessage());
		}
        
	}
	
	@Test
	public void searchTest() {
		
		String request = "Any title"; 		 
		ESRestMockCore.newBuilder()
				.forMethod("GET")
				.forEndPoint("_search/")
				.build();
		
		try {
			searchService.searchResquet(request);
		} catch (Exception e) {
			Assertions.fail("Exception thrown : "+e.getMessage());
		}
        
	}

}
