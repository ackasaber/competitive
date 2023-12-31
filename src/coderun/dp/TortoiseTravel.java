package coderun.dp;

/* Task 3. Maximum cost path */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * The task solution.
 * 
 * <p>The task is very similar to task 2, except this one changes minimum to
 * maximum and requires restoring the path.<p>
 */
public class TortoiseTravel {

	/**
	 * Reads the travelling costs from the standard input and writes
	 * the answer to the standard output.
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
		
		// Fill in the tables.
		var maxFine = new int[m];
		var wayBack = new char[n][m];
		maxFine[0] = fines[0][0];
		wayBack[0][0] = '.';
		
		for (int j = 1; j < m; j++) {
			maxFine[j] = maxFine[j - 1] + fines[0][j];
			wayBack[0][j] = 'R';
		}
		
		for (int i = 1; i < n; i++) {
			maxFine[0] += fines[i][0];
			wayBack[i][0] = 'D';
			
			for (int j = 1; j < m; j++) {
				if (maxFine[j] > maxFine[j - 1]) {
					maxFine[j] += fines[i][j];
					wayBack[i][j] = 'D';
				} else {
					maxFine[j] = maxFine[j - 1] + fines[i][j];
					wayBack[i][j] = 'R';
				}
			}
		}

		// Restore the way back.
		var way = new StringBuilder(n + m - 2);
		int r = n - 1;
		int c = m - 1;
		
		while (wayBack[r][c] != '.') {
			way.append(wayBack[r][c]);
			
			if (wayBack[r][c] == 'R') {
				c--;
			} else {
				r--;
			}
		}
	
		var output = new OutputStreamWriter(System.out, StandardCharsets.US_ASCII);
		var writeBuffer = new BufferedWriter(output);
		var printWriter = new PrintWriter(writeBuffer);
		printWriter.println(maxFine[m - 1]);
		
		for (int k = way.length() - 1; k >= 0; k--) {
			printWriter.print(way.charAt(k));
			printWriter.print(' ');
		}
		
		printWriter.println();
		printWriter.flush();
	}

}
