package codeforces.beta01;

/* A. Theater square. */

import java.util.Scanner;
import static java.lang.System.out;
import static java.nio.charset.StandardCharsets.US_ASCII;

public class TheaterSquare {
	static long flagstoneCount(int n, int m, int a) {
		if (n < 1 || n > 1000_000_000)
			throw new IllegalArgumentException("n out of range");
		if (m < 1 || m > 1000_000_000)
			throw new IllegalArgumentException("m out of range");
		if (a < 1 || a > 1000_000_000)
			throw new IllegalArgumentException("a out of range");
        // ceil(n/a)
		int oneSide = (n + a - 1) / a;
		// ceil(m/a)
		int otherSide = (m + a - 1) / a;
		// Result might not fit into int, so cast the first operand.
		return (long) oneSide * otherSide;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in, US_ASCII);
		int n = scanner.nextInt();
		int m = scanner.nextInt();
		int a = scanner.nextInt();
		long count = flagstoneCount(n, m, a);
		out.println(count);
	}
}
