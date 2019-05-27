package api.msg;

import javax.enterprise.context.ApplicationScoped;

import org.aerogear.kafka.SimpleKafkaProducer;
import org.aerogear.kafka.cdi.annotation.KafkaConfig;
import org.aerogear.kafka.cdi.annotation.Producer;

import domain.model.Ad;
import domain.model.AdSearchable;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@KafkaConfig(bootstrapServers = "#{thorntail.kafka-configuration.host}:#{thorntail.kafka-configuration.port}")
@Slf4j
public class AdProducer {
	
	@Producer
	private SimpleKafkaProducer<String, AdSearchable> producer;
	
	public void sendDelete(Ad ad) {
		log.info("Send an ad to delete to the topic with id " + ad.getId());
		AdSearchable adSearchable = new AdSearchable();
		adSearchable.createAd(ad);
		producer.send("deleteAds", adSearchable);
	}
	
	public void send(Ad ad) {
		log.info("Send an ad to the topic with id " + ad.getId());
		AdSearchable adSearchable = new AdSearchable();
		adSearchable.createAd(ad);
		producer.send("ads", adSearchable);			
	}	

}
