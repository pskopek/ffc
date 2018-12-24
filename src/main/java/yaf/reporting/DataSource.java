/*
 *
 */
package yaf.reporting;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * YAF implementation of JRDataSource.
 *
 * @author pskopek
 */
public class DataSource implements JRDataSource {

    protected static final Logger logger = Logger.getLogger(DataSource.class);

    private List<?> data;
    private Iterator<?> iterator;

    HashMap<String, JRDesignFieldAdapter> bufferTable = null;
    private boolean singleBuffer;
    private Object row;

    HashMap<String, Integer> bufferCount = new HashMap<String, Integer>();


    public DataSource(List<?> data) {

        this.data = data;
        this.iterator = this.data.iterator();
        this.singleBuffer = true;
        bufferTable = new HashMap<String, JRDesignFieldAdapter>(10);

        // preset the structure of an array
        if (data.size() > 0) {

            Object o = this.data.get(0);

            if (o.getClass().isArray()) {
                this.singleBuffer = false;

                for (int i = 0; i < Array.getLength(o); i++) {
                    Object element = Array.get(o, i);

                    if (element == null) {
                        // find another non null line
                        for (int j = 1; j < data.size(); j++) {
                            element = Array.get(data.get(j), i);
                            if (element != null)
                                break;
                        }

                    }

                    if (element != null)
                        addFieldsToBufferTable(element.getClass().getSimpleName(), element, i);
                }
            } else {
                addFieldsToBufferTable(o.getClass().getSimpleName(), o, -1);
            }

        } else {
            throw new DataSetEmptyException();
        }

    }


    private void addFieldsToBufferTable(String bufferName, Object obj, int index) {

        int count;
        if (bufferCount.containsKey(bufferName)) {
            count = bufferCount.get(bufferName).intValue();
            bufferCount.put(bufferName, new Integer(++count));
        } else {
            count = 1;
            bufferCount.put(bufferName, new Integer(1));
        }
        String realBufferName = (count == 1 ? bufferName : bufferName + "_" + count); //$NON-NLS-1$

        Field[] flds = obj.getClass().getDeclaredFields();
        for (int i = 0; i < flds.length; i++) {

            String key = realBufferName + "." + flds[i].getName(); //$NON-NLS-1$

            JRDesignFieldAdapter f = new JRDesignFieldAdapter(
                    key,
                    flds[i].getType(),
                    index);

            bufferTable.put(key, f);

        }


    }


    /**
     * @see net.sf.jasperreports.engine.JRDataSource#getFieldValue(net.sf.jasperreports.engine.JRField)
     * In case of non existent field return rather null then throw exception.
     */
    public Object getFieldValue(JRField field) throws JRException {

        if (row != null) {

            String referencedName = field.getName();
            Object element;
            if (!singleBuffer) {
                try {

                    JRDesignFieldAdapter fa = bufferTable.get(referencedName);
                    if (fa == null)
                        return null;
                    int i = fa.getIndex();
                    element = Array.get(row, i);
                } catch (Exception e) {
                    throw new JRException(e);
                }
            } else {
                element = row;
            }

            String[] parts = referencedName.split("\\.");//$NON-NLS-1$
            try {
                String methodName = "get" + parts[1].substring(0, 1).toUpperCase() + parts[1].substring(1);//$NON-NLS-1$
                Method method = element.getClass().getMethod(methodName, (Class[]) null);
                return method.invoke(element, (Object[]) null);
            } catch (NoSuchMethodException e) {
                try {
                    Field f = element.getClass().getField(parts[1]);
                    return f.get(element);
                } catch (NoSuchFieldException nsfe) {
                    return null;
                } catch (IllegalAccessException iae) {
                    return null;
                }
            } catch (Exception e) {
                throw new JRException(e);
            }

        }

        throw new JRException("current row equals to null");//$NON-NLS-1$

    }

    /* (non-Javadoc)
     * @see net.sf.jasperreports.engine.JRDataSource#next()
     */
    public boolean next() throws JRException {

        boolean nxt = iterator.hasNext();
        if (nxt)
            row = iterator.next();
        else
            row = null;

        return nxt;
    }

    public JRField[] getFields() {
        return bufferTable.values().toArray(new JRDesignFieldAdapter[0]);
    }

}
