package leetcode.queue;

/* Task 480. Sliding Window Median */

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
 * <p>Let's store the sliding window elements in two priority queues.
 * The low priority queue is a non-increasing priority queue with a half of
 * lesser sliding window elements and the high priority queue is a non-decreasing
 * priority queue with the other other half of elements. When moving the
 * sliding window, we keep the queue sizes equal or, for an odd window size,
 * the high queue one element larger than the low queue.</p>
 * 
 * <p>We need a lot of machinery in order to support removing a heap element
 * by its source array index.</p>
 */
public class SlidingWindowMedian {

	/**
	 * Reads the sequence of numbers and the window size from the standard
	 * input and writes the median sliding window sequence to the standard
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
		
		var result = medianSlidingWindow(nums, k);
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
	 * Computes medians of all sliding windows of the given length
	 * for a sequence of integer numbers.
	 * 
	 * @param nums the integer sequence, <i>n</i> = <code>nums.length</code>
	 * @param k the sliding window length, 1 &le; <i>k</i> &le; <i>n</i>
	 * @return array of all <i>n</i> &minus; <i>k</i> + 1 sliding window
	 * medians in order
	 */
	public static double[] medianSlidingWindow(int[] nums, int k) {
		int n = nums.length;
		var result = new double[n - k + 1];
		var queue = new MedianQueue(nums);
		
		for (int i = 0; i < k; i++) {
			queue.enqueue(i);
		}
		
		for (int i = k; i < n; i++) {
			result[i - k] = queue.median();
			queue.remove(i - k);
			queue.enqueue(i);
		}
		
		result[n - k] = queue.median();
        return result;
    }
	
	/**
	 * A data structure with an efficient median computation.
	 */
	private static final class MedianQueue {
		private int[] value;
		private byte[] heapNumber;
		private MinHeap highQueue;
		private MaxHeap lowQueue;
		
		/**
		 * Creates an empty data structure.
		 * 
		 * @param nums the array of elements that could be added to the data
		 * structure
		 */
		public MedianQueue(int[] nums) {
			value = nums;
			highQueue = new MinHeap(nums);
			lowQueue = new MaxHeap(nums);
			heapNumber = new byte[nums.length];
		}
		
		/**
		 * Returns the median (as defined in the task description) of the elements
		 * currently in the data structure.
		 * 
		 * @return the median
		 */
		public double median() {
			if (highQueue.size() == lowQueue.size()) {
				return ((double) highQueue.min() + lowQueue.max()) / 2;
			}
			
			// highQueue.size() == lowQueue.size() + 1
			return highQueue.min();
		}
		
		/**
		 * Removes a data structure element.
		 * 
		 * @param i the source array index of the element to remove
		 */
		public void remove(int i) {
			if (heapNumber[i] == 1) {
				highQueue.remove(i);
			} else {
				lowQueue.remove(i);
			}
		}
		
		/**
		 * Adds a new element to the data structure.
		 * 
		 * @param i the source array index of the element to add
		 */
		public void enqueue(int i) {
			if (lowQueue.size() > 0 && value[i] < lowQueue.max()) {
				lowQueue.offer(i);
				heapNumber[i] = 2;
			} else {
				highQueue.offer(i);
				heapNumber[i] = 1;
			}
			
			// balance the queue sizes
			if (lowQueue.size() > highQueue.size()) {
				int j = lowQueue.poll();
				highQueue.offer(j);
				heapNumber[j] = 1;
			}
			
			if (highQueue.size() > lowQueue.size() + 1) {
				int j = highQueue.poll();
				lowQueue.offer(j);
				heapNumber[j] = 2;
			}
		}
	}
	
	/**
	 * A non-decreasing priority queue implementation supporting removal by
	 * index.
	 */
	private static final class MinHeap {
		private int[] value;
		private int[] heap;
		private int[] back;
		private int size;
		
		/**
		 * Creates an empty priority queue.
		 * 
		 * @param nums the array of elements that can be added to the priority
		 * queue
		 */
		public MinHeap(int[] nums) {
			value = nums;
			heap = new int[nums.length];
			back = new int[nums.length];
			size = 0;
		}
		
		/**
		 * Returns the minimum element value.
		 * 
		 * @return the minimum element value
		 */
		public int min() {
			return value[heap[0]];
		}
		
		/**
		 * Returns the queue size.
		 * 
		 * @return the queue size
		 */
		public int size() {
			return size;
		}
		
		/**
		 * Adds a new element to the queue.
		 * 
		 * @param i the source array index of the element to add
		 */
		public void offer(int i) {
			decreaseKey(size, i);
			size++;
		}
		
		/**
		 * Removes the minimum element from the queue.
		 * 
		 * @return the source array index of the removed element
		 */
		public int poll() {
			int i = heap[0];
			size--;
			increaseKey(0, heap[size]);
			return i;
		}
		
		/**
		 * Removes a queue element by its source array index.
		 * 
		 * @param i the source array index of the element to remove
		 */
		public void remove(int i) {
			decreaseKeyInf(back[i]);
			size--;
			increaseKey(0, heap[size]);
		}
		
