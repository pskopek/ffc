/**
 * 
 */
package org.fc.ui.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Shell;
import org.fc.data.Contest;
import org.fc.entity.Result;
import org.fc.entity.Round;
import org.fc.entity.Team;
import org.fc.entity.report.Boats;
import org.fc.entity.report.CatchForm;
import org.fc.reports.ReportLoaderObject;

import yaf.reporting.ReportDescriptor;
import yaf.reporting.ui.PrintDialog;

/**
 * @author pskopek
 *
 */
public class Reports {
	
	public static void printFinalResults(Shell parent) {
		Map<String, Object> reportParameters = new HashMap<String, Object>();
		reportParameters.put("contestDate", Contest.CONTEST_DATE);

		Contest.getContest().finalResultsCalculation();
		
		ReportDescriptor rd = new ReportDescriptor("Záverečné výsledky",
				"FinalResults.jasper", //$NON-NLS-1$
				ReportLoaderObject.getLoader(), reportParameters) {

			@Override
			public List<?> invoke() throws Throwable {
				return Contest.getContest().getFinalResults();
			}

		};
		
		PrintDialog dialog = new PrintDialog(parent,
				"Tlač záverečných výsledkov", 
				rd);
		dialog.open();		
		
	}

	
	public static void printPartialResults(Shell parent, final int round) {
		Map<String, Object> reportParameters = new HashMap<String, Object>();
		reportParameters.put("contestDate", Contest.CONTEST_DATE);

		ReportDescriptor rd = new ReportDescriptor("Priebežné výsledky",
				"RoundResults.jasper", //$NON-NLS-1$
				ReportLoaderObject.getLoader(), reportParameters) {

			@Override
			public List<?> invoke() throws Throwable {
				List<Object> results = new ArrayList<Object>(); 

				for (Result res: Contest.getContest().getResults()) {
					if (res.getRound() == round) {
						results.add(res);
					}	
				}
				return results;
			}

		};
		
		PrintDialog dialog = new PrintDialog(parent,
				"Tlač priebežných výsledkov", 
				rd);
		dialog.open();		
		
	}

	public static void printBoats(Shell parent) {
		
		Map<String, Object> reportParameters = new HashMap<String, Object>();


		ReportDescriptor rd = new ReportDescriptor("Rozdelenie lodí",
				"boats.jasper", //$NON-NLS-1$
				ReportLoaderObject.getLoader(), reportParameters) {

			@Override
			public List<?> invoke() throws Throwable {
				List<?> boats = Contest.getContest().reportBoats();  
				return boats;
			}

		};
		
		PrintDialog dialog = new PrintDialog(parent,
				"Tlač rozdelenia lodí", 
				rd);
		dialog.open();		
		
	}
	
	public static void printCatchForms(Shell parent) {
		
		Map<String, Object> reportParameters = new HashMap<String, Object>();
		reportParameters.put("contestDate", Contest.CONTEST_DATE);

		RoundParameter dPar = new RoundParameter(parent);
		int res = dPar.open();
		
		if (res < 0)
			return;
		
		final int round = res;

		
		ReportDescriptor rd = new ReportDescriptor("Bodovacie lístky",
				"catchForms.jasper", //$NON-NLS-1$
				ReportLoaderObject.getLoader(), reportParameters) {

			@Override
			public List<?> invoke() throws Throwable {

				List<?> boats = Contest.getContest().reportBoats();

				List<Object> forms = new ArrayList<Object>();
				
				for (Team t: Contest.getContest().getTeams()) {
					
					String role = t.getRoundPlan().get(round).getRole(); 
					if (role == null || !role.equals("R")) {
						CatchForm cf = new CatchForm();
						cf.id = t.getId().toString();
						cf.name = t.getName();
						cf.organisation = t.getOrganisation();
						cf.round = String.valueOf(round + 1);
						cf.sector = t.getRoundPlan().get(round).getSector();
						cf.referee = Reports.findReferee(t, round);
						if (cf.sector.equals("H")) {
							cf.location = Integer.toString(t.getRoundPlan().get(round).getLocation()); 
						}
						else {
							Boats b = null;
							for (Object o: boats) {
								b = (Boats)o;
								if (b.round == round
										&& b.sector.equals(cf.sector) 
										&& b.location == t.getRoundPlan().get(round).getLocation())
									break;
							}
							
							if (b != null) {
								cf.location = "Loď:" + b.sector + b.location 
										+ " - Predný:" + b.front 
										+ " - Zadný:" + b.rear + "\n" 
										+ " - Rozhodca:" + b.referee; 
							}
							else {
								cf.location = "null"; 
							}
						}
						
						forms.add(cf);
					}
					
				}
				return forms;
			}


		};

		PrintDialog dialog = new PrintDialog(parent,
				"Tlač bodovacích lístkov", 
				rd);
		dialog.open();		
		
	}

	public static String findReferee(Team t, int round) {
		
		Round r = t.getRoundPlan().get(round);
		
		if (r.getSector().equals("H")) {
			return "";
		}
		else {
			for(Team m: Contest.getContest().getTeams()) {
				Round a = m.getRoundPlan().get(round);
				if (a.getRole() != null 
						&& a.getRole().equals("R") 
						&& a.getSector().equals(r.getSector()) 
						&& a.getLocation() == r.getLocation()   ) {
					
					return m.getName();
							
				}
			}
		}
		
		return null;
	}
	
	public static String findBoatCrew(Team t, int round) {

		
		
		return null;
	}
}