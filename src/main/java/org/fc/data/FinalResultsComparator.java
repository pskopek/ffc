/**
 * 
 */
package org.fc.data;

import java.util.Comparator;

import org.fc.entity.FinalResult;


/**
 * @author pskopek
 *
 */
public class FinalResultsComparator implements Comparator<FinalResult> {
	
	public int compare(FinalResult o1, FinalResult o2) {

		if (o1.getOrderPoints() < o2.getOrderPoints()) { 
			return -1;
		}	
		else if (o1.getOrderPoints() > o2.getOrderPoints()) { 
			return 1;
		}
		if (o1.getCips() < o2.getCips()) { 
			return 1;
		}	
		else if (o1.getCips() > o2.getCips()) { 
			return -1;
		}
		if (o1.getMax() < o2.getMax()) { 
			return 1;
		}	
		else if (o1.getMax() > o2.getMax()) { 
			return -1;
		}
		
		return 0;
	}
	

	
}