		/**
		 * Decreases the heap element value to <code>value</code>[<i>i</i>].
		 * 
		 * @param h the heap index of the element with a decreased key
		 * @param i the source array index of the new key value
		 */
		private void decreaseKey(int h, int i) {
			int p = parent(h);
			
			while (h > 0 && value[heap[p]] > value[i]) {
				heap[h] = heap[p];
				back[heap[h]] = h;
				h = p;
				p = parent(h);
			}
			
			heap[h] = i;
			back[i] = h;
		}
		
		/**
		 * Decreases the heap element value to minus infinity.
		 * 
		 * @param h the heap index of the element
		 */
		private void decreaseKeyInf(int h) {
			int p = parent(h);
			
			while (h > 0) {
				heap[h] = heap[p];
				back[heap[h]] = h;
				h = p;
				p = parent(h);
			}
		}
		
		/**
		 * Returns the parent heap index.
		 * 
		 * @param h the heap index
		 * @return its parent element heap index
		 */
		private static int parent(int h) {
			return (h - 1) / 2;
		}
		
		/**
		 * Increases the heap element value to <code>value</code>[<i>i</i>].
		 * 
		 * @param h the heap index of the element that increases its key
		 * @param i the source array index of the new key value
		 */
		private void increaseKey(int h, int i) {
			int c = minInSubtree(h, i);
			
			while (c != h) {
				heap[h] = heap[c];
				back[heap[h]] = h;
				h = c;
				c = minInSubtree(h, i);
			}
			
			heap[h] = i;
			back[i] = h;
		}
		
		/**
		 * Returns the heap index of the minimum element in the heap subtree
		 * rooted at the given heap element, assuming the element value.
		 * 
		 * @param h the heap index of the subtree root
		 * @param i the assumed subtree root source array index
		 * @return the heap index of the minimum element in the heap subtree
		 */
		private int minInSubtree(int h, int i) {
			int c1 = firstChild(h);
			
			if (c1 < size && value[heap[c1]] < value[i]) {
				i = heap[c1];
				h = c1;
			}
			
			int c2 = c1 + 1;
			
			if (c2 < size && value[heap[c2]] < value[i]) {
				h = c2;
			}
			
			return h;
		}
		
		/**
		 * Returns the first child of the heap element.
		 * 
		 * @param h the heap element index
		 * @return the first child heap element index
		 */
		private static int firstChild(int h) {
			return 2 * h + 1;
		}
	}
	
	/**
	 * A non-decreasing priority queue implementation supporting removal by
	 * index.
	 * 
	 * <p>Copy-pasted from <code>MinHeap</code>, changed <code>increase</code>
	 * ↔ <code>decrease</code>, <code>min</code> ↔ <code>max</code> and
	 * comparison direction when comparing array elements.</p>
	 * 
	 * @see MinHeap
	 */
	private static final class MaxHeap {
		private int[] value;
		private int[] heap;
		private int[] back;
		private int size;
		
		public MaxHeap(int[] nums) {
			value = nums;
			heap = new int[nums.length];
			back = new int[nums.length];
			size = 0;
		}
		
		public int max() {
			return value[heap[0]];
		}
		
		public int size() {
			return size;
		}
		
		public void offer(int i) {
			increaseKey(size, i);
			size++;
		}
		
		public int poll() {
			int i = heap[0];
			size--;
			decreaseKey(0, heap[size]);
			return i;
		}
		
		public void remove(int i) {
			increaseKeyInf(back[i]);
			size--;
			decreaseKey(0, heap[size]);
		}
		
		private void increaseKey(int h, int i) {
			int p = parent(h);
			
			while (h > 0 && value[heap[p]] < value[i]) {
				heap[h] = heap[p];
				back[heap[h]] = h;
				h = p;
				p = parent(h);
			}
			
			heap[h] = i;
			back[i] = h;
		}
		
		private void increaseKeyInf(int h) {
			int p = parent(h);
			
			while (h > 0) {
				heap[h] = heap[p];
				back[heap[h]] = h;
				h = p;
				p = parent(h);
			}
		}
		
		private static int parent(int h) {
			return (h - 1) / 2;
		}
		
		private void decreaseKey(int h, int i) {
			int c = maxInSubtree(h, i);
			
			while (c != h) {
				heap[h] = heap[c];
				back[heap[h]] = h;
				h = c;
				c = maxInSubtree(h, i);
			}
			
			heap[h] = i;
			back[i] = h;
		}
		
		private int maxInSubtree(int h, int i) {
			int c1 = firstChild(h);
			
			if (c1 < size && value[heap[c1]] > value[i]) {
				i = heap[c1];
				h = c1;
			}
			
			int c2 = c1 + 1;
			
			if (c2 < size && value[heap[c2]] > value[i]) {
				h = c2;
			}
			
			return h;
		}
		
		private static int firstChild(int h) {
			return 2 * h + 1;
		}
	}
}
