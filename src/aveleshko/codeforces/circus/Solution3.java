package aveleshko.codeforces.circus;

import static java.lang.Math.*;

/**
 * The same as solution 2, but a different angle computation formula.
 */
enum Solution3 implements RegularPolygon.Builder {
	INSTANCE;

	/**
	 * The minimum sector angle to consider according to the task description
	 * (doubled).
	 */
	private static final double MIN_ANGLE = PI / 100;

	/**
	 * Computes angle between AB and BC. The formula of Prof. W. Kahan,
	 * "Miscalculating Area and Angles of a Needle-like Triangle".
	 *
	 * @param A a point
	 * @param B a point
	 * @param C a point
	 * @return the angle ABC in radians
	 */
	private static double angle(Point A, Point B, Point C) {
		double a = hypot(A.x - B.x, A.y - B.y);
		double b = hypot(C.x - B.x, C.y - B.y);
		double c = hypot(A.x - C.x, A.y - C.y);

		if (a < b) {
			double t = a;
			a = b;
			b = t;
		}

		// Now a >= b.
		double mu;

		if (b >= c) {
			mu = c - (a - b);
		} else {
			mu = b - (a - c);
		}

		double angle = 2 * Math.atan(Math.sqrt(((a - b) + c) * mu / ((a + (b + c)) * ((a - c) + b))));
		return angle;
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
