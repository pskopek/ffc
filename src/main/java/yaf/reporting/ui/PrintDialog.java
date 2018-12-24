/*
 *
 */
package yaf.reporting.ui;

import java.util.Map;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import yaf.reporting.DataSetEmptyException;
import yaf.reporting.DataSource;
import yaf.reporting.DataSourceFactory;
import yaf.reporting.ReportDescriptor;

import com.jasperassistant.designer.viewer.ViewerComposite;


/**
 * This dialog if for print preview like dialog.
 * Note: later on should be standardized as normal print dialog.
 *
 * @author pskopek
 */
public class PrintDialog extends Dialog {

    protected static final Logger logger = Logger.getLogger(PrintDialog.class);

    private ViewerComposite jrViewer = null;
    private String title;
    private ReportDescriptor report;
    private JasperPrint print;

    /**
     * A parent shell
     *
     * @param parentShell
     */
    public PrintDialog(Shell parentShell, String title, ReportDescriptor report) {
        super(parentShell);
        setShellStyle(SWT.DIALOG_TRIM | SWT.RESIZE);
        this.title = title;
        this.report = report;
        createPrint();
    }


    private void createPrint() {

        try {

            Map<String, Object> parameters = report.getParameters();

            DataSource reportDataSource = DataSourceFactory.createDataSource(report);

            print = JasperFillManager.fillReport(
                    report.getLoaderObject().getClass().getResourceAsStream(report.getJasperFile()),
                    parameters,
                    reportDataSource
            );

        } catch (DataSetEmptyException e) {

            MessageDialog.openInformation(
                    getParentShell(),
                    "Chyba tlače",
                    "Prázdny zdroj dát."
            );
        } catch (Exception e) {
            logger.error("Error while preparing report.", e); //$NON-NLS-1$
        }

    }

    @Override
    protected Control createDialogArea(Composite parent) {
        getShell().setText(title);
        Composite comp = (Composite) super.createDialogArea(parent);
        comp.setLayout(new FillLayout());

        if (print != null) {
            jrViewer = new ViewerComposite(comp, SWT.NONE);
            jrViewer.getReportViewer().setDocument(print);
        }

        return comp;
    }


    @Override
    public int open() {

        if (print != null)
            return super.open();

        logger.debug("not opening print dialog (no data to show)");//$NON-NLS-1$
        return 0;

    }


}
