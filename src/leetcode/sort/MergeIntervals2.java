package leetcode.sort;

import java.util.Arrays;

/**
 * The task solution.
 * 
 * <p>Sort all interval ends, keep starts before ends if they coincide.
 * Then for each end in the sorted sequence count how many intervals cover it.
 * Pick only the ends that are covered by zero other intervals.</p>
 */
public class MergeIntervals2 {
	
	public static int[][] merge(int[][] intervals) {
		int n = intervals.length;
		var ends = new End[2 * n];
		int k = 0;
		
		for (int i = 0; i < n; i++) {
			ends[k++] = new End(intervals[i][0], +1);
			ends[k++] = new End(intervals[i][1], -1);
		}
		
		Arrays.sort(ends);
		var result = new int[n][2];
		k = 0;
		int length = 0;
		
		while (k < 2 * n) {
			result[length][0] = ends[k].position;
			int count = 1;
			
			while (count > 0) {
				k++;
				count += ends[k].type;
			}
			
			result[length][1] = ends[k].position;
			length++;
			k++;
		}
		
		return Arrays.copyOf(result, length);
	}
	
	private static final class End implements Comparable<End> {
		int position;
		int type; // +1 - opening, -1 - closing
		
		public End(int position, int type) {
			this.position = position;
			this.type = type;
		}

		@Override
		public int compareTo(End end) {
			if (position < end.position) {
				return -1;
			}
			
			if (position > end.position) {
				return +1;
			}
			
			return -Integer.compare(type, end.type);
		}
		
	}
}
