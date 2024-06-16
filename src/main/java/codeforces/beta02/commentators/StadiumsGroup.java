package codeforces.beta02.commentators;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

/**
 * Widget to enter stadium locations.
 */
public final class StadiumsGroup extends Composite {

	private DoubleInput Ax, Ay, Ar, Bx, By, Br, Cx, Cy, Cr;
	private Group group;

	/**
	 * Creates a new widget to enter stadium locations.
	 *
	 * @param parent the parent composite
	 */
	public StadiumsGroup(Composite parent) {
		super(parent, SWT.NONE);
		group = new Group(this, SWT.NONE);
		group.setText("Studiums");

		var labelA = new Label(group, SWT.NONE);
		labelA.setText("&A:");
		Ax = new DoubleInput(group, SWT.BORDER | SWT.RIGHT);
		Ay = new DoubleInput(group, SWT.BORDER | SWT.RIGHT);
		Ar = new DoubleInput(group, SWT.BORDER | SWT.RIGHT);
		Ax.setValue(0.0);
		Ay.setValue(0.0);
		Ar.setValue(10.0);

		var labelB = new Label(group, SWT.NONE);
		labelB.setText("&B:");
		Bx = new DoubleInput(group, SWT.BORDER | SWT.RIGHT);
		By = new DoubleInput(group, SWT.BORDER | SWT.RIGHT);
		Br = new DoubleInput(group, SWT.BORDER | SWT.RIGHT);
		Bx.setValue(60.0);
		By.setValue(0.0);
		Br.setValue(10.0);

		var labelC = new Label(group, SWT.NONE);
		labelC.setText("&C:");
		Cx = new DoubleInput(group, SWT.BORDER | SWT.RIGHT);
		Cy = new DoubleInput(group, SWT.BORDER | SWT.RIGHT);
		Cr = new DoubleInput(group, SWT.BORDER | SWT.RIGHT);
		Cx.setValue(30.0);
		Cy.setValue(30.0);
		Cr.setValue(10.0);

		var groupLayout = new GridLayout(4, false);
		groupLayout.marginHeight = 5;
		groupLayout.marginWidth = 5;
		group.setLayout(groupLayout);

		var labelAData = new GridData();
		labelAData.grabExcessHorizontalSpace = true;
		labelA.setLayoutData(labelAData);

		var AxData = new GridData();
		AxData.horizontalIndent = 10;
		Ax.setData(AxData);

		var BxData = new GridData();
		BxData.horizontalIndent = 10;
		Bx.setData(BxData);

		var CxData = new GridData();
		CxData.horizontalIndent = 10;
		Cx.setData(CxData);

		addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent event) {
				Rectangle bounds = getBounds();
				// The entirety of the control is occupied by the group.
				group.setBounds(0, 0, bounds.width, bounds.height);
			}
		});
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		checkWidget();
		// The control's size is the group's size.
		return group.computeSize(wHint, hHint, changed);
	}

	/**
	 * Validates the entered stadium location data.
	 *
	 * @return the validation status: true for passed, false for failed
	 */
	public boolean isValid() {
		return Ax.getValue().isPresent() && Ay.getValue().isPresent() && Ar.getValue().isPresent()
		        && Ar.getValue().get() > 0 && Bx.getValue().isPresent() && By.getValue().isPresent()
		        && Br.getValue().isPresent() && Br.getValue().get() > 0 && Cx.getValue().isPresent()
		        && Cy.getValue().isPresent() && Cr.getValue().isPresent() && Cr.getValue().get() > 0;
	}

	/**
	 * Returns the entered stadium locations data.
	 * <p>
	 * Check validity of the data with {@code isValid} first.
	 * </p>
	 *
	 * @return the stadium locations data
	 */
	public StadiumsData getStadiumsData() {
		var s1 = new Stadium(Ax.getValue().get(), Ay.getValue().get(), Ar.getValue().get());
		var s2 = new Stadium(Bx.getValue().get(), By.getValue().get(), Br.getValue().get());
		var s3 = new Stadium(Cx.getValue().get(), Cy.getValue().get(), Cr.getValue().get());
		return new StadiumsData(s1, s2, s3);
	}
}
