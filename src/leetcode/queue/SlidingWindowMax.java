package leetcode.queue;

/* Task 239. Sliding Window Maximum */

import static java.nio.charset.StandardCharsets.US_ASCII;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * The task solution.
 * 
 * <p>That one is a very clever trick. Notice that when a certain element in the
 * current window is maximum, all lesser elements before it can be maximum in
 * no further window. You could essentially have them removed from consideration
 * the moment the current maximum element hits the window. Moreover, whenever
 * we add an element to the window, anything lesser than it in the current
 * window can no longer become an element of the answer. This leads to us
 * storing only the non-increasing sequence of elements starting from the
 * current maximum. This data structure is called monotonic queue.</p>
 */
public class SlidingWindowMax {

	/**
	 * Reads the sequence of numbers and the window size from the standard
	 * input and writes the max sliding window sequence to the standard
	 * output.
	 * 
	 * @param args command line arguments (unused)
	 */
	public static void main(String[] args) {
		var reader = new InputStreamReader(System.in, US_ASCII);
		var bufferedReader = new BufferedReader(reader);
		@SuppressWarnings("resource")
		var scanner = new Scanner(bufferedReader);
		int n = scanner.nextInt();
		int k = scanner.nextInt();
		var nums = new int[n];
		
		for (int i = 0; i < n; i++) {
			nums[i] = scanner.nextInt();
		}
		
		var result = maxSlidingWindow(nums, k);
		var writer = new OutputStreamWriter(System.out, US_ASCII);
		var bufferedWriter = new BufferedWriter(writer);
		var printWriter = new PrintWriter(bufferedWriter);
		
		for (int i = 0; i < result.length; i++) {
			printWriter.print(result[i]);
			printWriter.print(' ');
		}
		
		printWriter.println();
		printWriter.flush();
	}
	
	/**
	 * Computes maximums of all sliding windows of the given length
	 * for a sequence of integer numbers.
	 * 
	 * @param nums the integer sequence, <i>n</i> = <code>nums.length</code>
	 * @param k the sliding window length, 1 &le; <i>k</i> &le; <i>n</i>
	 * @return array of all <i>n</i> &minus; <i>k</i> + 1 sliding window
	 * maximums in order
	 */
	public static int[] maxSlidingWindow(int[] nums, int k) {
		int n = nums.length;
		var result = new int[n - k + 1];
		var queue = new MonotonicQueue(n);
		
		for (int i = 0; i < k; i++) {
			queue.enqueue(nums[i]);
		}
		
		result[0] = queue.max();
		
		for (int i = k; i < n; i++) {
			
			if (nums[i - k] == queue.max()) {
				queue.removeMax();
			}
			
			queue.enqueue(nums[i]);
			result[i - k + 1] = queue.max();
		}
		
        return result;
    }
	
	/**
	 * Non-increasing monotonic queue.
	 */
	private static final class MonotonicQueue {
		private int[] queue;
		private int head;
		private int tail;
		
		/**
		 * Creates an empty non-increasing monotonic queue.
		 * 
		 * @param capacity the maximum supported number of insertions.
		 */
		public MonotonicQueue(int capacity) {
			queue = new int[capacity];
			head = 0;
			tail = 0;
		}
		
		/**
		 * Returns the maximum elements of the queue (it's in the head).
		 * 
		 * @return the maximum queue element
		 */
		public int max() {
			return queue[head];
		}
		
		/**
		 * Removes the maximum element of the queue (from the head).
		 */
		public void removeMax() {
			head++;
		}
		
		/**
		 * Adds a new element to the queue.
		 * 
		 * <p>The element is added to the queue tail, after removing all
		 * lesser elements from there.</p>
		 * @param x the element to add
		 */
		public void enqueue(int x) {
			while (tail > head && queue[tail - 1] < x) {
				tail--;
			}
			
			queue[tail++] = x;
		}
	}
}
