package codeforces.beta01;

// C. Ancient Berlandian circus

import static java.lang.Math.PI;
import static java.lang.Math.hypot;
import static java.lang.Math.round;
import static java.lang.Math.sin;
import static java.nio.charset.StandardCharsets.US_ASCII;

import java.util.Locale;
import java.util.Scanner;

/**
 * The solution from discussion, ideas of <a href="https://codeforces.com/profile/Sergey.Bankevich">
 * Sergey Bankevich</a> and <a href="https://codeforces.com/profile/Nerevar">Nerevar</a>.
 *
 * <p> We don't have to build the circumcircle center. The regular polygon area formula requires
 * only the number of corners and the circumcircle radius. </p>
 *
 * <p>The circumcircle radius is found from the law of sines:</p>
 * <p><i>R</i> = <i>a</i> / 2 sin &alpha;,</p>
 * <p>where <br>
 * <i>R</i> is the triangle's circumcircle radius, <br>
 * <i>a</i> is the triangle side length and <br>
 * &alpha; is the angle opposite to that side.</p>
 *
 * <p>The number of vertices is a bit trickier. First of all, the circle sector angle is twice the
 * corresponding chord angle. This means that once we compute angles based on the given three
 * points, we know the sector angles in the circumcircle.</p>
 *
 * <p>Since sectors of the regular polygon divide the circle evenly, the sector angles between any
 * polygon vertices in the circumcircle should be multipliers of the sector angle between
 * consecutive vertices. We want to minimize the number of vertices of the resulting regular
 * polygon, therefore we seek for the largest such divisor. This formulation means that we're
 * looking for the greatest common divisor of sector angles defined by the given points. It can be
 * found via Euclid's algorithm.</p>
 */

public final class Circus {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in, US_ASCII);
		scanner.useLocale(Locale.ROOT);
		Point A = nextPoint(scanner);
		Point B = nextPoint(scanner);
		Point C = nextPoint(scanner);
		double area = minRegularPolygonArea(A, B, C);
		System.out.println(area);
	}

	private static Point nextPoint(Scanner scanner) {
		double x = scanner.nextDouble();
		double y = scanner.nextDouble();
		return new Point(x, y);
	}

	/**
	 * The minimum sector angle to consider according to the task description (doubled).
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

		if (b >= c)
			mu = c - (a - b);
		else
			mu = b - (a - c);

		// parentheses are important
        return 2 * Math.atan(Math.sqrt(((a - b) + c) * mu / ((a + (b + c)) * ((a - c) + b))));
	}

	/**
	 * Euclid's algorithm for floating point numbers.
	 *
	 * <p> Since the task promises that there will be no regular polygons with more than 100
	 * corners, we stop at angles no lesser than 2 &pi; / 100.
	 * (Since we do angle computations in halved angles, it's &pi; / 100.)</p>
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

	private static double minRegularPolygonArea(Point A, Point B, Point C) {
		// First find two angles, say, ABC and BCA.
		double alpha = angle(A, B, C);
		double beta = angle(B, C, A);
		// The corresponding sector angles are twice ABC and BCA.
		// GCD of the double angles is double GCD of the angles, so we just work in halved angles.
		// phi thus is a half sector angle between consecutive vertices.
		double phi = gcd(gcd(alpha, PI), beta);
		int n = (int) round(PI / phi);
		double AC = hypot(C.x - A.x, C.y - A.y);
		double r = AC / (2 * sin(alpha));
        return n * r * r * sin(2 * PI / n) / 2;
	}

	record Point(double x, double y) {}
}
