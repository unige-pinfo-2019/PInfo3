package api.msg;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.aerogear.kafka.cdi.annotation.Consumer;
import org.aerogear.kafka.cdi.annotation.KafkaConfig;

import domain.model.AdSearchable;
import domain.service.SearchService;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@KafkaConfig(bootstrapServers = "#{thorntail.kafka-configuration.host}:#{thorntail.kafka-configuration.port}")
@Slf4j
public class AdConsumer {
	
	@Inject
	private SearchService searchService;
	
	@Consumer(topics = "ads", groupId = "ch.unige")
	public void updateAd(AdSearchable ad) {
		log.info("Consumer got following message : " + ad);
		if (searchService.getAdById(Long.toString(ad.getId())) != null) {
			searchService.insertAd(ad);	
		} else {
			searchService.updateAd(ad);
		}
	}
	
	@Consumer(topics = "deleteAds", groupId = "ch.unige")
	public void deleteAd(AdSearchable ad) {
		log.info("Consumer got following message : " + ad);
		String strID = Long.toString(ad.getId());
		if (searchService.getAdById(strID) != null) {
			searchService.deleteAdById(strID);
		}
	}

}
