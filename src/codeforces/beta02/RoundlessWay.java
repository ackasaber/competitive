package codeforces.beta02;

// B. The least round way

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.US_ASCII;

/**
 * The solution based on dynamic programming over 2- and 5-factorizations.
 * 
 * <p>Note that while it's not emphasized in the task description, but it's considered that the
 * number zero ends in precisely one zero digit.</p>
 */
public final class RoundlessWay {

    public static void main(String[] args) {
		var reader = new InputStreamReader(System.in, US_ASCII);
		var bufferedReader = new BufferedReader(reader);
		var scanner = new Scanner(bufferedReader);
		var table = readTable(scanner);
		// Solve for powers of 2 and 5 and pick the smallest.
		// The smallest solution will have the same or larger power of the other number.
		Solution solution2 = new Solution(table, 2);
		Solution solution5 = new Solution(table, 5);
		Solution solution = solution2;

		if (solution5.power() < solution2.power())
			solution = solution5;

		solution.reportResults();
	}

    public static int[][] readTable(Scanner scanner) {
        int n = scanner.nextInt();
        var table = new int[n][n];

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j)
                table[i][j] = scanner.nextInt();
        }

        return table;
    }

    /**
	 * A solution for a prime factor.
	 */
	private static final class Solution {
		/**
		 * The table for dynamic programming: each cell contains the minimum power of
		 * the prime factor in the product of cell values across the path starting in
		 * the top left cell and ending in the current cell.
		 */
		private int[][] minPower;
		/**
		 * The direction of the previous cell in the optimal path.
		 */
		private Direction[][] previous;

		/**
		 * Whenever there is zero in the path, we consider the path product to contain
		 * the prime factor always of the power one.
		 */
		private static final int STICKY_ONE = -1;

		/**
		 * Combines the prime factor powers along the path.
         * 
         * <p>The zeroes across path give the power of "sticky one".</p>
         * 
		 * @param x one prime factor power
		 * @param y another prime factor power
		 * @return the combined power
		 */
		private static int add(int x, int y) {
			if (x == STICKY_ONE || y == STICKY_ONE) {
				return STICKY_ONE;
			}

			return x + y;
		}

		/**
		 * Compares prime factor powers, taking the "sticky one" power into account.
         *
		 * @param x one prime factor power
		 * @param y another prime factor power
		 * @return comparison result
		 * @see Integer#compare
		 */
		private static int compare(int x, int y) {
			return Integer.compare(unstick(x), unstick(y));
		}

		/**
		 * The prime factor power, with the "sticky one" converted to one.
         *
		 * @param u prime factor power
		 * @return "unstickied" power
		 */
		private static int unstick(int u) {
			if (u == STICKY_ONE) {
				return 1;
			}

			return u;
		}

		/**
		 * Constructs the solution for the given prime factor.
		 *
		 * @param table  source data
		 * @param factor prime factor
		 */
		public Solution(int[][] table, int factor) {
			int n = table.length;
			minPower = new int[n][n];
			previous = new Direction[n][n];

			var powers = new int[n][n];
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < n; ++j) {
					int value = table[i][j];

					if (value == 0)
						powers[i][j] = STICKY_ONE;
					else
						powers[i][j] = countPowers(table[i][j], factor);
				}
			}

			minPower[0][0] = powers[0][0];
			previous[0][0] = Direction.START;

			for (int j = 1; j < n; ++j) {
				minPower[0][j] = add(minPower[0][j - 1], powers[0][j]);
				previous[0][j] = Direction.LEFT;
			}

			for (int i = 1; i < n; ++i) {
				minPower[i][0] = add(minPower[i - 1][0], powers[i][0]);
				previous[i][0] = Direction.TOP;

				for (int j = 1; j < n; ++j) {
					if (compare(minPower[i][j - 1], minPower[i - 1][j]) < 0) {
						minPower[i][j] = add(minPower[i][j - 1], powers[i][j]);
						previous[i][j] = Direction.LEFT;
					} else {
						minPower[i][j] = add(minPower[i - 1][j], powers[i][j]);
						previous[i][j] = Direction.TOP;
					}
				}
			}
		}

		/**
		 * Final result: the minimum power of the prime factor across the optimal path
		 * from the top left to the bottom right corner.
		 *
		 * @return prime factor power
		 */
		public int power() {
            int n = minPower.length;
			return unstick(minPower[n - 1][n - 1]);
		}

		/**
		 * Writes down the results: the minimum power of the prime factor in the product
		 * over the optimal path and the optimal path itself.
		 */
		public void reportResults() {
			// Start from the final cell: the bottom right corner.
            int n = minPower.length;
			int i = n - 1;
			int j = n - 1;
			System.out.println(power());
			var directions = new ArrayList<Direction>(2 * n - 2);
			var d = previous[i][j];

			while (d != Direction.START) {
				directions.add(d);

				if (d == Direction.LEFT)
					j--;
				else
					i--;

				d = previous[i][j];
			}

			// Print the path from the first cell to the final cell.
			for (int k = directions.size() - 1; k >= 0; --k) {
				var direction = directions.get(k);

				if (direction == Direction.LEFT)
					System.out.print('R');
				else
					System.out.print('D');
			}

			System.out.println();
		}
	}

	/**
	 * Computes the power of the given number in the factorization of the input.
	 *
	 * @param n      input number, non-zero
	 * @param factor factor
	 * @return the power <i>k</i> such that <i>n</i> divides
	 *         factor<sup><i>k</i></sup> but doesn't divide factor<sup><i>k</i> +
	 *         1</sup>
	 */
	private static int countPowers(int n, int factor) {
		int power = 0;

		while (n % factor == 0) {
			power++;
			n /= factor;
		}

		return power;
	}

	private enum Direction { START, TOP, LEFT };
}
