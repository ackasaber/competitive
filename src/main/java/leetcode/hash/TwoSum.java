package leetcode.hash;

/* Task 1. Two Sum */

import static java.nio.charset.StandardCharsets.US_ASCII;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The task solution.
 * 
 * <p>A simple solution with a hash table. A more elaborate solution would
 * sort the array and use binary search to locate the second element of the
 * pair.</p>
 */
public class TwoSum {

	/**
	 * Reads the array and the sum from the standard input and writes
	 * the indices of two elements that have that sum to the standard output. 
	 * 
	 * @param args command line arguments (unused)
	 */
	public static void main(String[] args) {
		var reader = new InputStreamReader(System.in, US_ASCII);
		var bufferedReader = new BufferedReader(reader);
		var scanner = new Scanner(bufferedReader);
		var array = readArray(scanner);
		int sum = scanner.nextInt();
		var indices = twoSum(array, sum);
		int i = indices[0];
		int j = indices[1];
		System.out.printf("A[%d] = %d, A[%d] = %d%n",
		                  i, array[i], j, array[j]);
	}

	private static int[] readArray(Scanner scanner) {
		int n = scanner.nextInt();
		var array = new int[n];
		
		for (int i = 0; i < n; i++) {
			array[i] = scanner.nextInt();
		}
		
		return array;
	}
	
	/**
	 * Finds the two array elements with the given sum.
	 * 
	 * @param nums the input array
	 * @param sum the desired sum
	 * @return a pair of array indices that point to the necessary elements
	 */
	public static int[] twoSum(int[] nums, int sum) {
		int n= nums.length;
		var table = new HashTable(2 * n);
		table.put(nums[0], 0);
		
		for (int i = 1; i < n; i++) {
			int j = table.find(sum - nums[i]);
		
			if (j != -1) {
				return new int[] { j, i };
			}
			
			table.put(nums[i], i);
		}
		
		// impossible according to the task description
		return null;
	}
	
	/**
	 * A hash table implementation: universal hashing and chaining for
	 * collision resolution.
	 */
	private static final class HashTable {
		// Should be prime and greater than the number of possible keys.
		private static final int PRIME = 2000000011;
		private Entry[] table;
		private long a, b;
		
		private static final class Entry {
			int key;
			int value;
			Entry next;
		}
		
		/**
		 * Constructs an empty hash table.
		 * 
		 * @param capacity the hash table capacity
		 */
		public HashTable(int capacity) {
			table = new Entry[capacity];
			var random = ThreadLocalRandom.current();
			a = random.nextInt(1, PRIME);
			b = random.nextInt(PRIME);
		}
		
		/**
		 * Inserts a new key with the given value into the hash table
		 * or, if it's an existing key, changes the corresponding value.
		 * 
		 * @param key the hash table key
		 * @param value the associated value
		 */
		public void put(int key, int value) {
			int h = hash(key);
			var entry = find(table[h], key);
			
			if (entry == null) {
				entry = new Entry();
				entry.key = key;
				entry.next = table[h];
				table[h] = entry;
			}
			
			entry.value = value;
		}
		
		/**
		 * Searches for the key in the hash table.
		 * 
		 * @param key the searched key
		 * @return the associated value or -1 if the key is not found
		 */
		public int find(int key) {
			int h = hash(key);
			var entry = find(table[h], key);
			
			if (entry == null) {
				return -1;
			}
			
			return entry.value;
		}
		
		private int hash(int key) {
			return Math.floorMod(a * key + b, PRIME) % table.length;
		}

		private static Entry find(Entry entry, int key) {
			while (entry != null && entry.key != key) {
				entry = entry.next;
			}
			
			return entry;
		}
	}
}
