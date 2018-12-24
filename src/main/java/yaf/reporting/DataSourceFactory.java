package yaf.reporting;

import java.util.List;

import yaf.share.exceptions.ReportException;


/**
 * DataSource factory class.
 *
 * @author pskopek
 */
public class DataSourceFactory {

    public static DataSource createDataSource(ReportDescriptor report) throws ReportException {

        List<?> dataSet = null;
        try {
            dataSet = report.invoke();
        } catch (Throwable e) {
            throw new ReportException(e);
        }

        if (dataSet == null || dataSet.isEmpty()) {
            throw new DataSetEmptyException();
        }

        try {
            return new DataSource(dataSet);
        } catch (Throwable e) {
            throw new ReportException(e);
        }

    }

}
