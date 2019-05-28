package domain.model;

import java.util.Comparator;

public class SortAdResponsesByDate implements Comparator<AdResponse> {

	@Override
	public int compare(AdResponse arg0, AdResponse arg1) {
		return arg0.getTime().compareTo(arg1.getTime());
	}

}
