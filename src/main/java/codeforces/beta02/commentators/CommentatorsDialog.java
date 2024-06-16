package codeforces.beta02.commentators;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

/**
 * Dialog to enter Commentators task inputs for further visualization.
 */
public final class CommentatorsDialog {

	private Shell shell;
	private StadiumsGroup points;
	private OptionsGroup options;

	/**
	 * Creates the dialog, but doesn't open it.
	 *
	 * @param display the display to use the dialog shell for
	 */
	public CommentatorsDialog(Display display) {
		shell = new Shell(display, SWT.DIALOG_TRIM);
		shell.setText("Commentators task visualization");
		points = new StadiumsGroup(shell);
		options = new OptionsGroup(shell);
		var button = new Button(shell, SWT.PUSH);
		button.setText("Visualize");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				visualize();
			}
		});

		var layout = new GridLayout();
		layout.marginHeight = layout.marginWidth = 10;
		layout.verticalSpacing = 7;
		shell.setLayout(layout);
		var pointsData = new GridData();
		pointsData.horizontalAlignment = SWT.FILL;
		points.setLayoutData(pointsData);
		var optionsData = new GridData();
		optionsData.horizontalAlignment = SWT.FILL;
		options.setLayoutData(optionsData);
		var buttonData = new GridData();
		buttonData.horizontalAlignment = SWT.END;
		button.setLayoutData(buttonData);

		shell.pack();
	}

	/**
	 * Returns true if the dialog shell has been disposed of, false otherwise.
	 *
	 * @return the dialog shell disposed status
	 */
	public boolean isDisposed() {
		return shell.isDisposed();
	}

	/**
	 * Displays the dialog shell on the screen.
	 */
	public void open() {
		shell.open();
	}

	/**
	 * Opens the dialog shell and runs the main loop.
	 *
	 * @param args the command line arguments (unused)
	 */
	public static void main(String[] args) {
		var display = new Display();
		var shell = new CommentatorsDialog(display);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Opens the visualization shell for the entered inputs.
	 */
	private void visualize() {
		if (!options.isValid()) {
			var dialog = new MessageBox(shell, SWT.ICON_ERROR);
			dialog.setText("Error");
			dialog.setMessage("Invalid visualization options.");
			dialog.open();
			return;
		}

		if (!points.isValid()) {
			var dialog = new MessageBox(shell, SWT.ICON_ERROR);
			dialog.setText("Error");
			dialog.setMessage("Invalid stadium locations data.");
			dialog.open();
			return;
		}

		var viewShell = new Shell(shell, SWT.DIALOG_TRIM);
		viewShell.setText("Commentators positions");
		new CommentatorsView(viewShell, options.getOptions(), points.getStadiumsData());
		var layout = new FillLayout();
		viewShell.setLayout(layout);
		viewShell.setSize(800, 800);
		viewShell.open();
	}
}
