package codeforces.beta01.circus;

import static java.lang.Math.*;

/**
 * The first idea for the solution that came to my mind.
 */

enum Solution1 implements RegularPolygon.Builder {
	/**
	 * The single instance of this solution.
	 */
	INSTANCE;

	/**
	 * Precision of angle comparison in radians. The value here is a gamble.
	 */
	private static final double RAD_ACCURACY = 1E-6;
	/**
	 * Upper limit on the number of vertices.
	 */
	private static final int MAX_VERTICES = 100;

	/**
	 * Tries to construct a regular polygon with vertices in the given points and
	 * minimal area.
	 *
	 * The three vertices uniquely define the circumcircle around the hypothetic
	 * regular polygon. Therefore once we build the circumcircle, the circumcircle
	 * radius can be computed. The only other variable that is needed for square
	 * computation is the vertices number. Since the task defines a small limit on
	 * it, we look through all possible vertex numbers. Since the regular polygon
	 * area increases when the vertex number increases, the first fitting polygon
	 * will have the least area.
	 *
	 * If we fix the polygon center and the number of vertices n, then we can
	 * compute polar angles of A, B and C in the circumcircle and see if the
	 * difference between them consists of a whole number of 2 * PI/n radians.
	 *
	 * @param A           a polygon vertex
	 * @param B           a polygon vertex
	 * @param C           a polygon vertex
	 * @param maxVertices a limit on vertex number
	 * @return the necessary polygon
	 * @throws IllegalArgumentException If the polygon doesn't exist or the limit on
	 *                                  vertices was reached.
	 */

	@Override
	public RegularPolygon build(Point A, Point B, Point C) {
		Point center = Point.circleCenter(A, B, C);
		double phiA = center.polarAngle(A);
		double phiBA = center.polarAngle(B) - phiA;
		double phiCA = center.polarAngle(C) - phiA;
		int n = 3;

		while (n <= MAX_VERTICES) {
			double alpha = 2 * PI / n;
			// IEEEremainder is similar to %, but % truncates to zero while
			// IEEEremainder divides so that quotient is the nearest integer.
			// The nearest integer is what we want since we'd rather consider a reminder
			// close to alpha be zero.
			double remB = IEEEremainder(phiBA, alpha);
			double remC = IEEEremainder(phiCA, alpha);

			if (abs(remB) < RAD_ACCURACY && abs(remC) < RAD_ACCURACY) {
				break;
			}

			++n;
		}

		if (n > MAX_VERTICES) {
			throw new IllegalArgumentException("The regular polygon doesn't exist or of too many corners");
		}

		double r = hypot(A.x - center.x, A.y - center.y);
		return new RegularPolygon(n, r);
	}
}
