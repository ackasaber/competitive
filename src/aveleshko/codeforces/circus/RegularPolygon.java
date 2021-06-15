package aveleshko.codeforces.circus;

import static java.lang.Math.*;

/**
 * Regular polygon.
 */
final class RegularPolygon {

	/**
	 * A method of building a regular polygon by three points.
	 */
	public interface Builder {
		/**
		 * Builds a regular polygon by three points.
		 *
		 * @param A the first point
		 * @param B the second point
		 * @param C the third point
		 * @return a regular polygon
		 */
		RegularPolygon build(Point A, Point B, Point C);
	}

	/**
	 * Number of vertices.
	 */
	private final int n;
	/**
	 * Circumcircle radius.
	 */
	private final double r;
	/**
	 * Single sector angle.
	 */
	private final double angle;

	/**
	 * Create a new regular polygon with the given number of vertices and the
	 * circumcircle radius.
	 *
	 * @param n the number of vertices
	 * @param r the circumcircle radius
	 */
	public RegularPolygon(int n, double r) {
		this.n = n;
		this.r = r;
		this.angle = 2 * PI / n;
	}

	/**
	 * Retuns the number of corners of the regular polygon.
	 *
	 * @return the number of corners
	 */
	public int cornerCount() {
		return n;
	}

	/**
	 * Returns the circumcircle radius of the regular polygon.
	 *
	 * @return the circumcircle radius
	 */
	public double radius() {
		return r;
	}

	/**
	 * Returns the single sector angle of the regular polygon (the angle between
	 * radiuses to two consecutive corners).
	 *
	 * @return the sector angle
	 */
	public double sectorAngle() {
		return angle;
	}

	/**
	 * Computes the area of the regular polygon.
	 *
	 * @return regular polygon area
	 */
	public double area() {
		return n * r * r * sin(angle) / 2;
	}
}