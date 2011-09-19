package org.fc.data;

import java.util.Comparator;
import org.fc.entity.report.Boats;

public class BoatsComparator implements Comparator<Boats> {
	
	public int compare(Boats b1, Boats b2) {
		if (b1.round < b2.round) 
			return -1;
		if (b1.round > b2.round) 
			return 1;
		
		int c;
		if ((c = b1.sector.compareTo(b2.sector)) != 0) 
			return c;
		
		if (b1.location < b2.location) 
			return -1;
		if (b1.location > b2.location) 
			return 1;
		
		return 0;
				
	}

}
