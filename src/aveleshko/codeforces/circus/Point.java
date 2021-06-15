package aveleshko.codeforces.circus;
import static java.lang.Math.atan2;

/**
 * Point on Cartesian plane.
 */
final class Point {
	/**
	 * X coordinate.
	 */
	public final double x;
	/**
	 * Y coordinate.
	 */
	public final double y;

	/**
	 * Create a new point with the given Cartesian coordinates.
	 *
	 * @param x
	 * @param y
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "[" + x + "; " + y + "]";
	}

	/**
	 * Computes the angle between Ox axis and OA, where point A is given and O is
	 * the current point.
	 *
	 * @param point A coordinates
	 * @return the angle in radians
	 */
	public double polarAngle(Point a) {
		return atan2(a.y - y, a.x - x);
	}

	/**
	 * Computes the center of the circle with the three given points on it.
	 *
	 * No handling for special cases is made: coinciding points, points on the same
	 * or nearly same line.
	 *
	 * @param a a point on the circle
	 * @param b a point on the circle
	 * @param c a point on the circle
	 * @return the circle center
	 */
	public static Point circleCenter(Point a, Point b, Point c) {
		Line abNormal = Line.middleNormal(a, b);
		Line bcNormal = Line.middleNormal(b, c);
		return abNormal.cross(bcNormal);
	}

}