package ch.unige.pinfo.classads.service;

import java.util.List;
import java.util.Optional;

import ch.unige.pinfo.classads.model.ClassAd;

public interface ClassAdService {
	
	public boolean createClassAd(ClassAd c);
	
	public List<ClassAd> getAll();
	
	public Optional<ClassAd> getByTitle(String title);
	
	public Optional<ClassAd> getById(long id);
	
	public void deleteClassAd(ClassAd classad);

}
