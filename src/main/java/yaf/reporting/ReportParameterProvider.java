/*
 *
 */
package yaf.reporting;

import java.util.Map;

/**
 * This interface is intended for use with ReportDescriptor to provide reference to parameter provider.
 *
 * @author pskopek
 */
public interface ReportParameterProvider<T> {

    /**
     * This method returns parameters to be used with report.
     *
     * @return
     */
    public Map<String, T> getParameters();

}
