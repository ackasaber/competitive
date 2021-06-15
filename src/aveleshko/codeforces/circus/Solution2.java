package aveleshko.codeforces.circus;

import static java.lang.Math.*;

/**
 * The solution from discussion, ideas of
 * <a href="https://codeforces.com/profile/Sergey.Bankevich">Sergey
 * Bankevich</a> and
 * <a href="https://codeforces.com/profile/Nerevar">Nerevar</a>.
 *
 * <p>
 * We don't have to build the circumcircle center. The regular polygon area
 * formula requires only the number of corners and the circumcircle radius.
 * </p>
 *
 * <p>
 * The circumcircle radius is found from the law of sines:
 * </p>
 *
 * <p>
 * <i>R</i> = <i>a</i> / 2 sin &alpha;,
 * </p>
 *
 * <p>
 * where <br>
 * <i>R</i> is the triangle's circumcircle radius, <br>
 * <i>a</i> is the triangle side length and <br>
 * &alpha; is the angle opposite to that side.
 * </p>
 *
 * <p>
 * The number of vertices is a bit trickier. First of all, the circle sector
 * angle is twice the corresponding chord angle. This means that once we compute
 * angles based on the given three points, we know the sector angles in the
 * circumcircle.
 * </p>
 *
 * <p>
 * Since sectors of the regular polygon divide the circle evenly, the sector
 * angles between any polygon vertices in the circumcircle should be multipliers
 * of the sector angle between consecutive vertices. We want to minimize the
 * number of vertices of the resulting regular polygon, therefore we seek for
 * the largest such divisor. This formulation means that we're looking for the
 * greatest common divisor of sector angles defined by the given points. It can
 * be found via Euclid's algorithm.
 * </p>
 */

enum Solution2 implements RegularPolygon.Builder {
	/**
	 * The single instance of the solution.
	 */
	INSTANCE;

	/**
	 * The minimum sector angle to consider according to the task description
	 * (doubled).
	 */
	private static final double MIN_ANGLE = PI / 100;

	/**
	 * Computes angle between AB and BC. This particular implementation is not
	 * particularly accurate.
	 *
	 * @param A a point
	 * @param B a point
	 * @param C a point
	 * @return the angle ABC in radians
	 */
	private static double angle(Point A, Point B, Point C) {
		double rotation = atan2(A.y - B.y, A.x - B.x) - atan2(C.y - B.y, C.x - B.x);
		double alpha = abs(rotation);

		if (alpha > PI) {
			alpha = 2 * PI - alpha;
		}

		return alpha;
	}

	/**
	 * Euclid's algorithm for floating point numbers.
	 *
	 * <p>
	 * Since the task promises that there will be no regular polygons with more than
	 * 100 corners, we stop at angles no lesser than 2 &pi; / 100. (Since we do
	 * angle computations in halved angles, it's &pi; / 100.)
	 * </p>
	 *
	 * @param a first number, > 0
	 * @param b second number, > 0
	 * @return the greatest common divisor
	 */
	private static double gcd(double a, double b) {
		double r = a % b;

		while (r > MIN_ANGLE) {
			a = b;
			b = r;
			r = a % b;
		}

		return b;
	}

	/**
	 * Builds the minimal regular polygon with vertices in the given points.
	 */
	@Override
	public RegularPolygon build(Point A, Point B, Point C) {
		// First find two angles, say, ABC and BCA.
		double alpha = angle(A, B, C);
		double beta = angle(B, C, A);
		// The corresponding sector angles are twice ABC and BCA.
		// GCD of the double angles is double GCD of the angles, so we just work in
		// halved angles.
		// phi thus is a half sector angle between consecutive vertices.
		double phi = gcd(gcd(alpha, PI), beta);
		int n = (int) round(PI / phi);
		double AC = hypot(C.x - A.x, C.y - A.y);
		double r = AC / (2 * sin(alpha));
		return new RegularPolygon(n, r);
	}

}
