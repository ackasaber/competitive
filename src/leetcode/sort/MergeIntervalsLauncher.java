package leetcode.sort;

/* Task 56. Merge Intervals */

import static java.nio.charset.StandardCharsets.US_ASCII;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * The task solution
 */
public class MergeIntervalsLauncher {

	/**
	 * Reads the array of intervals from the standard input and
	 * writes the array of merged non-overlapping intervals to the 
	 * standard output.
	 * 
	 * @param args command line arguments (unused)
	 */
	public static void main(String[] args) {
		var reader = new InputStreamReader(System.in, US_ASCII);
		var bufferedReader = new BufferedReader(reader);
		var scanner = new Scanner(bufferedReader);
		var array = readIntervals(scanner);
		var merged1 = MergeIntervals1.merge(array);
		var merged2 = MergeIntervals2.merge(array);
		var merged3 = MergeIntervals3.merge(array);
		var writer = new OutputStreamWriter(System.out, US_ASCII);
		var bufferedWriter = new BufferedWriter(writer);
		var printWriter = new PrintWriter(bufferedWriter);
		reportIntervals(printWriter, "Mergesort", merged1);
		reportIntervals(printWriter, "Ends sorting", merged2);
		reportIntervals(printWriter, "Start sorting", merged3);
		printWriter.flush();
	}

	private static int[][] readIntervals(Scanner scanner) {
		int n = scanner.nextInt();
		var array = new int[n][2];
		
		for (int i = 0; i < n; i++) {
			array[i][0] = scanner.nextInt();
			array[i][1] = scanner.nextInt();
		}
		
		return array;
	}
	
	private static void reportIntervals(PrintWriter writer,
	                                    String message, int[][] intervals) {
		writer.printf("> %s:%n", message);
		
		for (var interval: intervals) {
			writer.printf("%d %d%n", interval[0], interval[1]);
		}
		
		writer.println();
	}
}
