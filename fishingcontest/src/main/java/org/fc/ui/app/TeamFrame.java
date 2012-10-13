package org.fc.ui.app;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class TeamFrame extends Composite {
	private Text txtTextname;
	private Text txtTextorganisation;
	private Button btnDisqualified;
	private Button btnDummy;
	private Label lblNewLabel;
	private Text txtFee;
	private Button btnReferee;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TeamFrame(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		Label lblMeno = new Label(this, SWT.NONE);
		lblMeno.setAlignment(SWT.RIGHT);
		GridData gd_lblMeno = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblMeno.widthHint = 77;
		lblMeno.setLayoutData(gd_lblMeno);
		lblMeno.setText("Meno");
		
		txtTextname = new Text(this, SWT.BORDER);
		txtTextname.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				// part of the nasty workaround (see refreshTeamViewerSelection description)
				((TeamWindow)getParent()).refreshTeamViewerSelection();
			}
		});
		txtTextname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblOrgani = new Label(this, SWT.NONE);
		lblOrgani.setAlignment(SWT.RIGHT);
		lblOrgani.setText("Organizácia");
		GridData gd_lblOrgani = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblOrgani.widthHint = 90;
		lblOrgani.setLayoutData(gd_lblOrgani);
		
		txtTextorganisation = new Text(this, SWT.BORDER);
		txtTextorganisation.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				// part of the nasty workaround (see refreshTeamViewerSelection description)
				((TeamWindow)getParent()).refreshTeamViewerSelection();
			}
		});
		txtTextorganisation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Zaplatené");
		
		txtFee = new Text(this, SWT.BORDER);
		txtFee.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				((TeamWindow)getParent()).refreshTeamViewerSelection();
			}
		});
		txtFee.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(4, false));
		GridData gd_composite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite.heightHint = 32;
		composite.setLayoutData(gd_composite);
		
		btnDisqualified = new Button(composite, SWT.CHECK);
		btnDisqualified.addSelectionListener(new SelectionAdapter() {
		});
		GridData gd_btnDisqualified = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnDisqualified.widthHint = 151;
		btnDisqualified.setLayoutData(gd_btnDisqualified);
		btnDisqualified.setText("Diskvalifikovaný");
		
		btnDummy = new Button(composite, SWT.CHECK);
		btnDummy.addSelectionListener(new SelectionAdapter() {
		});
		GridData gd_btnDummy = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnDummy.widthHint = 123;
		btnDummy.setLayoutData(gd_btnDummy);
		btnDummy.setText("Doplnený");
		
		btnReferee = new Button(composite, SWT.CHECK);
		btnReferee.setText("Rozhodca");
		new Label(composite, SWT.NONE);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public Text getTxtTextname() {
		return txtTextname;
	}
	public Text getTxtTextorganisation() {
		return txtTextorganisation;
	}
	
	
	
	public Button getBtnDisqualified() {
		return btnDisqualified;
	}
	
	public Button getBtnDummy() {
		return btnDummy;
	}
	
	public Text getTxtFee() {
		return txtFee;
	}

	public Button getBtnReferee() {
		return btnReferee;
	}
}
