package domain.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import com.google.gson.JsonArray;

import domain.model.Ad;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@ApplicationScoped
public class SearchServiceImpl implements SearchService {

	RestHighLevelClient client = new RestHighLevelClient(
	        RestClient.builder(new HttpHost("elasticsearch", 9200, "http")));
	
	private String index = "posts";
	
	
	public JsonArray searchResquet(String request) {
		//Request without arguments to run for all indices
		SearchRequest searchRequest = new SearchRequest(); 
		
		//Specify informations about the request
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); //With default option
		sourceBuilder.from(0); //Set the starting index to search 
		sourceBuilder.size(5); //Set the number of reponses
		sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS)); //Set the maximum time for the search
		sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC)); //Sort by the highest matching
		
		QueryBuilder queryBuilder = QueryBuilders.matchQuery("description", request)
                .fuzziness(Fuzziness.AUTO) //Enable fuzzy matching (search even if there's not a full match)
                .prefixLength(3)
                .maxExpansions(10);
		sourceBuilder.query(queryBuilder);
		
		//Add the source builder to the search request
		searchRequest.source(sourceBuilder);
		
		//Getting the results
		SearchResponse searchResponse = null;
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.error(e.getMessage());
		} 
		
		if (searchResponse != null) {
			JsonArray json = new JsonArray();		
			
			SearchHits hits = searchResponse.getHits();
			SearchHit[] searchHits = hits.getHits();
			for (SearchHit hit : searchHits) {
				String sourceAsString = hit.getSourceAsString();
				json.add(sourceAsString);
			}
			log.info("Search done, the results are : "+json.toString());
			return json;
		}
		
		log.error("Error in the search process");
		return null;
	}
	
	public void insertAd(Ad ad){
	    
		Map<String, Object> dataMap = buildMapFromAd(ad);
	    IndexRequest indexRequest = new IndexRequest(index).id(Long.toString(ad.getId())).source(dataMap);
	    try {
	    	client.index(indexRequest, RequestOptions.DEFAULT);
	    } catch(ElasticsearchException e) {
	        log.error(e.getDetailedMessage());
	    } catch (java.io.IOException e){
	        log.error(e.getMessage());
	    } finally {
	    	log.info("The ad has been added to elastic search data");
	    }
	    
	}
	
	public void updateAd(Ad ad) {
		Map<String, Object> dataMap = buildMapFromAd(ad);
		UpdateRequest request = new UpdateRequest(index, Long.toString(ad.getId())).doc(dataMap);
		try {
			client.update(request, RequestOptions.DEFAULT);
		} catch (ElasticsearchException e) {
			log.error(e.getDetailedMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			log.info("Succefully updated the ad with id : "+ad.getId());
		}
	}

	public Ad getAdById(String id){
	    GetRequest getRequest = new GetRequest(index, id);
	    GetResponse getResponse = null;
	    Ad ad = null;
	    try {
	        getResponse = client.get(getRequest, RequestOptions.DEFAULT);
	    } catch (java.io.IOException e){
	        log.error(e.getMessage());
	    } finally {
	    	if (getResponse != null) {
		    	Map<String, Object> mapData = getResponse.getSourceAsMap();
		    	ad = buildAdFromMap(mapData);
		    	if (ad != null)
		    		log.info("Succefully get the ad with id : "+id+"\nAd information : "+ad);
		    }
	    }
	    return ad;
	    
	}
	
	public void deleteAdById(String id) {
	    DeleteRequest deleteRequest = new DeleteRequest(index, id);
	    try {
	    	client.delete(deleteRequest, RequestOptions.DEFAULT);
	    } catch (java.io.IOException e){
	        log.error(e.getMessage());
	    } finally {
	    	log.info("Succefully delete the ad with id : "+id);
	    }
	}
	
	private Map<String, Object> buildMapFromAd(Ad ad) {
		Map<String, Object> dataMap = new HashMap<>();
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
	    return dataMap;
	}
	
	private Ad buildAdFromMap(Map<String, Object> mapData) {
		Ad ad = new Ad();
		Map<String, Integer> mapInt = new HashMap<>();
		Map<String, Boolean> mapBool = new HashMap<>();
		Map<String, String> mapString = new HashMap<>();
		
		if (mapData.containsKey(Ad.getTitleField())) {
			ad.setTitle((String)(mapData.get(Ad.getTitleField())));
			mapData.remove(Ad.getTitleField());
		} 
		if (mapData.containsKey(Ad.getDescriptionField())) {
			ad.setDescription((String)(mapData.get(Ad.getDescriptionField())));
			mapData.remove(Ad.getDescriptionField());
		} 
		if (mapData.containsKey(Ad.getPriceField())) {
			ad.setPrice((float)(mapData.get(Ad.getPriceField())));
			mapData.remove(Ad.getPriceField());
		} 
		if (mapData.containsKey("id")) {
			ad.setId((int)(mapData.get("id")));
			mapData.remove("id");
		} 
	
		for (Map.Entry<String, Object> entry : mapData.entrySet()) {
			
			if(entry.getValue().getClass()== Integer.class) mapInt.put(entry.getKey(), (Integer) entry.getValue());
			if(entry.getValue().getClass()== Boolean.class) mapBool.put(entry.getKey(), (Boolean) entry.getValue());
			if(entry.getValue().getClass()== String.class) mapString.put(entry.getKey(), (String) entry.getValue());
			
		}
		
		ad.setCategory(mapInt, mapBool, mapString);
		return ad;
	}
}
