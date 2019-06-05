package api.rest;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import api.msg.AdProducer;
import domain.model.Ad;
import domain.service.AdService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AdEndPointTest {
	
	@InjectMocks
	private AdEndpoint aep;
	
	@Mock
	private AdService adservice;

	@Mock
	private AdProducer adProducer;
	
	private Ad getAdDefault() {
		ArrayList<String> images = new ArrayList<String>();
		images.add("Image1");
		images.add("Image2");
		images.add("Image3");
		return new Ad("Charger iPhone", "Works with Android (not a joke)", (float) 120, 100, 1, images);
	}
	
	@Test
	public void deleteAdTest() {
		Ad ad = getAdDefault();
		when(adservice.getById(ad.getId())).thenReturn(Optional.of(ad));
		doNothing().when(adservice).deleteAd(ad);
		doNothing().when(adProducer).sendDelete(ad);
		
		Response res = aep.deleteAd(Long.toString(ad.getId()));
		Assertions.assertEquals(200, res.getStatus());
	}
	
	@Test
	public void deleteNotExistingAdTest() {
		Ad ad = getAdDefault();
		when(adservice.getById(ad.getId())).thenReturn(Optional.empty());
		doNothing().when(adservice).deleteAd(ad);
		doNothing().when(adProducer).sendDelete(ad);
		
		Response res = aep.deleteAd(Long.toString(ad.getId()));
		Assertions.assertEquals(404, res.getStatus());
	}
	
	@Test
	public void getNotExistingAdTest() {
		Ad ad = getAdDefault();
		when(adservice.getById(ad.getId())).thenReturn(Optional.empty());
		
		Response res = aep.getAd(Long.toString(ad.getId()));
		Assertions.assertEquals(400, res.getStatus());
	}
	
	@Test
	public void getAdTest() {
		Ad ad = getAdDefault();
		when(adservice.getById(ad.getId())).thenReturn(Optional.of(ad));
		
		Response res = aep.getAd(Long.toString(ad.getId()));
		Assertions.assertEquals(200, res.getStatus());
		Assertions.assertEquals(ad, res.getEntity());
	}
	
	@Test
	public void postAdTest() {
		Ad ad = getAdDefault();
		Response res = aep.addNewAd(ad);
		Assertions.assertEquals(200, res.getStatus());
		Assertions.assertEquals(ad.getId(), res.getEntity());
	}
	
	@Test
	public void postBadAdTest() {	
		Response res = aep.addNewAd(null);
		Assertions.assertEquals(400, res.getStatus());
	}

}
