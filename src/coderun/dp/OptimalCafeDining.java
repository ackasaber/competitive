package coderun.dp;

/* Task 5. Cafe */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The task solution.
 * 
 * <p>Let's define <i>S</i><sub><i>kn</i></sub> as the minimum necessary amount
 * of money that's spent on lunches in the last <i>n</i> days while having
 * exactly <i>k</i> free meal tickets, <i>k</i> &ge; 0 and
 * 0 &le; <i>n</i> &le; <i>N</i>. Then we observe the following recurrent
 * relation:</p>
 * 
 * <p><i>S</i><sub><i>k</i>0</sub> = 0 for all <i>k</i> &ge; 0,</p>
 * 
 * <p>If <i>n</i> &ge; 1
 * <ul>
 *     <li>when <i>C</i><sub><i>N</i> &minus; <i>n</i></sub> &le; 100:<br>
 *
 *         <i>S</i><sub>0<i>n</i></sub> =
 *             <i>C</i><sub><i>N</i> &minus; <i>n</i></sub>
 *             + <i>S</i><sub>0,<i>n</i> &minus; 1</sub>,<br>
 *         <i>S</i><sub><i>kn</i></sub> = min(<i>C</i><sub><i>N</i> &minus;
 *             <i>n</i></sub> + <i>S</i><sub><i>k</i>,<i>n</i> &minus; 1</sub>,
 *             <i>S</i><sub><i>k</i> &minus; 1, <i>n</i> &minus; 1</sub>)
 *             for <i>k</i> &ge; 1,
 *     </li>
 *     <li> when <i>C</i><sub><i>N</i> &minus; <i>n</i></sub> &gt; 100:<br>
 *     
 *         <i>S</i><sub>0<i>n</i></sub> =
 *             <i>C</i><sub><i>N</i> &minus; <i>n</i></sub> +
 *             <i>S</i><sub>1,<i>n</i> &minus; 1</sub>,<br>
 *         <i>S</i><sub><i>kn</i></sub> = min(<i>C</i><sub><i>N</i> &minus;
 *             <i>n</i></sub> + <i>S</i><sub><i>k</i> + 1, <i>n</i> &minus; 1</sub>,
 *             <i>S</i><sub><i>k</i> &minus; 1, <i>n</i> &minus; 1</sub>
 *             for <i>k</i> &ge; 1.
 *     </li>
 * </ul>
 * <p>The recurrence can be computed using a square <i>N</i>&times;<i>N</i>
 * table in &Theta;(<i>N</i><sup>2</sup>) time.</p>
 * <p>For computation we also use that <i>S</i><sub><i>kn</i></sub> = 0
 * for <i>k</i> &ge; <i>n</i>.</p>
 */
public class OptimalCafeDining {

	/**
	 * Reads the price table in from the standard input and writes the optimal
	 * dining schedule out to the standard output.
	 * 
	 * @param args command line arguments (unused)
	 */
	public static void main(String[] args) {
		var input = new InputStreamReader(System.in, StandardCharsets.US_ASCII);
		var bufferedInput = new BufferedReader(input);
		var scanner = new Scanner(bufferedInput); 
		var C = readLunchCosts(scanner);
		var S = calculateMinCosts(C);
		
		var output = new OutputStreamWriter(System.out, StandardCharsets.US_ASCII);
		var bufferedOutput = new BufferedWriter(output);
		reportSolution(bufferedOutput, C, S);
	}

	/**
	 * Reads the lunch costs.
	 *  
	 * @param scanner the source of data
	 * @return the array with lunch costs
	 */
	static int[] readLunchCosts(Scanner scanner) {
		int N = scanner.nextInt();
		// lunch costs per day
		var C = new int[N];
		
		for (int d = 0; d < N; d++) {
			C[d] = scanner.nextInt();
		}

		return C;
	}
	
	/**
	 * Dynamic programming for the solution.
	 * 
	 * @param C the lunch cost array
	 * @return the table S as defined in the class documentation
	 */
	static int[][] calculateMinCosts(int[] C) {
		int N = C.length;
		
		// Note the default zero initialization.
		var S = new int[N + 1][N + 1];
		
		for (int n = 1; n <= N; n++) {
			if (C[N - n] <= 100) {
				S[0][n] = C[N - n] + S[0][n - 1];
				
				for (int k = 1; k < n; k++) {
					S[k][n] = Math.min(C[N - n] + S[k][n - 1], S[k - 1][n - 1]);
				}
			} else {
				S[0][n] = C[N - n] + S[1][n - 1];
				
				for (int k = 1; k < n; k++) {
					S[k][n] = Math.min(C[N - n] + S[k + 1][n - 1], S[k - 1][n - 1]);
				}
			}
		}
		
		return S;
	}
	
	/**
	 * Restores the solution from the dynamic programming table and
	 * writes in the required format.
	 * 
	 * @param output where to write the result
	 * @param C the lunch cost array
	 * @param S the solution table, see the class documentation
	 */
	static void reportSolution(Writer output, int[] C, int[][] S) {
		var writer = new PrintWriter(output);
		int N = C.length;
		writer.println(S[0][N]);
		
		int k = 0;
		var freeDays = new ArrayList<Integer>();
		
		for (int n = N; n > 0; n--) {
			if (C[N - n] <= 100) {
				if (k > 0 && S[k][n] != C[N - n] + S[k][n - 1]) {
					freeDays.add(N - n + 1);
					k--;
				}
			} else {
				if (k > 0 && S[k][n] != C[N - n] + S[k + 1][n - 1]) {
					freeDays.add(N - n + 1);
					k--;
				} else {
					k++;
				}
			}
		}
		
		writer.printf("%d %d%n", k, freeDays.size());
		freeDays.forEach(day -> writer.printf("%d ", day));
		writer.println();
		writer.flush();
	}
}
