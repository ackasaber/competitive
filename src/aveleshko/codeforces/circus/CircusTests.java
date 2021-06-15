package aveleshko.codeforces.circus;

import static java.lang.Math.*;
import static java.lang.System.out;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Test various solutions of the minimal regular polygon on three points
 * problem.
 */
public final class CircusTests {
	/**
	 * Range of coordinate values for random testing.
	 */
	private static final double COORD_RANGE = 1000.0;

	/**
	 * Number of random inputs.
	 */
	private static final double TRY_COUNT = 10_000;

	/**
	 * Maximum number of regular polygon corners for testing.
	 */
	private static final int MAX_CORNERS = 100;

	/**
	 * Maximum allowable difference for radius comparison.
	 */
	private static final double RADIUS_ACCURACY = 10E-7;

	/**
	 * Tested solution.
	 */
	private final RegularPolygon.Builder solution;

	/**
	 * Solution description.
	 */
	private final String description;

	/**
	 * Table of primes. In order to simplify testing, only regular polygons with the
	 * prime number of corners are tested.
	 */
	private final int[] primes;

	/**
	 * Maximum radius error of the solution during the testing.
	 */
	private double maxRError;

	/**
	 * Initialize the testing framework for the given solution.
	 *
	 * @param solution    the solution
	 * @param description a short human-readable solution description
	 * @param primes      pre-computed table of possible corner counts
	 */
	public CircusTests(RegularPolygon.Builder solution, String description, int[] primes) {
		this.solution = solution;
		this.description = description;
		this.primes = primes.clone();
	}

	/**
	 * Run tests for solutions.
	 *
	 * @param args command line arguments (not used)
	 */
	public static void main(String[] args) {
		var primes = generatePrimes(MAX_CORNERS);
		var test2 = new CircusTests(Solution2.INSTANCE, "Discussion solution", primes);
		test2.run();
		var test1 = new CircusTests(Solution1.INSTANCE, "My solution", primes);
		test1.run();
		var test3 = new CircusTests(Solution3.INSTANCE, "Discussion solution + Kahan's formula", primes);
		test3.run();

		out.println("Testing finished");
	}

	/**
	 * Run tests for a particular solution.
	 */
	private void run() {
		out.println(description);
		long startTime = System.currentTimeMillis();

		out.println("Select points...");
		testPoints(new Point(0.0035, 1_000_000.0), 0.001, 3, 0, 1, 2);
		testPoints(new Point(0.1E-6, 1000.0), 0.001, 89, 73, 78, 80);
		out.println("Problematic test...");
		Point A = new Point(129.400249, -44.695226);
		Point B = new Point(122.278798, -53.696996);
		Point C = new Point(44.828427, -83.507917);
		RegularPolygon poly = solution.build(A, B, C);

		if (poly.cornerCount() != 100) {
			out.println("Failed a test with 100 corners");
		}

		out.println("All possible points for select circles...");
		testCircle(0.0, 0.0, 5.0);
		testCircle(-1.0, -56.640008, 5.0);
		testCircle(0.1E-6, 1_000.0, 0.001);

		out.println(TRY_COUNT + " random inputs...");
		var random = ThreadLocalRandom.current();

		for (int i = 0; i < TRY_COUNT; ++i) {
			double x = random.nextDouble(-COORD_RANGE, +COORD_RANGE);
			double y = random.nextDouble(-COORD_RANGE, +COORD_RANGE);
			double r = random.nextDouble(2 * COORD_RANGE);

			int n = primes[random.nextInt(primes.length)];
			int n1 = random.nextInt(n - 2);
			int n2 = random.nextInt(n1 + 1, n - 1);
			int n3 = random.nextInt(n2 + 1, n);
			testPoints(new Point(x, y), r, n, n1, n2, n3);
		}

		long finishTime = System.currentTimeMillis();
		out.println("Done, max r error = " + maxRError + " in " + (finishTime - startTime) + " ms");
	}

	/**
	 * Test many regular polygons in the given circle.
	 *
	 * @param x the x-coordinate of the circle center
	 * @param y the y-coordinate of the circle center
	 * @param r the circle radius
	 */
	private void testCircle(double x, double y, double r) {
		Point center = new Point(x, y);

		for (int n : primes) {
			for (int n1 = 0; n1 < n - 2; ++n1) {
				for (int n2 = n1 + 1; n2 < n - 1; ++n2) {
					for (int n3 = n2 + 1; n3 < n; ++n3) {
						testPoints(center, r, n, n1, n2, n3);
					}
				}
			}
		}
	}

	/**
	 * Test a single input of the solution.
	 *
	 * @param center the circumcircle center
	 * @param r      the circumcircle radius
	 * @param n      the number of polygon corners
	 * @param n1     an index of the corner, < n
	 * @param n2     an index of the corner, < n
	 * @param n3     an index of the corner, < n
	 */
	private void testPoints(Point center, double r, int n, int n1, int n2, int n3) {
		RegularPoly poly = new RegularPoly(n, r, center);
		Point A = poly.point(n1);
		Point B = poly.point(n2);
		Point C = poly.point(n3);

		RegularPolygon result = solution.build(A, B, C);

		int guessN = result.cornerCount();
		double guessR = result.radius();
		double rError = abs(guessR - r);

		if (rError > maxRError) {
			maxRError = rError;
		}

		if (guessN != n || rError > RADIUS_ACCURACY) {
			out.println(
					n + ": " + n1 + " " + n2 + " " + n3 + " -> " + guessN + ", r = " + guessR + " (true + " + r + ")");
		}
	}

	/**
	 * Generates a table of primes >= 3 no greater than the given upper limit.
	 *
	 * <p>
	 * The sieve of Eratosthenes without even numbers is used.
	 * </p>
	 *
	 * @param max the greatest possible prime number to consider
	 * @return an array of prime numbers from 3 to max
	 */
	private static int[] generatePrimes(int max) {
		var seive = new boolean[(max - 1) / 2];
		Arrays.fill(seive, true);

		// the upper limit is because the inner loop won't run anyway for greater values
		for (int candidate = 3; 3 * candidate <= max; candidate += 2) {
			boolean prime = seive[(candidate - 3) / 2];

			if (prime) {
				// even multiples are not in the table
				for (int j = 3 * candidate; j <= max; j += 2 * candidate) {
					seive[(j - 3) / 2] = false;
				}
			}
		}

		// find out how many elements to allocate
		int primeCount = 0;

		for (int cell = 0; cell < seive.length; ++cell) {
			if (seive[cell]) {
				primeCount++;
			}
		}

		var primes = new int[primeCount];
		int i = 0;

		// add primes to the array
		for (int cell = 0; cell < seive.length; ++cell) {
			if (seive[cell]) {
				primes[i++] = cell * 2 + 3;
			}
		}

		return primes;
	}

	/**
	 * A helper class to build regular polygon vertices.
	 */
	private static class RegularPoly {
		private double alpha;
		private double r;
		private Point center;

		/**
		 * Creates a regular polygon.
		 *
		 * @param n      the number of corners
		 * @param r      the circumcircle radius
		 * @param center the circumcircle center
		 */
		public RegularPoly(int n, double r, Point center) {
			this.alpha = (2 * PI) / n;
			this.r = r;
			this.center = center;
		}

		/**
		 * Builds a regular polygon vertex.
		 *
		 * @param i the vertex number, >= 0
		 * @return the regular polygon vertex
		 */
		public Point point(int i) {
			double angle = alpha * i;
			double x = center.x + r * cos(angle);
			double y = center.y + r * sin(angle);
			return new Point(x, y);
		}
	}
}
