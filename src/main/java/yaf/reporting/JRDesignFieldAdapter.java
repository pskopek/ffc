/**
 *
 */
package yaf.reporting;

import net.sf.jasperreports.engine.design.JRDesignField;

/**
 * @author pskopek
 *
 */
public class JRDesignFieldAdapter extends JRDesignField {

    static final long serialVersionUID = 8968744566533568694L;

    private int index;

    public JRDesignFieldAdapter(String name, Class<?> valueClass, int index) {
        super.setName(name);
        super.setValueClass(valueClass);
        this.index = index;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{" + getName() + "," + getValueClassName() + "," + index + "}";
    }


}
