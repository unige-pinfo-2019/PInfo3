package api.msg;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.aerogear.kafka.SimpleKafkaProducer;
import org.aerogear.kafka.cdi.annotation.KafkaConfig;
import org.aerogear.kafka.cdi.annotation.Producer;

import com.google.gson.JsonObject;

import domain.model.Ad;
import domain.service.AdService;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@KafkaConfig(bootstrapServers = "#{thorntail.kafka-configuration.host}:#{thorntail.kafka-configuration.port}")
@Slf4j
public class AdProducer {
	
	@Producer
	private SimpleKafkaProducer<String, JsonObject> producer;
	
	@Inject
	private AdService adService;
	
	public void sendAllAds() {
		log.info("Send the current state of ALL ads to the topic");
		for (Ad ad : adService.getAll()) {
			JsonObject json = ad.getJsonValues();
			json.addProperty("id", ad.getId());
			producer.send("ads", json);	
		}
	}
	
	public void sendDelete(Ad ad) {
		log.info("Send an ad to delete to the topic with id " + ad.getId());
		JsonObject json = ad.getJsonValues();
		json.addProperty("id", ad.getId());
		producer.send("deleteAds", json);
	}
	
	public void send(Ad ad) {
		log.info("Send an ad to the topic with id " + ad.getId() );
		JsonObject json = ad.getJsonValues();
		json.addProperty("id", ad.getId());
		producer.send("ads", json);			
	}	
	
	public void send(Long id) {
		log.info("Send the state of an ad to the topic with id " + id);
		Optional<Ad> ad = adService.getById(id);
		if (!ad.isEmpty()) {
			send(ad.get());
		}
	}

}
