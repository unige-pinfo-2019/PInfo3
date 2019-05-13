package domain.service;

import domain.model.Ad;
import domain.model.AdSearchable;

public interface AdSearchableService {
	
	public AdSearchable convertAdToAdSearchable(Ad ad);

}
