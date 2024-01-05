package leetcode.greedy;

/* Task 560. Subarray Sum Equals K */

import static java.nio.charset.StandardCharsets.US_ASCII;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Task solution.
 * 
 * <p>Let <i>P</i>[<i>i</i>] denote the sum of the first <i>i</i> elements
 * of the input array <i>A</i>. Then subarray <i>A</i>[<i>r</i> .. <i>q</i>] sum
 * equals <i>P</i>[<i>q</i> + 1] - <i>P</i>[<i>r</i>]. For a fixed index
 * <i>q</i> we would like to count how many values of index <i>r</i> there are such
 * that <i>P</i>[<i>q</i> + 1] - <i>P</i>[<i>r</i>] = <i>k</i>. Then we would
 * sum this count and get the total number of subarrays with the sum <i>k</i>.
 * </p>
 * 
 * <p>Such indices <i>r</i> are precisely those that have 
 * <i>P</i>[<i>q</i> + 1] - <i>k</i> = <i>P</i>[<i>r</i>].
 * The number of them equals the number of <i>P</i>[<i>r</i>] values for
 * 0 &le; <i>r</i> &le; <i>q</i> that equal <i>P</i>[<i>q</i> + 1] - <i>k</i>.
 * We can look up this number quickly if all the values of <i>P</i>[<i>r</i>] are
 * added into a dedicated data structure, for example
 * in a red-black tree or a hash table.</p>
 * 
 * <p>Here is a solution using the hash table. For every consequent value of
 * <i>q</i> the hash table stores all values of <i>P</i>[<i>r</i>]
 * for 0 &le; <i>r</i> &le; <i>q</i> and allows to query how many times
 * any particular value has been added to the hash table.</p>
 */
public class SubarraySum {

	/**
	 * Reads the array and the sum from the standard input and writes
	 * the number of subarrays having this sum to the standard output. 
	 * 
	 * @param args command line arguments (unused)
	 */
	public static void main(String[] args) {
		var reader = new InputStreamReader(System.in, US_ASCII);
		var bufferedReader = new BufferedReader(reader);
		var scanner = new Scanner(bufferedReader);
		var array = readArray(scanner);
		int sum = scanner.nextInt();
		int count = subarraySum(array, sum);
		System.out.println(count);
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
	 * Counts subarrays having the given sum.
	 * 
	 * @param nums the input array
	 * @param k the desired sum
	 * @return number of non-empty subarrays with the sum <i>k</i>
	 */
	public static int subarraySum(int[] nums, int k) {
		var prefixCount = new HashTable(2 * nums.length);
		int count = 0;
		int prefixSum = 0;
		prefixCount.add(0);
		
		for (int x: nums) {
			prefixSum += x;
			count += prefixCount.count(prefixSum - k);
			prefixCount.add(prefixSum);
		}
		
		return count;
	}
	
	/**
	 * A hash table implementation for the task.
	 * 
	 * <p>Uses universal hashing with chaining collision resolution.</p>
	 */
	private static final class HashTable {
		// This is a prime number.
		// There should be no more than PRIME possible key values.
		private static final int PRIME = 40000003;
		// Universal hash function coefficients.
		private long a, b; // long for no overflow multiplication
		
		private static class Entry {
			int key;
			int count;
			Entry next;
		}
		
		private Entry[] table;
		
		/**
		 * Creates an empty hash table.
		 * 
		 * @param capacity the hash table capacity
		 */
		public HashTable(int capacity) {
			table = new Entry[capacity];
			// choose the universal hash function coefficients
			var random = ThreadLocalRandom.current();
			a = random.nextInt(1, PRIME);
			b = random.nextInt(PRIME);
		}
		
		/**
		 * Searches the hash table for a key.
		 * 
		 * @param key the hash table key
		 * @return the number of keys with the given value in the hash table
		 */
		public int count(int key) {
			int h = hash(key);
			var entry = find(table[h], key);
			
			if (entry == null) {
				return 0;
			}
			
			return entry.count;
		}
		
		/**
		 * Adds a key to the hash table.
		 * 
		 * @param key a key, can be added multiple times
		 */
		public void add(int key) {
			int h = hash(key);
			var entry = find(table[h], key);
			
			if (entry == null) {
				entry = new Entry();
				entry.key = key;
				entry.count = 1;
				entry.next = table[h];
				table[h] = entry;
			} else {
				entry.count++;
			}
		}
		
		// universal hashing function
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
