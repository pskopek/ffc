package org.fc.ui.app;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Control;
import org.fc.data.Contest;
import org.fc.data.ContestDrawException;

public class FishingContestApp {
	private static Shell shlFishingContest;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = new Display();
		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
			public void run() {

				try {
					FishingContestApp window = new FishingContestApp();
					window.open();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}	
	
	public void open() {
		Display display = Display.getDefault();
		shlFishingContest = new Shell();
		shlFishingContest.setSize(357, 221);
		shlFishingContest.setText("Rybárske preteky");
		shlFishingContest.setLayout(new GridLayout(3, false));
		
		Button btnContesters = new Button(shlFishingContest, SWT.NONE);
		GridData gd_btnContesters = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnContesters.widthHint = 120;
		btnContesters.setLayoutData(gd_btnContesters);
		btnContesters.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new TeamWindow(shlFishingContest.getDisplay()).open();
			}
		});
		btnContesters.setText("Pretekári");
		new Label(shlFishingContest, SWT.NONE);
		new Label(shlFishingContest, SWT.NONE);
		
		Button btnNewContest = new Button(shlFishingContest, SWT.NONE);
		btnNewContest.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean confirm = MessageDialog.openConfirm(shlFishingContest,
						"Potvrď vytvorenie nového preteku",
						"Nauzaj chcete vytvoriť nový pretek?\nVšetky údaje aktuálneho preteku budú stratené!!!'");
				if (confirm) {
					Contest.createNewContest();
				}
			}
		});
		GridData gd_btnNewContest = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnNewContest.widthHint = 120;
		btnNewContest.setLayoutData(gd_btnNewContest);
		btnNewContest.setText("Nový pretek");
		
		Button btnLoadContest = new Button(shlFishingContest, SWT.NONE);
		btnLoadContest.setToolTipText("Načítaj celý pretek zo súboru.");
		GridData gd_btnLoadContest = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnLoadContest.widthHint = 100;
		btnLoadContest.setLayoutData(gd_btnLoadContest);
		btnLoadContest.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					FileDialog fd = new FileDialog(shlFishingContest, SWT.OPEN);
			        fd.setText("Načítať súbor s pretekom");
			        String[] filterExt = { "*.xml", "*.*" };
			        fd.setFilterExtensions(filterExt);
			        String selected = fd.open();
					Contest.getContest().loadFromFile(selected);
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});
		btnLoadContest.setText("Načítaj ...");
		
		Button btnSaveContest = new Button(shlFishingContest, SWT.NONE);
		GridData gd_btnSaveContest = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnSaveContest.widthHint = 110;
		btnSaveContest.setLayoutData(gd_btnSaveContest);
		btnSaveContest.setToolTipText("");
		btnSaveContest.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
			        FileDialog fd = new FileDialog(shlFishingContest, SWT.SAVE);
			        fd.setText("Zapíš aktuálny stav preteku do súboru");
			        String[] filterExt = { "*.xml" };
			        fd.setFilterExtensions(filterExt);
			        String selected = fd.open();
					Contest.getContest().dumpToFile(selected);
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnSaveContest.setText("Zapíš");
		
		Button btnDraw = new Button(shlFishingContest, SWT.NONE);
		btnDraw.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean confirm = MessageDialog.openConfirm(shlFishingContest,
						"Potvrď nového rozlosovanie",
						"Naozaj chcete urobiť nové rozlosovanie?\nVšetky údaje o úlohkoch vo všetkých kolách budú stratené!!!'");
				if (confirm) {
					try {
						Contest.getContest().draw();
						MessageDialog.openInformation(shlFishingContest,
								"Nové rozlosovanie",
								"Nové rozlosovanie je aktívne.");
					}
					catch (ContestDrawException ce) {
						ce.printStackTrace();
						MessageDialog.openError(shlFishingContest,
								"Chyba pri rozlosovaní",
								ce.getMessage());
					}
					catch (Throwable te) {
						te.printStackTrace();
					}
				}
			}
		});
		GridData gd_btnDraw = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnDraw.widthHint = 122;
		btnDraw.setLayoutData(gd_btnDraw);
		btnDraw.setText("Rozlosovanie");
		
		Button btnPrintBoats = new Button(shlFishingContest, SWT.NONE);
		btnPrintBoats.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Reports.printBoats(shlFishingContest);
			}
		});
		GridData gd_btnPrintBoats = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnPrintBoats.widthHint = 100;
		btnPrintBoats.setLayoutData(gd_btnPrintBoats);
		btnPrintBoats.setText("Tlač lodí");
		new Label(shlFishingContest, SWT.NONE);
		
		Button btnResults = new Button(shlFishingContest, SWT.NONE);
		GridData gd_btnResults = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnResults.widthHint = 121;
		btnResults.setLayoutData(gd_btnResults);
		btnResults.setText("Výsledky");
		new Label(shlFishingContest, SWT.NONE);
		new Label(shlFishingContest, SWT.NONE);
		new Label(shlFishingContest, SWT.NONE);
		new Label(shlFishingContest, SWT.NONE);
		
		Button btnClose = new Button(shlFishingContest, SWT.NONE);
		btnClose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlFishingContest.close();
			}
		});
		GridData gd_btnClose = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnClose.widthHint = 109;
		gd_btnClose.heightHint = 54;
		btnClose.setLayoutData(gd_btnClose);
		btnClose.setText("Koniec");
		shlFishingContest.setTabList(new Control[]{btnContesters, btnNewContest, btnLoadContest, btnDraw, btnResults, btnClose});

		createDefaultTestingContent();
		
		shlFishingContest.open();
		shlFishingContest.layout();
		while (!shlFishingContest.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private static void createDefaultTestingContent() {
		Contest c = Contest.getContest();
		c.createTestData();
	}

	public Shell getShlFishingContest() {
		return shlFishingContest;
	}
}
