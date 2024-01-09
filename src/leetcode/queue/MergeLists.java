package leetcode.queue;

/* Task 23. Merge k Sorted Lists */

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
 * <p>Organize the array of the given lists into a non-decreasing heap.
 * Then remove elements one by one from the root list of the heap.
 * When removing the last element of the root list, remove the root list from
 * the heap. Otherwise, restore the heap property in the root.</p>
 */
public class MergeLists {
	public static final class ListNode {
		int val;
		ListNode next;

		ListNode(int val, ListNode next) {
			this.val = val;
			this.next = next;
		}
	}

	/**
	 * Reads the lists from the standard input, merges them into a single list
	 * and writes the result to the standard output.
	 * 
	 * @param args command line arguments (unused)
	 */
	public static void main(String[] args) {
		var reader = new InputStreamReader(System.in, US_ASCII);
		var bufferedReader = new BufferedReader(reader);
		var scanner = new Scanner(bufferedReader);
		var lists = readLists(scanner);
		var merged = mergeKLists(lists);
		var writer = new OutputStreamWriter(System.out, US_ASCII);
		var bufferedWriter = new BufferedWriter(writer);
		var printWriter = new PrintWriter(bufferedWriter);
		printList(printWriter, merged);
		printWriter.flush();
	}

	private static ListNode[] readLists(Scanner scanner) {
		int n = scanner.nextInt();
		var lists = new ListNode[n];
		
		for (int i = 0; i < n; i++) {
			int nk = scanner.nextInt();
			ListNode head = null;
			
			if (nk > 0) {
				head = new ListNode(scanner.nextInt(), null);
				ListNode tail = head;
				
				for (int j = 1; j < nk; j++) {
					var newNode = new ListNode(scanner.nextInt(), null);
					tail.next = newNode;
					tail = newNode;
				}
			}
			
			lists[i] = head;
		}
		
		return lists;
	}
	
	private static void printList(PrintWriter writer, ListNode list) {
		while (list != null) {
			writer.print(list.val);
			writer.print(' ');
			list = list.next;
		}
		
		writer.println();
	}
	
	/**
	 * Merges lists.
	 * 
	 * @param lists the list of lists
	 * @return the single, merged list
	 */
	public static ListNode mergeKLists(ListNode[] lists) {
		// First move non-empty lists to the front and count their number.
		int n = 0;
		
		for (int i = 0; i < lists.length; i++) {
			if (lists[i] != null) {
				var t = lists[i];
				lists[i] = lists[n];
				lists[n] = t;
				n++;
			}
		}
		
		if (n == 0) {
			return null;
		}
		
		// Build the heap.
		var queue = new ListPriorityQueue(lists, n);
		// Start from the first element.
		var head = queue.poll();
		var tail = head;
		
		// Add the rest.
		while (!queue.isEmpty()) {
			var min = queue.poll();
			tail.next = min;
			tail = min;
		}
		
		return head;
	}
	
	/**
	 * A non-decreasing heap of lists. Lists are compared by their head
	 * element value.
	 */
	private static final class ListPriorityQueue {
		private ListNode[] heap;
		private int size;
		
		/**
		 * Builds the heap from the given array of lists (in-place).
		 * 
		 * @param array the array of lists
		 * @param n consider just so many first elements of the array
		 */
		public ListPriorityQueue(ListNode[] array, int n) {
			heap = array;
			size = n;
			
			// build the heap
			for (int i = n / 2 - 1; i >= 0; i--) {
				increaseKey(i, heap[i]);
			}
		}
		
		private static int firstChild(int i) {
			return i * 2 + 1;
		}
		
		/**
		 * Extracts the minimum list node from all lists.
		 * 
		 * @return the list node, the <code>next</code> reference is <code>null</code>
		 */
		public ListNode poll() {
			var min = heap[0];
			
			if (min.next == null) {
				// remove the whole list entry
				size--;
				increaseKey(0, heap[size]);
			} else {
				// remove the first list element
				heap[0] = min.next;
				increaseKey(0, heap[0]);
				min.next = null;
			}
			
			return min;
		}
		
		/**
		 * Checks if the heap is empty.
		 * 
		 * @return <code>true</code> if the heap is empty,
		 * <code>false</code> otherwise
		 */
		public boolean isEmpty() {
			return size == 0;
		}
		
		private void increaseKey(int i, ListNode key) {
			int j = minInSubtree(i, key);
			
			while (j != i) {
				heap[i] = heap[j];
				i = j;
				j = minInSubtree(i, key);
			}
			
			heap[i] = key;
		}
		
		private int minInSubtree(int i, ListNode key) {
			int c1 = firstChild(i);
			
			if (c1 < size && heap[c1].val < key.val) {
				i = c1;
				key = heap[c1];
			}
			
			int c2 = c1 + 1;
			
			if (c2 < size && heap[c2].val < key.val) {
				i = c2;
			}
			
			return i;
		}
	}
}
