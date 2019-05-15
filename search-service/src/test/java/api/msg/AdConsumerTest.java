package api.msg;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import domain.model.Ad;
import domain.service.SearchService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AdConsumerTest {
	
	@InjectMocks
	private AdConsumer consumer;
	
	@Mock
	private SearchService searchService;
	
	private Ad getAdDefault() {
		ArrayList<String> images = new ArrayList<String>();
		images.add("Image1");
		images.add("Image2");
		images.add("Image3");
		return new Ad((long)1, "Charger iPhone", "Works with Android (not a joke)", (float) 120, 100, 1, images);
	}
	
	@Test
	void testUpdateNewAd() {
		Ad ad = getAdDefault();
		doNothing().when(searchService).insertAd(ad);
		when(searchService.getAdById(Long.toString(ad.getId()))).thenReturn(null);
		consumer.updateAd(ad);
		
		verify(searchService, times(1)).getAdById(eq(Long.toString(ad.getId())));
		verify(searchService, times(1)).insertAd(eq(ad));
	}
	
	@Test
	void testUpdateExistingAd() {
		Ad ad = getAdDefault();
		doNothing().when(searchService).updateAd(ad);
		when(searchService.getAdById(Long.toString(ad.getId()))).thenReturn(ad);
		consumer.updateAd(ad);
		
		verify(searchService, times(1)).getAdById(eq(Long.toString(ad.getId())));
		verify(searchService, times(1)).updateAd(eq(ad));
	}
	
	@Test
	void testExistingDeleteAd() {
		Ad ad = getAdDefault();
		doNothing().when(searchService).deleteAdById(Long.toString(ad.getId()));
		when(searchService.getAdById(Long.toString(ad.getId()))).thenReturn(null);
		consumer.deleteAd(ad);
		
		verify(searchService, times(1)).getAdById(eq(Long.toString(ad.getId())));
		verify(searchService, times(0)).deleteAdById(eq(Long.toString(ad.getId())));
	}
	
	@Test
	void testNotExistingDeleteAd() {
		Ad ad = getAdDefault();
		doNothing().when(searchService).deleteAdById(Long.toString(ad.getId()));
		when(searchService.getAdById(Long.toString(ad.getId()))).thenReturn(ad);
		consumer.deleteAd(ad);
		
		verify(searchService, times(1)).getAdById(eq(Long.toString(ad.getId())));
		verify(searchService, times(1)).deleteAdById(eq(Long.toString(ad.getId())));
	}

}
