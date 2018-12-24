/**
 *
 */
package yaf.reporting;

import java.util.List;
import java.util.Map;


/**
 * Creates Report Descriptor object.
 * If you cannot supply parameters in the time of creation supply ReportParameterProvider, which will be called to supply
 * parameters. In case it is set.
 * @author pskopek
 *
 */
public abstract class ReportDescriptor {

    private final String reportName;
    private final String jasperFile;
    private Object loaderObject;
    private Map<String, Object> parameters;
    private ReportParameterProvider<Object> parameterProvider = null;

    public ReportDescriptor(String reportName, String jasperFile, Object loaderObject, Map<String, Object> parameters) {
        this.reportName = reportName;
        this.jasperFile = jasperFile;
        this.loaderObject = loaderObject;
        this.parameters = parameters;
        this.parameterProvider = null;
    }

    public ReportDescriptor(String reportName, String jasperFile, Object loaderObject, ReportParameterProvider<Object> parameterProvider) {
        this.reportName = reportName;
        this.jasperFile = jasperFile;
        this.loaderObject = loaderObject;
        this.parameters = null;
        this.parameterProvider = parameterProvider;
    }

    /**
     * Return current parameters associated with the report.
     * @return
     */
    public Map<String, Object> getParameters() {

        if (parameters != null) {
            return parameters;
        } else {

            if (parameterProvider != null) {
                return parameterProvider.getParameters();
            }

            // TODO Internationalize this.
            throw new IllegalStateException("Missing parameter provider along with missing parameters.");

        }
    }

    /**
     * @return the reportName
     */
    public String getReportName() {
        return reportName;
    }

    /**
     * @return the jasperFile
     */
    public String getJasperFile() {
        return jasperFile;
    }

    /**
     * Return the loader object which have to be in the same package as jasper file to be able to lead Jasper file as resource.
     * @return
     */
    public Object getLoaderObject() {
        return loaderObject;
    }


    /**
     * Call to the appserver or different source of data.
     * @return
     * @throws Throwable
     */
    public abstract List<?> invoke() throws Throwable;


}
