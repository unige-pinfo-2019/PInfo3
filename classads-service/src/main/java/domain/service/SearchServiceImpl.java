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
	
	
	public JsonArray searchResquet(String request) {
		//Request without arguments to run for all indices
		SearchRequest searchRequest = new SearchRequest(); 
		
//		//Count the number of matches for a query
//		CountRequest countRequest = new CountRequest();
		
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
		
//		//Add the source builder to the count request
//		countRequest.source(sourceBuilder);
//		
//		//Getting the count result
//		CountResponse countResponse = null;
//		long count = -1;
//		try {
//			countResponse = client.count(countRequest, RequestOptions.DEFAULT);
//			count = countResponse.getCount();
//		} catch (IOException e) {
//			e.printStackTrace();
//			log.error(e.getMessage());
//		}
		
		//Getting the results
		SearchResponse searchResponse = null;
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} 
		
		if (searchResponse != null) {
			JsonArray json = new JsonArray();
			
//			//Adding the number of hits found
//			JsonObject jsonCount = new JsonObject();
//			jsonCount.addProperty("nbHits", count);
//			json.add(jsonCount);
//			
			
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
	    log.info("Succefully get the ad with id : "+id+"\nAd information : "+ad.toString());
	    return ad;
	    
	}
	
	public void deleteAdById(String id) {
	    DeleteRequest deleteRequest = new DeleteRequest("posts", id);
	    try {
	    	client.delete(deleteRequest, RequestOptions.DEFAULT);
	    } catch (java.io.IOException e){
	        e.getLocalizedMessage();
	        log.error(e.getMessage());
	    } finally {
	    	log.info("Succefully delete the ad with id : "+id);
	    }
	}
	
	private Ad buildAdFromMap(Map<String, Object> mapData) {
		Ad ad = new Ad();
		Map<String, Integer> mapInt = new HashMap<>();
		Map<String, Boolean> mapBool = new HashMap<>();
		Map<String, String> mapString = new HashMap<>();
	
		
		for (Map.Entry<String, Object> entry : mapData.entrySet()) {
			
			if (entry.getKey().equals("id")) {
				int i = (int)(entry.getValue());
				ad.setId((long)i);
			}
			
			else if (entry.getKey().equals(Ad.getTitleField()))
				ad.setTitle((String)(entry.getValue()));
			
			else if (entry.getKey().equals(Ad.getDescriptionField()))
				ad.setDescription((String)(entry.getValue()));
			
			else if (entry.getKey().equals(Ad.getPriceField())) {
				double d = (double)(entry.getValue());
				ad.setPrice((float)d);
			}
			
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