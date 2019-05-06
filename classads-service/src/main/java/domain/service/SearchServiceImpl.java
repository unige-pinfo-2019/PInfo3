package domain.service;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import domain.model.Ad;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@ApplicationScoped
public class SearchServiceImpl implements SearchService {

	RestHighLevelClient client = new RestHighLevelClient(
	        RestClient.builder(new HttpHost("elasticsearch", 9200, "http")));
	
	public void insertAd(Ad ad){
	    Map<String, Object> dataMap = new HashMap<String, Object>();
	    dataMap.put("id", ad.getId());
	    dataMap.put(Ad.getTitleField(), ad.getTitle());
	    dataMap.put(Ad.getDescriptionField(), ad.getDescription());
	    dataMap.put(Ad.getPriceField(), ad.getPrice());
	
	    
	    for (Map.Entry<String, Integer> entry : ad.getCategoryInt().entrySet()) {
	    	dataMap.put(entry.getKey(), entry.getValue());
	    }
	    for (Map.Entry<String, String> entry : ad.getCategoryString().entrySet()) {
	    	dataMap.put(entry.getKey(), entry.getValue());
	    }
	    for (Map.Entry<String, Boolean> entry : ad.getCategoryBool().entrySet()) {
	    	dataMap.put(entry.getKey(), entry.getValue());
	    }
	   
	    IndexRequest indexRequest = new IndexRequest("posts").id(Long.toString(ad.getId())).source(dataMap);
	    try {
	    	client.index(indexRequest, RequestOptions.DEFAULT);
	    } catch(ElasticsearchException e) {
	        log.error(e.getDetailedMessage());
	    } catch (java.io.IOException e){
	        e.getLocalizedMessage();
	        log.error(e.getMessage());
	    } finally {
	    	log.info("The ad has been added to elastic search data");
	    }
	    
	}

	public Ad getAdById(String id){
	    GetRequest getRequest = new GetRequest("posts", id);
	    GetResponse getResponse = null;
	    Ad ad = null;
	    try {
	        getResponse = client.get(getRequest, RequestOptions.DEFAULT);
	    } catch (java.io.IOException e){
	        e.getLocalizedMessage();
	        log.error(e.getMessage());
	    } 
	    
	    if (getResponse.isExists()) {
	    	Map<String, Object> mapData = getResponse.getSourceAsMap();
	    	ad = buildAdFromMap(mapData);
	    }
	    return ad;
	    
	}
	
	private Ad buildAdFromMap(Map<String, Object> mapData) {
		Ad ad = new Ad();
		Map<String, Integer> mapInt = new HashMap<>();
		Map<String, Boolean> mapBool = new HashMap<>();
		Map<String, String> mapString = new HashMap<>();
	
		
		for (Map.Entry<String, Object> entry : mapData.entrySet()) {
			
			if (entry.getKey().equals("id"))
				ad.setId((long) entry.getValue());
			
			else if (entry.getKey().equals(Ad.getTitleField()))
				ad.setTitle((String) entry.getValue());
			
			else if (entry.getKey().equals(Ad.getDescriptionField()))
				ad.setDescription((String) entry.getValue());
			
			else if (entry.getKey().equals(Ad.getPriceField()))
				ad.setPrice((float) entry.getValue());
			
			else {
				if(entry.getValue().getClass()== Integer.class) mapInt.put(entry.getKey(), (Integer) entry.getValue());
				if(entry.getValue().getClass()== Boolean.class) mapBool.put(entry.getKey(), (Boolean) entry.getValue());
				if(entry.getValue().getClass()== String.class) mapString.put(entry.getKey(), (String) entry.getValue());	
			}
		}
		
		ad.setCategory(mapInt, mapBool, mapString);
		return ad;
	}
}
