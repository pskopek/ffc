package org.fc.data;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableColumn;
import org.fc.entity.Result;
import org.fc.ui.app.Reports;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ResultViever extends Shell {
	private DataBindingContext m_bindingContext;
	private Table table;
	private TableViewer tableViewer;
	private ArrayList<Result> resultsToView;
	private int round;

	/**
	 * Create the shell.
	 * @param display
	 */
	public ResultViever(Display display, ArrayList<Result> resultsToView) {
		super(display, SWT.SHELL_TRIM | SWT.APPLICATION_MODAL);
		
		this.resultsToView = resultsToView;
		
		setLayout(new GridLayout(1, false));
		
		tableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(40);
		tblclmnNewColumn.setText("Kolo");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(45);
		tblclmnNewColumn_1.setText("Sekt.");
		
		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_2.setWidth(37);
		tblclmnNewColumn_2.setText("Por.");
		
		TableColumn tblclmnNewColumn_3 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_3.setWidth(137);
		tblclmnNewColumn_3.setText("Meno");
		
		TableColumn tblclmnNewColumn_4 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_4.setWidth(172);
		tblclmnNewColumn_4.setText("Organizácia");
		
		TableColumn tblclmnNewColumn_5 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_5.setWidth(100);
		tblclmnNewColumn_5.setText("CIPS");
		
		TableColumn tblclmnNewColumn_6 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_6.setWidth(100);
		tblclmnNewColumn_6.setText("Počet rýb");
		
		TableColumn tblclmnNewColumn_7 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_7.setWidth(100);
		tblclmnNewColumn_7.setText("Max ryba");
		
		TableColumn tblclmnNewColumn_8 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_8.setWidth(100);
		tblclmnNewColumn_8.setText("Body um.");
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		composite.setLayout(null);
		
		Button btnPrint = new Button(composite, SWT.NONE);
		btnPrint.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Reports.printPartialResults(ResultViever.this.getShell(), round);
			}
		});
		btnPrint.setBounds(225, 0, 96, 27);
		btnPrint.setToolTipText("");
		btnPrint.setText("Tlač");
		
		Button btnClose = new Button(composite, SWT.NONE);
		btnClose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ResultViever.this.getShell().close();
			}
		});
		btnClose.setBounds(335, 0, 96, 27);
		btnClose.setText("Zatvor");
		createContents();
		m_bindingContext = initDataBindings();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {

		if (resultsToView != null && resultsToView.size() > 0) {
			round = resultsToView.get(0).getRound();
		}
		
		
		setText("Výsledky po " + round + ". kole" );
		setSize(832, 431);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public TableViewer getTableViewer() {
		return tableViewer;
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		ObservableListContentProvider listContentProvider = new ObservableListContentProvider();
		tableViewer.setContentProvider(listContentProvider);
		//
		IObservableMap[] observeMaps = PojoObservables.observeMaps(listContentProvider.getKnownElements(), Result.class, new String[]{"round", "sector", "order", "name", "organisation", "cips", "amount", "max", "orderPoints"});
		tableViewer.setLabelProvider(new ObservableMapLabelProvider(observeMaps));
		//
		WritableList writableList = new WritableList(resultsToView, Result.class);
		tableViewer.setInput(writableList);
		//
		return bindingContext;
	}
}
