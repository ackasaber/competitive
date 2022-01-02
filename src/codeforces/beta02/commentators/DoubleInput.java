package codeforces.beta02.commentators;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Optional;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

/**
 * The floating point number input field.
 * <p>
 * Note that although this class is a subclass of <code>Composite</code>, it
 * does not make sense to add children to it, or set a layout on it.
 * </p>
 * <p>
 * <strong>TODO</strong> Improve input of negative number by allowing prefixes of valid inputs.
 * </p>
 * <dl>
 * <dt><b>Styles:</b></dt>
 * <dd>READ_ONLY</dd>
 * <dt><b>Events:</b></dt>
 * <dd>-</dd>
 * </dl>
 */
public final class DoubleInput extends Composite {
	/**
	 * The underlying text widget.
	 */
	private Text text;
	/**
	 * The number format for the input formatting and parsing.
	 */
	private NumberFormat numberFormat;
	/**
	 * Default width.
	 */
	private int defaultWidth;

	/**
	 * Constructs a new floating point input field given its parent and a style value
	 * describing its behavior and appearance.
	 * <p>
	 * The style value is either one of the style constants defined in class
	 * <code>SWT</code> which is applicable to instances of this class, or must be
	 * built by <em>bitwise OR</em>'ing together (that is, using the
	 * <code>int</code> "|" operator) two or more of those <code>SWT</code> style
	 * constants. The class description lists the style constants that are
	 * applicable to the class. Style bits are also inherited from superclasses.
	 * </p>
	 *
	 * @param parent a composite control which will be the parent of the new
	 *               instance (cannot be null)
	 * @param style  the style of control to construct
	 *
	 * @exception IllegalArgumentException
	 *                                     <ul>
	 *                                     <li>ERROR_NULL_ARGUMENT - if the parent
	 *                                     is null</li>
	 *                                     </ul>
	 * @exception SWTException
	 *                                     <ul>
	 *                                     <li>ERROR_THREAD_INVALID_ACCESS - if not
	 *                                     called from the thread that created the
	 *                                     parent</li>
	 *                                     <li>ERROR_INVALID_SUBCLASS - if this
	 *                                     class is not an allowed subclass</li>
	 *                                     </ul>
	 *
	 * @see SWT#READ_ONLY
	 * @see Widget#checkSubclass
	 * @see Widget#getStyle
	 */
	public DoubleInput(Composite parent, int style) {
		super(parent, SWT.NONE);
		text = new Text(this,
				SWT.SINGLE | (style & ~SWT.MULTI & ~SWT.PASSWORD & ~SWT.WRAP & ~SWT.H_SCROLL & ~SWT.V_SCROLL));
		defaultWidth = numberWidth(text, 12);
		text.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent event) {
				DoubleInput.this.verifyText(event);
			}
		});
		setValue(Optional.empty());

		addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent event) {
				DoubleInput.this.controlResized(event);
			}
		});

		// This is the default JVM integer format which
		// by default reflects the user locale.
		numberFormat = NumberFormat.getInstance();
	}

	/**
	 * Returns the widget contents.
	 *
	 * @return integer value stored in the widget
	 */
	public Optional<Double> getValue() {
		checkWidget();

		var content = text.getText();

		if (content.isEmpty()) {
			return Optional.empty();
		}

		Number value;

		try {
			value = numberFormat.parse(content);
		} catch (ParseException e) {
			return Optional.empty();
		}

		return Optional.of(value.doubleValue());
	}

	/**
	 * Sets the widget contents to a new value.
	 * <p>
	 * The default JVM integer numeric format will be used to format the number.
	 * </p>
	 *
	 * @param n new integer value to be stored in the widget
	 */
	public void setValue(Optional<Double> n) {
		checkWidget();

		if (n.isEmpty()) {
			text.setText("");
		} else {
			var nText = numberFormat.format(n.get());
			text.setText(nText);
			text.setSelection(nText.length(), nText.length());
		}
	}

	public void setValue(double n) {
		setValue(Optional.of(n));
	}

	/**
	 * Calculate the width in pixels that a number with the given amount of digits
	 * would occupy when drawn by the given <code>Drawable</code>.
	 *
	 * @param drawable <code>Drawable</code> to use the font from
	 * @param digits   amount of digits in the number
	 * @return width of the number in pixels
	 */
	private static int numberWidth(Drawable drawable, int digits) {
		var gc = new GC(drawable);
		var width = gc.stringExtent("0").x * digits;
		gc.dispose();
		return width;
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		checkWidget();
		// The control's size is the text input's size.
		var size = text.computeSize(wHint, hHint, changed);

		if (wHint == SWT.DEFAULT) {
			size.x = defaultWidth;
		}

		return size;
	}

	/**
	 * Handles resizes of the control.
	 *
	 * @param event event for a resize
	 * @see ControlListener
	 * @see ControlEvent
	 */
	private void controlResized(ControlEvent event) {
		Rectangle bounds = getBounds();
		// The entirety of the control is occupied by the text input.
		text.setBounds(0, 0, bounds.width, bounds.height);
	}

	/**
	 * Forbids illegal input. The default JVM numeric format is used to check for
	 * illegal input. Verification is done not only for user input but also for
	 * programmatic changes via <code>setValue</code>.
	 *
	 * <p>
	 * Illegal input is accompanied with a beep.
	 * </p>
	 *
	 * @param event info about the text to be inserted
	 * @see VerifyListener
	 * @see VerifyEvent
	 */
	private void verifyText(VerifyEvent event) {
		String oldText = text.getText();
		String newText = oldText.substring(0, event.start) + event.text + oldText.substring(event.end);

		if (!newText.isEmpty()) {
			// Java standard NumberFormat parsing is not easy to manage.
			ParsePosition position = new ParsePosition(0);
			numberFormat.parse(newText, position);

			if (position.getErrorIndex() != -1 || position.getIndex() != newText.length()) {
				event.doit = false;
				getDisplay().beep();
			}
		}
	}
}
