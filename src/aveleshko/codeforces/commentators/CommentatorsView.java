package aveleshko.codeforces.commentators;

import static java.lang.Math.*;

import java.util.Objects;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

/**
 * The visualization dialog for Commentators problem.
 */
public final class CommentatorsView extends Canvas {
	private final Options options;
	private final StadiumsData points;

	/**
	 * Constructs the visualization dialog.
	 *
	 * @param parent  the parent composite
	 * @param options the visualization options: coordinate limits
	 * @param points  the stadium location data
	 */
	public CommentatorsView(Composite parent, Options options, StadiumsData points) {
		super(parent, SWT.NONE);
		this.options = Objects.requireNonNull(options);
		this.points = Objects.requireNonNull(points);

		addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent event) {
				CommentatorsView.this.paintControl(event);
			}
		});
	}

	/**
	 * Paints the view.
	 *
	 * @param event the incoming paint event
	 */
	private void paintControl(PaintEvent event) {
		var area = getClientArea();
		var values = new double[area.width + 1][area.height + 1];

		for (int i = 0; i <= area.width; ++i) {
			for (int j = 0; j <= area.height; ++j) {
				double x = pixelToWorldX(area, i);
				double y = pixelToWorldY(area, j);
				values[i][j] = compute(x, y);
			}
		}

		double min = values[0][0];
		double max = values[0][0];

		for (int i = 0; i <= area.width; ++i) {
			for (int j = 0; j <= area.height; ++j) {
				if (values[i][j] < min) {
					min = values[i][j];
				}

				if (values[i][j] > max) {
					max = values[i][j];
				}
			}
		}

		var rgbPalette = new PaletteData(0x0000FF, 0x00FF00, 0xFF0000);
		var imageData = new ImageData(area.width + 1, area.height + 1, 24, rgbPalette);

		for (int i = 0; i <= area.width; ++i) {
			for (int j = 0; j <= area.height; ++j) {
				double v = values[i][j];
				int tint = (int) (255 * (v - min) / (max - min));
				int color = ~(tint << 16 | tint << 8 | tint);
				imageData.setPixel(area.x + i, area.y + area.height - j, color);
			}
		}

		var gc = event.gc;
		var image = new Image(gc.getDevice(), imageData);
		gc.drawImage(image, area.x, area.y);
		Color markColor = new Color(gc.getDevice(), new RGB(100, 100, 255));
		gc.setBackground(markColor);

		for (int i = 1; i <= 3; ++i) {
			int stadiumx = worldToPixelX(area, points.stadium(i).x());
			int stadiumy = worldToPixelY(area, points.stadium(i).y());
			gc.fillOval(stadiumx - 2, stadiumy - 2, 5, 5);
		}
	}

	/**
	 * Translates the pixel x coordinate from the range 0 .. area.width to world
	 * coordinates from the range xmin .. xmax.
	 *
	 * @param area the source image area
	 * @param x    the pixel x coordinate
	 * @return the world x coordinate
	 */
	private double pixelToWorldX(Rectangle area, int x) {
		return options.xmin() + x * (options.xmax() - options.xmin()) / area.width;
	}

	/**
	 * Translates the pixel y coordinate from the range 0 .. area.height to world
	 * coordinates from the range ymin .. ymax.
	 *
	 * @param area the source image area
	 * @param y    the pixel y coordinate
	 * @return the world y coordinate
	 */
	private double pixelToWorldY(Rectangle area, int y) {
		return options.ymin() + y * (options.ymax() - options.ymin()) / area.height;
	}

	/**
	 * Translates the world x coordinate from the range xmin .. xmax to the image
	 * pixel x coordinate from the range 0 .. area.width.
	 *
	 * @param area the target image area
	 * @param x    the world x coordinate
	 * @return the nearest pixel x coordinate
	 */
	private int worldToPixelX(Rectangle area, double x) {
		return (int) (area.x + (x - options.xmin()) * area.width / (options.xmax() - options.xmin()));
	}

	/**
	 * Translates the world y coordinate from the range ymin .. ymax to the image
	 * pixel y coordinate from the range 0 .. area.height.
	 *
	 * @param area the target image area
	 * @param y    the world y coordinate
	 * @return the nearest pixel y coordinate
	 */
	private int worldToPixelY(Rectangle area, double y) {
		return (int) (area.y + area.height - (y - options.ymin()) * area.height / (options.ymax() - options.ymin()));
	}

	/**
	 * Computes the visualized scalar field.
	 *
	 * @param x the world x coordinate
	 * @param y the world y coordinate
	 * @return the measure of difference between view angles
	 */
	private double compute(double x, double y) {
		double Ad = hypot(points.stadium(1).x() - x, points.stadium(1).y() - y);
		double Bd = hypot(points.stadium(2).x() - x, points.stadium(2).y() - y);
		double Cd = hypot(points.stadium(3).x() - x, points.stadium(3).y() - y);
		double phiA = atan2(points.stadium(1).r(), Ad);
		double phiB = atan2(points.stadium(2).r(), Bd);
		double phiC = atan2(points.stadium(3).r(), Cd);
		return hypot(phiA - phiB, phiB - phiC);
	}
}
