package coderun.dp;

/* Task 2. The cheapest path */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * The task solution.
 * 
 * <p>
 * Let <i>A</i><sub><i>ij</i></sub> be the cost of travelling through the
 * cell in row <i>i</i> and column <i>j</i> and <i>C</i><sub><i>ij</i></sub> be
 * the solution for a sub-matrix with <i>i</i> first rows and <i>j</i> first
 * columns. The latter satisfies the recurrence
 * </p>
 * <p>
 * <i>C</i><sub>11</sub> = <i>A</i><sub>11</sub>,<br>
 * <i>C</i><sub>1<i>j</i></sub> = <i>C</i><sub>1, <i>j</i> &minus; 1</sub> +
 * <i>A</i><sub>1<i>j</i></sub> for 2 &le; <i>j</i> &le; <i>M</i>,<br>
 * <i>C</i><sub><i>i</i>1</sub> = <i>C</i><sub><i>i</i> &minus; 1,1</sub> +
 * <i>A</i><sub><i>i</i>1</sub> for 2 &le; <i>i</i> &le; <i>N</i>,<br>
 * <i>C</i><sub><i>ij</i></sub> = min(<i>C</i><sub><i>i</i> &minus; 1, <i>j</i></sub>,
 * <i>C</i><sub><i>i</i>,<i>j</i> &minus; 1</sub>) + <i>A</i><sub><i>ij</i></sub>.
 * </p>
 * 
 * We can compute just a row of the <i>C</i> matrix at a time.
 */
public class FitTraveler {

	/**
	 * Reads the travelling costs from the standard input and writes the
	 * answer to the standard output.
	 * 
	 * @param args command line arguments (unused)
	 */
	public static void main(String[] args) {
		var input = new InputStreamReader(System.in, StandardCharsets.US_ASCII);
		var buffer = new BufferedReader(input);
		@SuppressWarnings("resource")
		var scanner = new Scanner(buffer);
		int n = scanner.nextInt();
		int m = scanner.nextInt();
		var fines = new int[n][m];
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				fines[i][j] = scanner.nextInt();
			}
		}
		
		var minFine = new int[m];
		minFine[0] = fines[0][0];
		
		for (int j = 1; j < m; j++) {
			minFine[j] = minFine[j - 1] + fines[0][j];
		}
		
		for (int i = 1; i < n; i++) {
			minFine[0] += fines[i][0];
			
			for (int j = 1; j < m; j++) {
				minFine[j] = Math.min(minFine[j], minFine[j - 1]) + fines[i][j];
			}
		}
		
		System.out.println(minFine[m - 1]);
	}

}
