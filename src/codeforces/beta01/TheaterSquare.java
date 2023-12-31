package codeforces.beta01;

/* Task 1A. Theater square. */

import java.util.Scanner;
import static java.lang.System.out;

import java.nio.charset.StandardCharsets;

public class TheaterSquare {
	static long flagstoneCount(int n, int m, int a) {
		/* multiplication in the end would require a wider type */
		long N = n;
		long M = m;
		long A = a;
		/* ceil(N/A) */
		long oneside = (N + A - 1) / A;
		/* ceil(M/A) */
		long otherside = (M + A - 1) / A;
		return oneside * otherside;
	}

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in, StandardCharsets.US_ASCII);
		int n = scanner.nextInt();
		int m = scanner.nextInt();
		int a = scanner.nextInt();

		if (n < 1 || n > 1000000000)
			out.println("WRONG n");
		else if (m < 1 || m > 1000000000)
			out.println("WRONG m");
		else if (a < 1 || a > 1000000000)
			out.println("WRONG a");
		else {
			long count = flagstoneCount(n, m, a);
			out.println(count);
		}
	}
}
