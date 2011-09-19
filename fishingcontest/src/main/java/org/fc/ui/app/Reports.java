/**
 * 
 */
package org.fc.ui.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Shell;
import org.fc.data.Contest;
import org.fc.reports.ReportLoaderObject;

import yaf.reporting.ReportDescriptor;
import yaf.reporting.ui.PrintDialog;

/**
 * @author pskopek
 *
 */
public class Reports {

	public static void printBoats(Shell parent) {
		
		Map<String, Object> reportParameters = new HashMap<String, Object>();


		ReportDescriptor rd = new ReportDescriptor("Rozdelenie lodí",
				"boats.jasper", //$NON-NLS-1$
				ReportLoaderObject.getLoader(), reportParameters) {

			@Override
			public List<?> invoke() throws Throwable {
				
				List<?> boats = Contest.getContest().reportBoats(); 
				
				System.out.println("Boats:");
				for (Object o: boats) 
					System.out.println(o);
				
				return boats;
			}

		};

		PrintDialog dialog = new PrintDialog(parent,
				"Tlač rozdelenia lodí", 
				rd);
		dialog.open();		
		
	}
	
}
