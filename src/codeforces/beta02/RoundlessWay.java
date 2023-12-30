package codeforces.beta02;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/* Task 2B. The least round way */

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The task solution.
 *
 * The solution is based on dynamic programming over 2- and 5-factorizations.
 */
public final class RoundlessWay {
	/**
	 * Runs the solution.
	 *
	 * @param args command-line arguments (unused)
	 */
	public static void main(String[] args) {
		var reader = new InputStreamReader(System.in, StandardCharsets.US_ASCII);
		var bufferedReader = new BufferedReader(reader);
		var scanner = new Scanner(bufferedReader);
		var table = Table.read(scanner);
		// Solve for powers of 2 and 5 and pick the smallest.
		// The smallest solution will have the same or larger power of the other number.
		Solution solution2 = new Solution(table, 2);
		Solution solution5 = new Solution(table, 5);
		Solution solution = solution2;

		if (solution5.power() < solution2.power()) {
			solution = solution5;
		}

		solution.reportResults();
	}

	/**
	 * A square table of integer numbers.
	 */
	private static final class Table {
		/**
		 * Table size.
		 */
		private int n;
		/**
		 * Table data.
		 */
		private int[][] data;

		/**
		 * Creates a nxn-table filled with zeroes.
		 *
		 * @param n table size
		 */
		private Table(int n) {
			this.n = n;
			this.data = new int[n][n];
		}

		/**
		 * Returns the cell contents.
		 *
		 * @param i row number
		 * @param j column number
		 * @return the cell contents
		 */
		public int get(int i, int j) {
			return data[i][j];
		}

		/**
		 * Returns the table size.
		 *
		 * @return table size
		 */
		public int size() {
			return n;
		}

		/**
		 * Reads the table from a scanner.
		 */
		public static Table read(Scanner scanner) {
			int n = scanner.nextInt();
			var table = new Table(n);

			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < n; ++j) {
					table.data[i][j] = scanner.nextInt();
				}
			}

			return table;
		}

	}

	/**
	 * A solution for a prime factor.
	 */
	private static final class Solution {
		/**
		 * The tables size.
		 */
		private int n;
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
		 * Combines the prime factor powers along the path. The zeroes across path give the power of "sticky one".
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
		public Solution(Table table, int factor) {
			n = table.size();
			minPower = new int[n][n];
			previous = new Direction[n][n];

			var powers = new int[n][n];
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < n; ++j) {
					int value = table.get(i, j);

					if (value == 0) {
						powers[i][j] = STICKY_ONE;
					} else {
						powers[i][j] = countPowers(table.get(i, j), factor);
					}
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
			return unstick(minPower[n - 1][n - 1]);
		}

		/**
		 * Writes down the results: the minimum power of the prime factor in the product
		 * over the optimal path and the optimal path itself.
		 */
		public void reportResults() {
			// Start from the final cell: the bottom right corner.
			int i = n - 1;
			int j = n - 1;
			System.out.println(power());
			var directions = new ArrayList<Direction>(2 * n - 2);
			var d = previous[i][j];

			while (d != Direction.START) {
				directions.add(d);

				if (d == Direction.LEFT) {
					j--;
				} else {
					i--;
				}

				d = previous[i][j];
			}

			// Print the path from the first cell to the final cell.
			for (int k = directions.size() - 1; k >= 0; --k) {
				var direction = directions.get(k);

				if (direction == Direction.LEFT) {
					System.out.print('R');
				} else {
					System.out.print('D');
				}
			}

			System.out.println();
		}
	}

	/**
	 * Computes the power of the given number in the factorization of the input.
	 *
	 * @param n      input number
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

	/**
	 * Direction of the previous cell in the path.
	 */
	private enum Direction {
		/**
		 * This is the starting cell.
		 */
		START,
		/**
		 * The previous cell in the path is on the top.
		 */
		TOP,
		/**
		 * The previous cell in the path is on the left.
		 */
		LEFT
	};

}
