package codeforces.beta02;

// C. Commentators

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

import static java.lang.Math.*;

/**
 * The solution calculated via a formula from Commentators.odf.
 */
public final class Commentators {

	private final static class Stadium {
		/**
		 * Stadium center x coordinate.
		 */
		public final int x;
		/**
		 * Stadium center y coordinate.
		 */
		public final int y;
		/**
		 * Stadium radius.
		 */
		public final int r;

		public Stadium(int x, int y, int r) {
			this.x = x;
			this.y = y;
			this.r = r;
		}
	}

	private static Stadium readStadium(Scanner scanner) {
		int x = scanner.nextInt();
		int y = scanner.nextInt();
		int r = scanner.nextInt();
		return new Stadium(x, y, r);
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.ROOT);
		var scanner = new Scanner(System.in, StandardCharsets.US_ASCII);
		var s1 = readStadium(scanner);
		var s2 = readStadium(scanner);
		var s3 = readStadium(scanner);
		var answer = solve(s1, s2, s3);
		answer.ifPresent(p -> {
			System.out.format("%.5f %.5f%n", p.x, p.y);
		});
	}

	private static class Point {
		public final double x;
		public final double y;

		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}

	/**
	 * Solves the problem for the given stadium locations.
	 *
	 * @param s1 a stadium
	 * @param s2 a stadium
	 * @param s3 a stadium
	 * @return the sought for point, if it exists
	 */
	private static Optional<Point> solve(Stadium s1, Stadium s2, Stadium s3) {
		var a0 = solveShifted(s2.x - s1.x, s2.y - s1.y, s3.x - s1.x, s3.y - s1.y, s1.r, s2.r, s3.r);
		return a0.map(p -> new Point(s1.x + p.x, s1.y + p.y));
	}

	/**
	 * Solves the modified problem with the first stadium always at (0, 0).
	 *
	 * @param x1 the second stadium x coordinate
	 * @param y1 the second stadium y coordinate
	 * @param x2 the third stadium x coordinate
	 * @param y2 the third stadium y coordinate
	 * @param r1 the first stadium radius
	 * @param r2 the second stadium radius
	 * @param r3 the third stadium radius
	 * @return the sought for point, if it exists
	 */
	private static Optional<Point> solveShifted(int x1, int y1, int x2, int y2, int r1, int r2, int r3) {
		var sqrR12 = big(r1 * r1 - r2 * r2);
		var sqrR13 = big(r1 * r1 - r3 * r3);
		var L1 = big(x1 * x1 + y1 * y1);
		var L2 = big(x2 * x2 + y2 * y2);

		var Pi = times(2 * r1 * r1, x1 * y2 - y1 * x2);
		var p = times(y2, sqrR12).subtract(times(y1, sqrR13));
		var q = times(r1 * r1, times(y2, L1).subtract(times(y1, L2)));
		var r = times(x1, sqrR13).subtract(times(x2, sqrR12));
		var s = times(r1 * r1, times(x1, L2).subtract(times(x2, L1)));

		// coefficients for a(d2)^2 - 2b d2 + c = 0
		var a = sqr(p).add(sqr(r));
		var b = sqr(Pi).shiftRight(1)
		          .subtract(p.multiply(q))
		          .subtract(r.multiply(s));
		var c = sqr(q).add(sqr(s));

		// linear case
		if (a.signum() == 0) {
			double Q = q.doubleValue() / Pi.doubleValue();
			double S = s.doubleValue() / Pi.doubleValue();
			return Optional.of(new Point(Q, S));
		}

		// quadratic case
		var D = sqr(b).subtract(a.multiply(c));

		if (D.signum() < 0)
			return Optional.empty();

		double num = b.doubleValue() + b.signum() * sqrt(D.doubleValue());
		double delta1 = num / a.doubleValue();
		double delta2 = c.doubleValue() / num;
		double d2 = delta1;

		if (delta1 < 0 || delta2 > 0 && delta2 < delta1)
			d2 = delta2;

		if (d2 > 0) {
			double P = p.doubleValue() / Pi.doubleValue();
			double Q = q.doubleValue() / Pi.doubleValue();
			double R = r.doubleValue() / Pi.doubleValue();
			double S = s.doubleValue() / Pi.doubleValue();
			return Optional.of(new Point(P * d2 + Q, R * d2 + S));
		}

		return Optional.empty();
	}

	private static BigInteger sqr(BigInteger x) {
		return x.multiply(x);
	}

	private static BigInteger big(int x) {
		return BigInteger.valueOf(x);
	}

	private static BigInteger times(int x, int y) {
		return BigInteger.valueOf(x).multiply(BigInteger.valueOf(y));
	}

	private static BigInteger times(int x, BigInteger y) {
		return BigInteger.valueOf(x).multiply(y);
	}
}
