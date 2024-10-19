package codeforces.beta03;

// D. Least cost bracket sequence

import static java.nio.charset.StandardCharsets.US_ASCII;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Dynamic programming solution.
 * 
 * <p>Let <i>f</i>(<i>m</i>, <i>i</i>) be the minimum cost of
 * correctly placing <i>m</i> pairs of brackets starting from
 * the position <i>i</i>, 0 &leq; <i>m</i> &leq; <i>n</i> / 2,
 * 0 &leq; <i>i</i> &lt; <i>n</i> &minus; 2<i>m</i>.</p>
 * 
 * <p>Let's assume that zero brackets cost zero to place:</p>
 * <p><i>f</i>(0, <i>i</i>) = 0.</p>
 * <p>Every solution on the highest level of brackets is either
 * <code>((...))</code> or <code>(...)(...)</code>:</p>
 * <p><i>f</i>(<i>m</i>, <i>i</i>) = min{ <i>q</i><sub><i>i</i></sub> +
 *    <i>f</i>(<i>i</i> + 1, <i>m</i> &minus; 1) + <i>p</i><sub><i>i</i> + 2<i>m</i> &minus; 1</sub>,
 *    <i>f</i>(<i>i</i>, <i>k</i>) + <i>f</i>(<i>i</i> + 2<i>k</i>, <i>m</i> &minus; <i>k</i>) |
 *    1 &le; <i>k</i> &le; <i>m</i> &minus; 1 }.</p>
 *    
 * <p>(<i>q</i><sub><i>i</i></sub> and <i>p</i><sub><i>i</i></sub> are costs of opening
 * and closing a bracket at the index <i>i</i> respectively.)</p>
 */
public class BracketsDP {

	public static void main(String[] args) {
		var bufferedReader = new BufferedReader(new InputStreamReader(System.in, US_ASCII));
		var scanner = new Scanner(bufferedReader);
		var task = new Task(scanner);
		var solution = task.solve();

		var bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out, US_ASCII));
		var writer = new PrintWriter(bufferedWriter);
		solution.write(writer);
		writer.flush();
	}

	private static class Task {
		// The input pattern.
		String pattern;
		
		// The input pattern length.
		int n;
		
		// Opening bracket costs at the given index, -1 if the opening bracket is forbidden.
		int[] openCost;
		
		// Closing bracket costs, -1 if the closing bracket is forbidden.
		int[] closeCost;
		
		// The minimal cost of placing certain number of brackets at the given index,
		// -1 if it's impossible to place brackets in this range.
		int[][] minCost;
		
		public Task(Scanner scanner) {
			pattern = scanner.nextLine();
			n = pattern.length();
			openCost = new int[n];
			closeCost = new int[n];
			
			for (int i = 0; i < n; ++i) {
                switch (pattern.charAt(i)) {
                    case '?' -> {
                        openCost[i] = scanner.nextInt();
                        closeCost[i] = scanner.nextInt();
                    }
                    case '(' -> {
                        openCost[i] = 0;
                        closeCost[i] = -1;
                    }
                    case ')' -> {
                        openCost[i] = -1;
                        closeCost[i] = 0;
                    }
                }
			}
		}
		
		public Solution solve() {
			if (n > 1000)
				throw new UnsupportedOperationException("this will take too long to compute");

			minCost = new int[n / 2 + 1][];
			minCost[0] = new int[n + 1];
			
            // Java fill with zeroes by default.
            // for (int i = 0; i <= n; ++i)
            //     minCost[0][i] = 0;
			
			for (int m = 1; m <= n / 2; ++m) {
				minCost[m] = new int[n - 2 * m + 1];
				for (int i = 0; i <= n - 2 * m; ++i) {
					int cost = add(openCost[i], minCost[m - 1][i + 1], closeCost[i + 2 * m - 1]);
					
					for (int k = 1; k < m; ++k) {
						int other = add(minCost[k][i], minCost[m - k][i + 2 * k]);
						
						if (cost == -1 || other != -1 && other < cost)
							cost = other;
					}

					minCost[m][i] = cost;
				}
			}
			
			return new Solution(this);
		}
	}
	
	private static class Solution {
		StringBuilder brackets;
		Task task;
		
		public Solution(Task task) {
			this.task = task;
		}

		public void write(PrintWriter writer) {
			var cost = task.minCost[task.n / 2][0];
			writer.println(cost);
			
			if (cost != -1) {
				brackets = new StringBuilder(task.pattern);
				restore(task.n / 2, 0);
				writer.println(brackets);
			}
		}
		
		/**
		 * Restores the solution from the minimum cost table.
		 * 
		 * @param m amount of bracket pairs to restore
		 * @param i the starting index
		 */
		private void restore(int m, int i) {
			if (m > 0) {
				var cost = task.minCost[m][i];
				
				if (cost == add(task.openCost[i], task.minCost[m - 1][i + 1], task.closeCost[i + 2 * m - 1])) {
					brackets.setCharAt(i, '(');
					brackets.setCharAt(i + 2 * m - 1, ')');
					restore(m - 1, i + 1);
				} else {
					int k = 1;
					
					while (cost != add(task.minCost[k][i], task.minCost[m - k][i + 2 * k])) {
						++k;
					}
					
					restore(k, i);
					restore(m - k, i + 2 * k);
				}
			}
		}
	}
	
	// Add numbers considering -1 to be an infinity.
    
	private static int add(int x, int y, int z) {
		return (x == -1 || y == -1 || z == -1) ? - 1 : x + y + z;
	}
	
	private static int add(int x, int y) {
		return (x == -1 || y == -1) ? -1 : x + y;
	}
}
