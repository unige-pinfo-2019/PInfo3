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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import domain.model.Ad;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class SearchServiceImpl implements SearchService {

	private String index = "posts";

	private RestHighLevelClient client = new RestHighLevelClient(
	        RestClient.builder(new HttpHost("elasticsearch", 9200, "http")));

	public void setClient(RestHighLevelClient client) {
		this.client = client;
	}


	/***** "Classical" methods *****/
	public void insertAd(Ad ad) {
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
		UpdateRequest updateRequest = new UpdateRequest(index, Long.toString(ad.getId())).doc(dataMap);
		try {
			client.update(updateRequest, RequestOptions.DEFAULT);
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
	    try {
	        getResponse = client.get(getRequest, RequestOptions.DEFAULT);
	    } catch (java.io.IOException e){
	        log.error(e.getMessage());
	    }
	    Ad ad = null;
		if (getResponse != null) {
	    	Map<String, Object> mapData = getResponse.getSourceAsMap();
	    	if (mapData != null) {
	    		ad = buildAdFromMap(mapData);
	    		log.info("Succefully get the ad\nAd information : "+ad);
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

	/***** Search methods *****/
	public JsonArray searchResquet(String request) {
		//Request without arguments to run for all indices
		SearchRequest searchRequest = new SearchRequest();

		//Specify informations about the request
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); //With default option
		sourceBuilder.from(0); //Set the starting index to search
		sourceBuilder.size(5); //Set the number of reponses
		sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS)); //Set the maximum time for the search
		sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC)); //Sort by the highest matching

		QueryBuilder queryBuilder = QueryBuilders
				.matchQuery("description", request)
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
			json = transformJson(json);
			log.info("Search done, the results are : "+json.toString());
			return json;
		}

		log.error("Error in the search process");
		return null;
	}

	/****** Manipulation *****/
	private JsonArray transformJson(JsonArray json) {
		JsonArray array = new JsonArray();
		JsonParser parser = new JsonParser();
		for (JsonElement elt : json) {
			JsonObject jsonObj = parser.parse(elt.getAsString()).getAsJsonObject();
			array.add(jsonObj);			
		}
		return array;
	}
	Map<String, Object> buildMapFromAd(Ad ad) {
		Map<String, Object> dataMap = new HashMap<>();
	    dataMap.put(Ad.getIdField(), ad.getId());
	    dataMap.put(Ad.getTitleField(), ad.getTitle());
	    dataMap.put(Ad.getDescriptionField(), ad.getDescription());
	    dataMap.put(Ad.getPriceField(), ad.getPrice());
	    dataMap.put(Ad.getCategoryIDField(), ad.getCategoryID());
	    dataMap.put(Ad.getUserIDField(), ad.getUserID());
	    return dataMap;
	}

	Ad buildAdFromMap(Map<String, Object> mapData) {
		Ad ad = new Ad();

		if (mapData != null) {
			if (mapData.containsKey(Ad.getTitleField())) {
				ad.setTitle((String)(mapData.get(Ad.getTitleField())));
			}
			if (mapData.containsKey(Ad.getDescriptionField())) {
				ad.setDescription((String)(mapData.get(Ad.getDescriptionField())));
			}
			if (mapData.containsKey(Ad.getPriceField())) {
				ad.setPrice(Float.parseFloat(mapData.get(Ad.getPriceField()).toString()));
			}
			if (mapData.containsKey(Ad.getIdField())) {
				ad.setId(Long.parseLong(mapData.get(Ad.getIdField()).toString()));
			}
			if (mapData.containsKey(Ad.getUserIDField())) {
				ad.setUserID(Long.parseLong(mapData.get(Ad.getUserIDField()).toString()));
			}
			if (mapData.containsKey(Ad.getCategoryIDField())) {
				ad.setCategoryID(Integer.parseInt(mapData.get(Ad.getCategoryIDField()).toString()));
			}
		}
		return ad;
	}


}
