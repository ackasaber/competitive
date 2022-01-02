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
 * Widget and enter visualization options.
 */
public final class OptionsGroup extends Composite {
	private DoubleInput Xmin, Xmax, Ymin, Ymax;
	private Group group;

	/**
	 * Create the visualization options widget.
	 *
	 * @param parent the parent composite
	 */
	public OptionsGroup(Composite parent) {
		super(parent, SWT.NONE);
		group = new Group(this, SWT.NONE);
		group.setText("Options");

		var labelX = new Label(group, SWT.NONE);
		labelX.setText("&X range:");
		Xmin = new DoubleInput(group, SWT.BORDER | SWT.RIGHT);
		Xmax = new DoubleInput(group, SWT.BORDER | SWT.RIGHT);
		Xmin.setValue(-5.0);
		Xmax.setValue(65.0);

		var labelY = new Label(group, SWT.NONE);
		labelY.setText("&Y range:");
		Ymin = new DoubleInput(group, SWT.BORDER | SWT.RIGHT);
		Ymax = new DoubleInput(group, SWT.BORDER | SWT.RIGHT);
		Ymin.setValue(-5.0);
		Ymax.setValue(35.0);

		var groupLayout = new GridLayout(3, false);
		groupLayout.marginHeight = 5;
		groupLayout.marginWidth = 5;
		group.setLayout(groupLayout);

		var labelXData = new GridData();
		labelXData.grabExcessHorizontalSpace = true;
		labelX.setLayoutData(labelXData);

		var XminData = new GridData();
		XminData.horizontalIndent = 10;
		Xmin.setLayoutData(XminData);

		var YminData = new GridData();
		YminData.horizontalIndent = 10;
		Ymin.setLayoutData(YminData);

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
	 * Validates the inputs.
	 *
	 * @return the options validation status
	 */
	public boolean isValid() {
		return Xmin.getValue().isPresent() && Xmax.getValue().isPresent() && Ymin.getValue().isPresent()
		        && Ymax.getValue().isPresent() && Xmin.getValue().get() < Xmax.getValue().get()
		        && Ymin.getValue().get() < Ymax.getValue().get();
	}

	/**
	 * Returns the currently entered visualization options.
	 * <p>
	 * Check the inputs with {@code isValid} first.
	 * </p>
	 *
	 * @return the visualization options
	 */
	public Options getOptions() {
		var builder = new Options.Builder();
		builder.withXmin(Xmin.getValue().get().doubleValue());
		builder.withXmax(Xmax.getValue().get().doubleValue());
		builder.withYmin(Ymin.getValue().get().doubleValue());
		builder.withYmax(Ymax.getValue().get().doubleValue());
		return builder.build();
	}
}
