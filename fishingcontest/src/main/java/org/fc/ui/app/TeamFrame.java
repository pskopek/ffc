package org.fc.ui.app;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class TeamFrame extends Composite {
	private Text txtTextname;
	private Text txtTextorganisation;

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
}
