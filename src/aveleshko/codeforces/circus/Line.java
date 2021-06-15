package aveleshko.codeforces.circus;

/**
 * Line on a plane.
 *
 * <p>
 * The line is stored parametrically as coefficients a, b, c, d in <br>
 * x = at + b, y = ct + d, t in R.
 * </p>
 */
final class Line {
	/**
	 * Coefficients of the parametric equation.
	 */
	private final double a, b, c, d;

	/**
	 * Constructs a new line with the given parametric coefficients. No checks are
	 * made against degenerate cases.
	 */
	private Line(double a, double b, double c, double d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	@Override
	public String toString() {
		return "x = " + a + " t + " + b + ", y = " + c + " t + " + d;
	}

	/**
	 * Constructs a line equidistant from the given two points.
	 *
	 * @param u a point
	 * @param v another point
	 * @return normal that goes throw the middle
	 */
	public static Line middleNormal(Point u, Point v) {
		// "Rotate" the line uv by PI/2 and fix a point in the middle.
		// (The uv line would be defined as
		// x = (v.x - u.x)t + u.x, y = (v.y - u.y)t + u.y, t in R.)
		return new Line(u.y - v.y, (u.x + v.x) / 2, v.x - u.x, (u.y + v.y) / 2);
	}

	/**
	 * Find the intersection point with another line. It's expected that it exists
	 * and the lines are not nearly parallel.
	 *
	 * @param other another line
	 * @return the intersection point
	 */
	public Point cross(Line other) {
		// Solve the linear system of two parametric equations.
		// Note that the line parameter variables are distinct.
		double D = other.a * c - a * other.c;
		double d1 = other.a * (other.d - d) - other.c * (other.b - b);
		// The current line parameter value for the intersection point.
		double t = d1 / D;
		double x = a * t + b;
		double y = c * t + d;
		return new Point(x, y);
	}
}