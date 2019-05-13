package api.msg;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.aerogear.kafka.SimpleKafkaProducer;
import org.aerogear.kafka.cdi.annotation.KafkaConfig;
import org.aerogear.kafka.cdi.annotation.Producer;

import domain.model.Ad;
import domain.model.AdSearchable;
import domain.service.AdSearchableService;
import domain.service.AdService;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@KafkaConfig(bootstrapServers = "#{thorntail.kafka-configuration.host}:#{thorntail.kafka-configuration.port}")
@Slf4j
public class AdProducer {
	
	@Producer
	private SimpleKafkaProducer<String, AdSearchable> producer;
	
	@Inject
	private AdService adService;
	
	@Inject
	private AdSearchableService adSearchableService;
	
	public void sendAllAds() {
		log.info("Send the current state of ALL ads to the topic");
		for (Ad ad : adService.getAll()) {
			producer.send("ads", adSearchableService.convertAdToAdSearchable(ad));	
		}
	}
	
	public void sendDelete(Ad ad) {
		log.info("Send an ad to delete to the topic with id " + ad.getId());
		producer.send("deleteAds", adSearchableService.convertAdToAdSearchable(ad));
	}
	
	public void send(Ad ad) {
		log.info("Send an ad to the topic with id " + ad.getId() );
		AdSearchable newAd = adSearchableService.convertAdToAdSearchable(ad);
		log.info(newAd.getTitle() + "\n" + newAd.getDescription() + "\n" + newAd.getPrice() + "\n" + newAd.getCategoryAttributes());
		producer.send("ads", adSearchableService.convertAdToAdSearchable(ad));			
	}	
	
	public void send(Long id) {
		log.info("Send the state of an ad to the topic with id " + id);
		Optional<Ad> ad = adService.getById(id);
		if (!ad.isEmpty()) {
			send(ad.get());
		}
	}

}
