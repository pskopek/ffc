package org.fc.ui.app;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.fc.data.Contest;
import org.fc.entity.Team;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class TeamWindow extends Shell {
	private DataBindingContext m_bindingContext;

	private Table table;
	private TeamFrame teamFrame;
	private TableViewer m_teamViever;

	private ArrayList<Team> teams = Contest.getContest().getTeams();

	/**
	 * Create the shell.
	 * @param display
	 */
	public TeamWindow(Display display) {
		super(display, SWT.SHELL_TRIM | SWT.APPLICATION_MODAL);
		setLayout(new GridLayout(1, false));
		
		m_teamViever = new TableViewer(this, SWT.FULL_SELECTION);
		table = m_teamViever.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_2.setWidth(35);
		tblclmnNewColumn_2.setText("Id");
		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(340);
		tblclmnNewColumn.setText("Meno");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setToolTipText("");
		tblclmnNewColumn_1.setWidth(301);
		tblclmnNewColumn_1.setText("Organizácia");
		
		TableColumn tblclmnNewColumn_3 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_3.setWidth(240);
		tblclmnNewColumn_3.setText("Plán");
		
		TableColumn tblclmnNewColumn_4 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_4.setWidth(100);
		tblclmnNewColumn_4.setText("Dis");
		m_teamViever.setContentProvider(new ObservableListContentProvider());
		
		teamFrame = new TeamFrame(this, SWT.NONE);
		GridData gd_teamFrame = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_teamFrame.widthHint = 586;
		teamFrame.setLayoutData(gd_teamFrame);
		
		Composite composite = new Composite(this, SWT.BORDER);
		composite.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false, 1, 1));
		composite.setLayout(new GridLayout(4, true));
		
		Button btnNewTeam = new Button(composite, SWT.NONE);
		btnNewTeam.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Team t = new Team(); 
				Contest.getContest().getTeams().add(t);
				m_teamViever.add(t);
				m_teamViever.setSelection(new StructuredSelection(t), true);
				m_bindingContext.updateModels();
			}
		});
		GridData gd_btnNewTeam = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnNewTeam.widthHint = 94;
		btnNewTeam.setLayoutData(gd_btnNewTeam);
		btnNewTeam.setText("Nový");
		
		Button btnDelete = new Button(composite, SWT.NONE);
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				IStructuredSelection sel = (IStructuredSelection)m_teamViever.getSelection();
				Team t = (Team)sel.getFirstElement();
				
				
				boolean confirm = MessageDialog.openConfirm(TeamWindow.this,
						"Potvrď vymazanie",
						"Nauzaj chcete vymazať '"
								+ t.getName() + " - " + t.getOrganisation() + "'?");
				if (confirm) {
					m_teamViever.remove(t);
					
					ArrayList<Team> teams = Contest.getContest().getTeams(); 
					
					int newIndex =teams.indexOf(t);
					teams.remove(t);

					if (newIndex < teams.size()) {
						// try to select
						m_teamViever.setSelection(new StructuredSelection(teams.get(newIndex)), true);
					}
					else if (teams.size() > 0) {
						m_teamViever.setSelection(new StructuredSelection(teams.get(teams.size() - 1)), true);
					}
					
					m_teamViever.refresh();
					m_bindingContext.updateModels();
				}
			}
		});
		btnDelete.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnDelete.setBounds(0, 0, 96, 27);
		btnDelete.setText("Vymaž");
		
		Button btnPrint = new Button(composite, SWT.NONE);
		btnPrint.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnPrint.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnPrint.setBounds(0, 0, 96, 27);
		btnPrint.setText("Tlač");
		
		Button btnNewButton_4 = new Button(composite, SWT.NONE);
		btnNewButton_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TeamWindow.this.close();
			}
		});
		btnNewButton_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnNewButton_4.setBounds(0, 0, 96, 27);
		btnNewButton_4.setText("Zatvor");
		createContents();
		m_bindingContext = initDataBindings();
	}

	
	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Súťažiaci");
		setSize(1100, 700);

		

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	/**
	 * Nasty workaround as this should be done automatically by binding.
	 * TODO: investigate later
	 */
	public void refreshTeamViewerSelection() {
		m_teamViever.refresh();
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue teamVieverObserveSingleSelection = ViewersObservables.observeSingleSelection(m_teamViever);
		IObservableValue teamVieverNameObserveDetailValue = PojoObservables.observeDetailValue(teamVieverObserveSingleSelection, "name", String.class);
		IObservableValue teamFramegetTxtTextnameObserveTextObserveWidget = SWTObservables.observeText(teamFrame.getTxtTextname(), SWT.Modify);
		bindingContext.bindValue(teamVieverNameObserveDetailValue, teamFramegetTxtTextnameObserveTextObserveWidget, null, null);
		//
		IObservableValue teamVieverObserveSingleSelection_1 = ViewersObservables.observeSingleSelection(m_teamViever);
		IObservableValue teamVieverOrganisationObserveDetailValue = PojoObservables.observeDetailValue(teamVieverObserveSingleSelection_1, "organisation", String.class);
		IObservableValue teamFramegetTxtTextorganisationObserveTextObserveWidget = SWTObservables.observeText(teamFrame.getTxtTextorganisation(), SWT.Modify);
		bindingContext.bindValue(teamVieverOrganisationObserveDetailValue, teamFramegetTxtTextorganisationObserveTextObserveWidget, null, null);
		//
		ObservableListContentProvider listContentProvider = new ObservableListContentProvider();
		m_teamViever.setContentProvider(listContentProvider);
		//
		IObservableMap[] observeMaps = PojoObservables.observeMaps(listContentProvider.getKnownElements(), Team.class, new String[]{"id", "name", "organisation", "planAsText", "disqualified"});
		m_teamViever.setLabelProvider(new ObservableMapLabelProvider(observeMaps));
		//
		WritableList writableList = new WritableList(teams, Team.class);
		m_teamViever.setInput(writableList);
		//
		IObservableValue teamVieverObserveSingleSelection_2 = ViewersObservables.observeSingleSelection(m_teamViever);
		IObservableValue teamVieverDummyObserveDetailValue = PojoObservables.observeDetailValue(teamVieverObserveSingleSelection_2, "dummy", boolean.class);
		IObservableValue teamFramegetBtnDummyObserveSelectionObserveWidget = SWTObservables.observeSelection(teamFrame.getBtnDummy());
		bindingContext.bindValue(teamVieverDummyObserveDetailValue, teamFramegetBtnDummyObserveSelectionObserveWidget, null, null);
		//
		IObservableValue teamVieverObserveSingleSelection_3 = ViewersObservables.observeSingleSelection(m_teamViever);
		IObservableValue teamVieverDisqualifiedObserveDetailValue = PojoObservables.observeDetailValue(teamVieverObserveSingleSelection_3, "disqualified", boolean.class);
		IObservableValue teamFramegetBtnDisqualifiedObserveSelectionObserveWidget = SWTObservables.observeSelection(teamFrame.getBtnDisqualified());
		bindingContext.bindValue(teamVieverDisqualifiedObserveDetailValue, teamFramegetBtnDisqualifiedObserveSelectionObserveWidget, null, null);
		//
		return bindingContext;
	}
}
