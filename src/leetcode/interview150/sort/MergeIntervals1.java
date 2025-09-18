package leetcode.sort;

import java.util.Arrays;

/**
 * The task solution via merge sort.
 */
public class MergeIntervals1 {
	
	/**
	 * Merges intervals until they don't overlap.
	 * 
	 * @param intervals the array of pairs defining intervals
	 * @return the array of pairs defining non-overlapping intervals
	 */
	public static int[][] merge(int[][] intervals) {
		var sorter = new MergeSorter(intervals);
		int k = sorter.sort(0, intervals.length);
		return sorter.cut(k);
	}
	
	/**
	 * Merge sorting framework.
	 */
	private static final class MergeSorter {
		private int[][] array;
		private int[][] buffer;
		
		/**
		 * Initializes the merge sorter.
		 * 
		 * @param intervals the array to merge
		 */
		public MergeSorter(int[][] intervals) {
			int n = intervals.length;
			// copy the input
			array = new int[n][2];
			
			for (int i = 0; i < n; i++) {
				array[i][0] = intervals[i][0];
				array[i][1] = intervals[i][1];
			}
			
			buffer = new int[n][];
		}
		
		/**
		 * Merge intervals in the subarray.
		 * 
		 * @param p the subarray starting index
		 * @param q the subarray one-past-end index
		 * @return one-past-end index of the merged part
		 */
		public int sort(int p, int q) {
			if (q - p <= 1) {
				return q;
			}
			
			int r = (p + q) / 2;
			int s = sort(p, r);
			int t = sort(r, q);
			return merge(p, s, r, t);
		}
		
		/**
		 * Merges non-overlapping intervals in <code>array</code>[<i>p</i> ..
		 * <i>s</i> &minus; 1] with <code>array</code>[<i>t</i> .. <i>q</i>
		 * &minus; 1].
		 * 
		 * @param p the first subarray starting index
		 * @param s the first subarray one-past-end index
		 * @param t the second subarray staring index
		 * @param q the second subarray one-past-end index
		 * @return the one-past-end index of the merged result 
		 */
		private int merge(int p, int s, int t, int q) {
			for (int i = p; i < s; i++) {
				buffer[i] = array[i];
			}
			
			for (int j = t; j < q; j++) {
				buffer[j] = array[j];
			}
			
			int k = p;
			int i = p;
			int j = t;
			
			if (buffer[i][0] <= buffer[j][0]) {
				array[k++] = buffer[i++];
			} else {
				array[k++] = buffer[j++];
			}
			
			while (i < s && j < q) {
				// let's consume a single element per iteration
				if (buffer[i][0] <= array[k - 1][1]) {
					consume(array[k - 1], buffer[i++]);
				} else if (buffer[j][0] <= array[k - 1][1]) {
					consume(array[k - 1], buffer[j++]);
				} else {
					if (buffer[i][0] <= buffer[j][0]) {
						array[k++] = buffer[i++];
					} else {
						array[k++] = buffer[j++];
					}
				}
			}
			
			while (i < s) {
				if (buffer[i][0] <= array[k - 1][1]) {
					consume(array[k - 1], buffer[i++]);
				} else {
					array[k++] = buffer[i++];
				}
			}
			
			while (j < q) {
				if (buffer[j][0] <= array[k - 1][1]) {
					consume(array[k - 1], buffer[j++]);
				} else {
					array[k++] = buffer[j++];
				}
			}
			
			return k;
		}
		
		public int[][] cut(int count) {
			return Arrays.copyOf(array, count);
		}
		
		private static void consume(int[] x, int[] y) {
			if (y[1] > x[1]) {
				x[1] = y[1];
			}
		}
	}
}
