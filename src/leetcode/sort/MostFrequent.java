package leetcode.sort;

/* Task 347. Top K Frequent Elements */

import static java.nio.charset.StandardCharsets.US_ASCII;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The task solution.
 * 
 * <p>Find the element frequencies using a hash table and then use
 * a quickselect algorithm to partition the result.</p>
 */
public class MostFrequent {

	/**
	 * Reads an integer array from the standard input and the number <i>k</i>
	 * and writes <i>k</i> most frequent numbers to the standard output. 
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
		var array = new int[n];
		
		for (int i = 0; i < n; i++) {
			array[i] = scanner.nextInt();
		}
		
		var top = topKFrequent(array, k);
		var writer = new OutputStreamWriter(System.out, US_ASCII);
		var bufferedWriter = new BufferedWriter(writer);
		var printer = new PrintWriter(bufferedWriter);
		
		for (int x: top) {
			printer.print(x);
			printer.print(' ');
		}
		
		printer.println();
		printer.flush();
	}

	/**
	 * Finds the <i>k</i> most frequent numbers in the array.
	 * 
	 * @param nums the array
	 * @param k the number of most frequent elements to find
	 * @return the array of <i>k</i> most frequent elements. 
	 */
	public static int[] topKFrequent(int[] nums, int k) {
		var table = new FrequencyTable(nums.length);
		
		for (int i = 0; i < nums.length; i++) {
			table.add(nums[i]);
		}
		
		var freq = table.frequencies();
		quickSelect(freq, k);
		var result = new int[k];
		
		for (int i = 0; i < k; i++) {
			result[i] = freq[i].key;
		}
		
		return result;
	}

	// Only used to return a pair of numbers
	private static class Range {
		int start;
		int end;
		
		public Range(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}
	
	/**
	 * Partition the frequency entry array so that first go <i>k</i> &minus; 1
	 * most frequent elements, then the element with the frequency rank <i>k</i>
	 * and then the rest.
	 * 
	 * @param freq the array of frequency entries
	 * @param k the frequency rank to partition on
	 */
	private static void quickSelect(Entry[] freq, int k) {
		int p = 0;
		int q = freq.length;
		
		while (q - p > 1) {
			var equals = randomPartition(freq, p, q);
			
			if (equals.start - p < k &&
			    k <= equals.end - p) {
				return;
			}
			
			if (k > equals.end - p) {
				k -= equals.end - p;
				p = equals.end;
			} else {
				q = equals.start;
			}
		}
	}
	
	/**
	 * Partition the subarray on a random pivot element.
	 * 
	 * @param freq the array
	 * @param p the subarray first index
	 * @param q the subarray one-past-end index 
	 * @return the range of equal frequency pivot elements
	 */
	private static Range randomPartition(Entry[] freq, int p, int q) {
		var random = ThreadLocalRandom.current();
		int t = random.nextInt(p, q);
		return partition(freq, p, q, freq[t]);
	}
	
	/**
	 * Partitions the subarray on a given pivot element.
	 * 
	 * @param freq the array
	 * @param p the subarray first index
	 * @param q the subarray one-past-end index
	 * @param pivot the pivot element
	 * @return the range of equal frequency pivot elements
	 */
	private static Range partition(Entry[] freq, int p, int q, Entry pivot) {
		int s = p;
		int t = p;
		
		for (int j = p; j < q; j++) {
			var item = freq[j];
			
			if (item.freq > pivot.freq) {
				freq[j] = freq[t];
				freq[t] = freq[s];
				freq[s] = item;
				s++;
				t++;
			} else if (item.freq == pivot.freq) {
				freq[j] = freq[t];
				freq[t] = item;
				t++;
			}
		}
		
		return new Range(s, t);
	}
	
	/**
	 * A frequency table entry.
	 * 
	 * <p>For simplicity we reuse this both for the hash table implementation
	 * and when partitioning data, although in the latter case the <code>
	 * next</code> field is unused.</p> 
	 */
	private static final class Entry {
		int key;
		int freq;
		Entry next;
	}
	
	/**
	 * A hand-written hash table to compute element frequencies.
	 */
	private static final class FrequencyTable {
		// Prime and greater than the number of possible keys.
		private static final int PRIME = 20011;
		private int a, b;
		private Entry[] table;
		private ArrayList<Entry> values;
		
		public FrequencyTable(int capacity) {
			table = new Entry[capacity];
			values = new ArrayList<>();
			var random = ThreadLocalRandom.current();
			a = random.nextInt(1, PRIME);
			b = random.nextInt(PRIME);
		}
		
		private int hash(int key) {
			return Math.floorMod(a * key + b, PRIME) % table.length;
		}
		
		public void add(int key) {
			int h = hash(key);
			var entry = findEntry(table[h], key);
			
			if (entry == null) {
				entry = new Entry();
				entry.key = key;
				entry.freq = 1;
				entry.next = table[h];
				table[h] = entry;
				values.add(entry);
			} else {
				entry.freq++;
			}
		}
		
		private static Entry findEntry(Entry entry, int key) {
			while (entry != null && entry.key != key) {
				entry = entry.next;
			}
			
			return entry;
		}
		
		public Entry[] frequencies() {
			var entries = new Entry[values.size()];
			return values.toArray(entries);
		}
	}
}
