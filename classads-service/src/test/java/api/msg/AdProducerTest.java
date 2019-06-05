package api.msg;

import java.util.ArrayList;

import org.aerogear.kafka.SimpleKafkaProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import domain.model.Ad;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AdProducerTest {
	
	@Mock
	private SimpleKafkaProducer<String, Ad> kafkaProducer;
	
	@InjectMocks
	private AdProducer producer;
	
	private Ad getAdDefault() {
		ArrayList<String> images = new ArrayList<String>();
		images.add("Image1");
		images.add("Image2");
		images.add("Image3");
		Ad ad = new Ad("Charger iPhone", "Works with Android (not a joke)", (float) 120, "100", 1, images);
		ad.setId((long)1);
		return ad;
	}
	
	@Test
	void testSendAd() {
		Ad ad = getAdDefault();
		producer.send(ad);
		verify(kafkaProducer, times(1)).send("ads", ad);
	}
	
	@Test
	void testDeleteAd() {
		Ad ad = getAdDefault();
		producer.sendDelete(ad);
		verify(kafkaProducer, times(1)).send("deleteAds", ad);
	}


}
