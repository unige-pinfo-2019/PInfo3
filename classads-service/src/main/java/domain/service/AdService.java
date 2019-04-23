package domain.service;

import java.util.List;
import java.util.Optional;

import domain.model.Ad;

public interface AdService {
	
	public boolean createAd(Ad ad);
	
	public List<Ad> getAll();
	
	public Optional<Ad> getByTitle(String title);
	
	public Optional<Ad> getById(long id);
	
	public void deleteAd(Ad ad);

}
