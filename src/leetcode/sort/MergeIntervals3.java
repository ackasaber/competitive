package leetcode.sort;

import java.util.Arrays;

/**
 * The task solution.
 * 
 * <p>Sort the intervals by the interval start. Join the neighboring intervals
 * that overlap.</p>
 */
public class MergeIntervals3 {

	public static int[][] merge(int[][] intervals) {
		int n = intervals.length;
		var array = copyIntervals(intervals);
		Arrays.sort(array, (x, y) -> Integer.compare(x[0], y[0]));
		var result = new int[n][];
		result[0] = array[0];
		int k = 1;
		
		for (int i = 1; i < n; i++) {
			if (array[i][0] <= result[k - 1][1]) {
				consume(result[k - 1], array[i]);
			} else {
				result[k++] = array[i];
			}
		}
		
		return Arrays.copyOf(result, k);
	}
	
	private static void consume(int[] x, int[] y) {
		if (y[1] > x[1]) {
			x[1] = y[1];
		}
	}
	
	private static int[][] copyIntervals(int[][] intervals) {
		int n = intervals.length;
		var array = new int[n][2];
		
		for (int i = 0; i < n; i++) {
			array[i][0] = intervals[i][0];
			array[i][1] = intervals[i][1];
		}
		
		return array;
	}
}
