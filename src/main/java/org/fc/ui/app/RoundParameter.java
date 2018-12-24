package org.fc.ui.app;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.fc.data.Contest;

public class RoundParameter extends Dialog {

    protected int result = -1;
    protected Shell shell;
    private Text tRound;

    /**
     * Create the dialog.
     *
     * @param parent
     * @param style
     */
    public RoundParameter(Shell parent) {
        super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        setText("Parametre tlače ...");
    }

    /**
     * Open the dialog.
     *
     * @return the result
     */
    public int open() {
        createContents();
        shell.open();
        shell.layout();
        Display display = getParent().getDisplay();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        return result;
    }

    /**
     * Create contents of the dialog.
     */
    private void createContents() {
        shell = new Shell(getParent(), getStyle());
        shell.setSize(286, 117);
        shell.setText(getText());
        shell.setLayout(new GridLayout(1, false));

        Composite composite = new Composite(shell, SWT.NONE);
        GridData gd_composite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_composite.widthHint = 255;
        gd_composite.heightHint = 38;
        composite.setLayoutData(gd_composite);
        composite.setLayout(new GridLayout(2, false));

        Label lblNewLabel = new Label(composite, SWT.NONE);
        lblNewLabel.setText("Zadaj číslo kola");
        lblNewLabel.setToolTipText("");
        GridData gd_lblNewLabel = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
        gd_lblNewLabel.heightHint = 27;
        lblNewLabel.setLayoutData(gd_lblNewLabel);

        tRound = new Text(composite, SWT.BORDER);
        tRound.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        Composite composite_1 = new Composite(shell, SWT.NONE);
        GridData gd_composite_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_composite_1.widthHint = 234;
        composite_1.setLayoutData(gd_composite_1);
        composite_1.setLayout(new GridLayout(8, false));
        new Label(composite_1, SWT.NONE);
        new Label(composite_1, SWT.NONE);
        new Label(composite_1, SWT.NONE);

        Button btnNewButton = new Button(composite_1, SWT.NONE);
        btnNewButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                try {
                    result = Integer.parseInt(tRound.getText()) - 1;
                    shell.close();
                } catch (Exception ex) {
                    result = -1;
                }


                if (result < 0 || result >= Contest.getContest().getNumRounds()) {
                    result = -1;
                }

            }
        });
        GridData gd_btnNewButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_btnNewButton.widthHint = 64;
        btnNewButton.setLayoutData(gd_btnNewButton);
        btnNewButton.setText("OK");
        new Label(composite_1, SWT.NONE);
        new Label(composite_1, SWT.NONE);
        new Label(composite_1, SWT.NONE);

        Button btnNewButton_1 = new Button(composite_1, SWT.NONE);
        btnNewButton_1.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                shell.close();
            }
        });
        GridData gd_btnNewButton_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_btnNewButton_1.widthHint = 71;
        btnNewButton_1.setLayoutData(gd_btnNewButton_1);
        btnNewButton_1.setText("Zruš");

    }
}
