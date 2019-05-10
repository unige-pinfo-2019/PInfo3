package api.msg;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.aerogear.kafka.SimpleKafkaProducer;
import org.aerogear.kafka.cdi.annotation.KafkaConfig;
import org.aerogear.kafka.cdi.annotation.Producer;

import domain.model.Ad;
import domain.service.AdService;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@KafkaConfig(bootstrapServers = "#{thorntail.kafka-configuration.host}:#{thorntail.kafka-configuration.port}")
@Slf4j
public class AdProducer {
	
	@Producer
	private SimpleKafkaProducer<String, Ad> producer;
	
	@Inject
	private AdService adService;
	
	public void sendAllAds() {
		log.info("Send the current state of ALL ads to the topic");
		for (Ad instrument : adService.getAll()) {
			producer.send("ads", instrument);	
		}
	}
	
	public void sendDelete(Ad ad) {
		log.info("Send an ad to delete to the topic with id " + ad.getId());
		producer.send("deleteAds", ad);
	}
	
	public void send(Ad ad) {
		log.info("Send an ad to the topic with id " + ad.getId() );
		producer.send("ads", ad);			
	}	
	
	public void send(Long id) {
		log.info("Send the state of an ad to the topic with id " + id);
		Optional<Ad> ad = adService.getById(id);
		if (!ad.isEmpty()) {
			send(ad.get());
		}
	}

}
