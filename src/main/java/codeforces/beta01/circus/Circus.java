package codeforces.beta01.circus;

import java.nio.charset.StandardCharsets;

/* Task 1C. Ancient Berlandian circus */

import java.util.Locale;
import java.util.Scanner;

/**
 * The task solution.
 */
public final class Circus {
	/**
	 * Read the three points from the standard input and compute the square of the
	 * smallest regular polygon with vertices in these points.
	 *
	 * @param args command line arguments (not used)
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in, StandardCharsets.US_ASCII);
		scanner.useLocale(Locale.ROOT);
		Point a = nextPoint(scanner);
		Point b = nextPoint(scanner);
		Point c = nextPoint(scanner);
		RegularPolygon polygon = Solution1.INSTANCE.build(a, b, c);
		System.out.println(polygon.area());
		polygon = Solution2.INSTANCE.build(a, b, c);
		System.out.println(polygon.area());
		polygon = Solution3.INSTANCE.build(a, b, c);
		System.out.println(polygon.area());
	}

	/**
	 * Read the point coordinates from a text source.
	 *
	 * @param scanner text source
	 * @return a new point with the read coordinates
	 */
	private static Point nextPoint(Scanner scanner) {
		double x = scanner.nextDouble();
		double y = scanner.nextDouble();
		return new Point(x, y);
	}
}