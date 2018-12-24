/**
 *
 */
package org.fc.data;

import java.util.Comparator;

import org.fc.entity.Result;

/**
 * @author pskopek
 *
 */
public class RoundResultsComparator implements Comparator<Result> {

    public int compare(Result o1, Result o2) {

        if (o1.getRound() < o2.getRound()) {
            return -1;
        } else if (o1.getRound() > o2.getRound()) {
            return 1;
        } else if (o1.getSector().compareTo(o2.getSector()) < 0) {
            return -1;
        } else if (o1.getSector().compareTo(o2.getSector()) > 0) {
            return 1;
        }
        if (o1.getCips() < o2.getCips()) {
            return 1;
        } else if (o1.getCips() > o2.getCips()) {
            return -1;
        }
        if (o1.getMax() < o2.getMax()) {
            return 1;
        } else if (o1.getMax() > o2.getMax()) {
            return -1;
        }

        return 0;
    }


}
