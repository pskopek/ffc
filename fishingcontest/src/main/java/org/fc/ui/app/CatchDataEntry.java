package org.fc.ui.app;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.fc.data.Contest;
import org.fc.entity.Catch;
import org.fc.entity.Team;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.widgets.Text;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class CatchDataEntry extends Shell {
	private DataBindingContext m_bindingContext;
	private Table tableTeam;
	private Table tableCatch;
	
	private ArrayList<Team> teams = Contest.getContest().getTeams();
	
	private UiHelperBean actTeamCont = new UiHelperBean(); 
	
	private TableViewer teamTableViewer;
	private Text txtLength;
	private Text txtFishType;
	private TableViewer catchTableViewer;
	private Text txtRound;
	private Text txtSector;
	private Text txtCISP;
	private Button btnLockUI;
	private Text txtDefaultRound;
	private Text txtDefaultSector;
	private Text txtDefaultFishType;
	

	/**
	 * Create the shell.
	 * @param display
	 */
	public CatchDataEntry(Display display) {
		super(display, SWT.SHELL_TRIM | SWT.APPLICATION_MODAL);
		setLayout(new GridLayout(1, false));
		
		SashForm sashForm = new SashForm(this, SWT.BORDER | SWT.VERTICAL);
		GridData gd_sashForm = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_sashForm.widthHint = 334;
		sashForm.setLayoutData(gd_sashForm);
		sashForm.setSashWidth(5);
		
		Composite composite_1 = new Composite(sashForm, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite_5 = new Composite(composite_1, SWT.NONE);
		composite_5.setLayout(new GridLayout(6, false));
		
		Label lblNewLabel_3 = new Label(composite_5, SWT.NONE);
		lblNewLabel_3.setBounds(0, 0, 70, 17);
		lblNewLabel_3.setText("Kolo");
		
		txtDefaultRound = new Text(composite_5, SWT.BORDER);
		txtDefaultRound.setBounds(0, 0, 73, 25);
		
		Label lblSektor = new Label(composite_5, SWT.NONE);
		lblSektor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSektor.setText("Sektor");
		
		txtDefaultSector = new Text(composite_5, SWT.BORDER);
		
		Label lblNewLabel_4 = new Label(composite_5, SWT.NONE);
		lblNewLabel_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_4.setText("Druh ryby");
		
		txtDefaultFishType = new Text(composite_5, SWT.BORDER);
		
		teamTableViewer = new TableViewer(composite_5, SWT.BORDER | SWT.FULL_SELECTION);
		tableTeam = teamTableViewer.getTable();
		tableTeam.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 6, 1));
		tableTeam.setLinesVisible(true);
		tableTeam.setHeaderVisible(true);
		
		TableColumn tblclmnNewColumn = new TableColumn(tableTeam, SWT.NONE);
		tblclmnNewColumn.setWidth(35);
		tblclmnNewColumn.setText("Id");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(tableTeam, SWT.NONE);
		tblclmnNewColumn_1.setWidth(340);
		tblclmnNewColumn_1.setText("Meno");
		
		TableColumn tblclmnNewColumn_2 = new TableColumn(tableTeam, SWT.NONE);
		tblclmnNewColumn_2.setWidth(300);
		tblclmnNewColumn_2.setText("Organizácia");
		
		TableColumn tblclmnNewColumn_3 = new TableColumn(tableTeam, SWT.NONE);
		tblclmnNewColumn_3.setWidth(30);
		tblclmnNewColumn_3.setText("Dis");
		new Label(composite_5, SWT.NONE);
		new Label(composite_5, SWT.NONE);
		new Label(composite_5, SWT.NONE);
		new Label(composite_5, SWT.NONE);
		new Label(composite_5, SWT.NONE);
		new Label(composite_5, SWT.NONE);
		
		Composite composite_2 = new Composite(sashForm, SWT.NONE);
		composite_2.setLayout(new GridLayout(1, false));
		
		SashForm sashForm_1 = new SashForm(composite_2, SWT.VERTICAL);
		sashForm_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		catchTableViewer = new TableViewer(sashForm_1, SWT.BORDER | SWT.FULL_SELECTION);
		tableCatch = catchTableViewer.getTable();
		tableCatch.setLinesVisible(true);
		tableCatch.setHeaderVisible(true);
		
		TableColumn tblclmnNewColumn_7 = new TableColumn(tableCatch, SWT.NONE);
		tblclmnNewColumn_7.setWidth(35);
		tblclmnNewColumn_7.setText("Kolo");
		
		TableColumn tblclmnNewColumn_8 = new TableColumn(tableCatch, SWT.NONE);
		tblclmnNewColumn_8.setWidth(60);
		tblclmnNewColumn_8.setText("Sektor");
		
		TableColumn tblclmnNewColumn_4 = new TableColumn(tableCatch, SWT.NONE);
		tblclmnNewColumn_4.setText("Dĺžka");
		tblclmnNewColumn_4.setWidth(100);
		
		TableColumn tblclmnNewColumn_5 = new TableColumn(tableCatch, SWT.NONE);
		tblclmnNewColumn_5.setWidth(100);
		tblclmnNewColumn_5.setText("CIPS Body");
		
		TableColumn tblclmnNewColumn_6 = new TableColumn(tableCatch, SWT.NONE);
		tblclmnNewColumn_6.setWidth(100);
		tblclmnNewColumn_6.setText("Druh ryby");
		
		Composite composite_3 = new Composite(sashForm_1, SWT.NONE);
		composite_3.setLayout(new GridLayout(8, false));
		
		Label lblNewLabel = new Label(composite_3, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Dĺžka (mm)");
		
		txtLength = new Text(composite_3, SWT.BORDER);
		txtLength.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				catchTableViewer.refresh();
			}
		});
		txtLength.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblNewLabel_1 = new Label(composite_3, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("Kolo");
		
		txtRound = new Text(composite_3, SWT.BORDER);
		txtRound.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				catchTableViewer.refresh();
			}
		});
		txtRound.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblCips = new Label(composite_3, SWT.NONE);
		lblCips.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCips.setText("CIPS");
		
		txtCISP = new Text(composite_3, SWT.BORDER);
		txtCISP.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				catchTableViewer.refresh();
			}
		});
		GridData gd_txtCISP = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtCISP.widthHint = 95;
		txtCISP.setLayoutData(gd_txtCISP);
		new Label(composite_3, SWT.NONE);
		
		btnLockUI = new Button(composite_3, SWT.CHECK);
		btnLockUI.setSelection(true);
		btnLockUI.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				toggleUILock();
			}
		});
		GridData gd_btnCheckButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnCheckButton.widthHint = 124;
		btnLockUI.setLayoutData(gd_btnCheckButton);
		btnLockUI.setText("Zamknuté");
		
		Label lblDruhRyby = new Label(composite_3, SWT.NONE);
		lblDruhRyby.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDruhRyby.setText("Druh ryby");
		
		txtFishType = new Text(composite_3, SWT.BORDER);
		txtFishType.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				catchTableViewer.refresh();
			}
		});
		txtFishType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblNewLabel_2 = new Label(composite_3, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setText("Sektor");
		
		txtSector = new Text(composite_3, SWT.BORDER);
		txtSector.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				catchTableViewer.refresh();
			}
		});
		txtSector.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		
		Composite composite_4 = new Composite(composite_3, SWT.NONE);
		GridData gd_composite_4 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite_4.widthHint = 197;
		composite_4.setLayoutData(gd_composite_4);
		composite_4.setLayout(new GridLayout(4, false));
		new Label(composite_4, SWT.NONE);
		new Label(composite_4, SWT.NONE);
		
		Button btnNewCatch = new Button(composite_4, SWT.NONE);
		btnNewCatch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Catch c = new Catch();
				c.setId(Contest.getContest().getNextDataSeq());
				c.setTeamId(actTeamCont.getActualTeam().getId());
				c.setFishType(txtDefaultFishType.getText());
				
				int r = 1;
				try {
					r = Integer.parseInt(txtDefaultRound.getText());
				}
				catch (Exception ex) {
					System.out.println("Error in parsing round " + ex);
				}
				c.setRound(r);
				c.setSector(txtDefaultSector.getText());
				actTeamCont.getActualTeam().getCatched().add(c);
				
				catchTableViewer.add(c);
				catchTableViewer.setSelection(new StructuredSelection(c), true);
				m_bindingContext.updateModels();
				
			}
		});
		GridData gd_btnNewCatch = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnNewCatch.widthHint = 80;
		btnNewCatch.setLayoutData(gd_btnNewCatch);
		btnNewCatch.setText("Nový");
		
		Button btnDeleteCatch = new Button(composite_4, SWT.NONE);
		btnDeleteCatch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection sel = (IStructuredSelection)catchTableViewer.getSelection();
				Catch c = (Catch)sel.getFirstElement();
				
				
				boolean confirm = MessageDialog.openConfirm(CatchDataEntry.this,
						"Potvrď vymazanie",
						"Naozaj chcete vymazať označený úlovok?");
				if (confirm) {
					catchTableViewer.remove(c);
					
					ArrayList<Catch> catches = actTeamCont.getActualTeam().getCatched(); 
					
					int newIndex = catches.indexOf(c);
					catches.remove(c);

					if (newIndex < catches.size()) {
						// try to select
						catchTableViewer.setSelection(new StructuredSelection(catches.get(newIndex)), true);
					}
					else if (catches.size() > 0) {
						catchTableViewer.setSelection(new StructuredSelection(catches.get(catches.size() - 1)), true);
					}
					
					catchTableViewer.refresh();
					m_bindingContext.updateModels();
				}
			}
				
		});
		GridData gd_btnDeleteCatch = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnDeleteCatch.widthHint = 80;
		btnDeleteCatch.setLayoutData(gd_btnDeleteCatch);
		btnDeleteCatch.setText("Vymaž");
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		sashForm_1.setWeights(new int[] {243, 104});
		sashForm.setWeights(new int[] {337, 383});
		
		Composite composite = new Composite(this, SWT.NONE);
		GridData gd_composite = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_composite.widthHint = 978;
		composite.setLayoutData(gd_composite);
		
		Button btnClose = new Button(composite, SWT.NONE);
		btnClose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				close();
			}
		});
		btnClose.setBounds(688, 0, 96, 27);
		btnClose.setText("Close");
		createContents();
		m_bindingContext = initDataBindings();
				
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Úlovky");
		setSize(900, 800);
		txtDefaultFishType.setText(Contest.FISH_TYPE);
		txtDefaultRound.setText(Integer.toString(Contest.ROUND));
		txtDefaultSector.setText(Contest.SECTOR);
		toggleUILock();
		
	}

	private void toggleUILock() {
		txtCISP.setEnabled(! btnLockUI.getSelection());
		txtFishType.setEnabled(! btnLockUI.getSelection());
		txtRound.setEnabled(! btnLockUI.getSelection());
		txtSector.setEnabled(! btnLockUI.getSelection());
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	protected Button getBtnCheckButton() {
		return btnLockUI;
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		ObservableListContentProvider listContentProvider = new ObservableListContentProvider();
		teamTableViewer.setContentProvider(listContentProvider);
		//
		IObservableMap[] observeMaps = PojoObservables.observeMaps(listContentProvider.getKnownElements(), Team.class, new String[]{"id", "name", "organisation", "disqualified"});
		teamTableViewer.setLabelProvider(new ObservableMapLabelProvider(observeMaps));
		//
		WritableList writableList = new WritableList(teams, Team.class);
		teamTableViewer.setInput(writableList);
		//
		ObservableListContentProvider listContentProvider_1 = new ObservableListContentProvider();
		catchTableViewer.setContentProvider(listContentProvider_1);
		//
		IObservableMap[] observeMaps_1 = PojoObservables.observeMaps(listContentProvider_1.getKnownElements(), Catch.class, new String[]{"round", "sector", "length", "cips", "fishType"});
		catchTableViewer.setLabelProvider(new ObservableMapLabelProvider(observeMaps_1));
		//
		IObservableValue teamTableViewerObserveSingleSelection = ViewersObservables.observeSingleSelection(teamTableViewer);
		IObservableList teamTableViewerCatchedObserveDetailList = PojoObservables.observeDetailList(teamTableViewerObserveSingleSelection, "catched", Catch.class);
		catchTableViewer.setInput(teamTableViewerCatchedObserveDetailList);
		//
		IObservableValue catchTableViewerObserveSingleSelection = ViewersObservables.observeSingleSelection(catchTableViewer);
		IObservableValue catchTableViewerCipsObserveDetailValue = PojoObservables.observeDetailValue(catchTableViewerObserveSingleSelection, "cips", int.class);
		IObservableValue txtCISPObserveTextObserveWidget = SWTObservables.observeText(txtCISP, SWT.Modify);
		bindingContext.bindValue(catchTableViewerCipsObserveDetailValue, txtCISPObserveTextObserveWidget, null, null);
		//
		IObservableValue catchTableViewerObserveSingleSelection_1 = ViewersObservables.observeSingleSelection(catchTableViewer);
		IObservableValue catchTableViewerRoundObserveDetailValue = PojoObservables.observeDetailValue(catchTableViewerObserveSingleSelection_1, "round", int.class);
		IObservableValue txtRoundObserveTextObserveWidget = SWTObservables.observeText(txtRound, SWT.Modify);
		bindingContext.bindValue(catchTableViewerRoundObserveDetailValue, txtRoundObserveTextObserveWidget, null, null);
		//
		IObservableValue catchTableViewerObserveSingleSelection_2 = ViewersObservables.observeSingleSelection(catchTableViewer);
		IObservableValue catchTableViewerSectorObserveDetailValue = PojoObservables.observeDetailValue(catchTableViewerObserveSingleSelection_2, "sector", String.class);
		IObservableValue txtSectorObserveTextObserveWidget = SWTObservables.observeText(txtSector, SWT.Modify);
		bindingContext.bindValue(catchTableViewerSectorObserveDetailValue, txtSectorObserveTextObserveWidget, null, null);
		//
		IObservableValue catchTableViewerObserveSingleSelection_3 = ViewersObservables.observeSingleSelection(catchTableViewer);
		IObservableValue catchTableViewerLengthObserveDetailValue = PojoObservables.observeDetailValue(catchTableViewerObserveSingleSelection_3, "length", int.class);
		IObservableValue txtLengthObserveTextObserveWidget = SWTObservables.observeText(txtLength, SWT.Modify);
		bindingContext.bindValue(catchTableViewerLengthObserveDetailValue, txtLengthObserveTextObserveWidget, null, null);
		//
		IObservableValue catchTableViewerObserveSingleSelection_4 = ViewersObservables.observeSingleSelection(catchTableViewer);
		IObservableValue catchTableViewerFishTypeObserveDetailValue = PojoObservables.observeDetailValue(catchTableViewerObserveSingleSelection_4, "fishType", String.class);
		IObservableValue txtFishTypeObserveTextObserveWidget = SWTObservables.observeText(txtFishType, SWT.Modify);
		bindingContext.bindValue(catchTableViewerFishTypeObserveDetailValue, txtFishTypeObserveTextObserveWidget, null, null);
		//
		IObservableValue teamTableViewerObserveSingleSelection_1 = ViewersObservables.observeSingleSelection(teamTableViewer);
		IObservableValue actTeamContActualTeamObserveValue = PojoObservables.observeValue(actTeamCont, "actualTeam");
		bindingContext.bindValue(teamTableViewerObserveSingleSelection_1, actTeamContActualTeamObserveValue, null, null);
		//
		return bindingContext;
	}

	@Override
	public void open() {
		super.open();
		if (teams != null && teams.size() > 0)
			teamTableViewer.setSelection(new StructuredSelection(teams.get(0)), true);

	}
}
