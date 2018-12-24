/**
 *
 */
package org.fc.ui.app;

import java.util.Locale;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.fc.entity.Team;

/**
 * @author pskopek
 *
 */
public class TeamFilter extends ViewerFilter {

    private String searchString;

    public void setSearchText(String s) {
        searchString = ".*" + s.toLowerCase(Locale.forLanguageTag("sk")) + ".*";
    }


    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
     */
    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {

        if (searchString == null || searchString.length() == 0) {
            return true;
        }

        Team t = (Team) element;
        if (t.getSearchField().matches(searchString)) {
            return true;
        }

        return false;
    }

}
