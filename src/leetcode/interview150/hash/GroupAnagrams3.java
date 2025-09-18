package leetcode.hash;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * The task solution using a custom hash table.
 * 
 * <p>Conceptually this is similar to the JCL <code>groupingBy</code>
 * solution, but we roll our own hash table.</p>
 * 
 * <p>When keeping the same anagram signature the performance is already
 * better than the majority of other solutions. However, we improve it even
 * further by changing the choice of the group signature. Since the string
 * characters are limited to lower-case English letters, we can use
 * the letter frequency table as a compact anagram group signature.</p>
 */
public class GroupAnagrams3 {

	/**
	 * Groups the strings in anagram groups.
	 * 
	 * @param array the string to be grouped
	 * @return the groups of anagram strings
	 */
	public static List<List<String>> groupAnagrams(String[] array) {
		var map = new AnagramMap(2 * array.length);
		
		for (var item: array) {
			map.add(item);
		}
		
		return map.values();
	}
	
	/**
	 * A hash table for anagram groups.
	 */
	private static final class AnagramMap {
		private Entry[] table;
		private List<List<String>> values;
		private static final int LETTERS = 'z' - 'a' + 1;
		
		/**
		 * Initializes the hash table.
		 * 
		 * @param capacity the hash table capacity
		 */
		public AnagramMap(int capacity) {
			table = new Entry[capacity];
			values = new LinkedList<>();
		}
		
		/**
		 * Adds a string to its anagram group in the hash table.
		 * 
		 * @param item the string to add
		 */
		public void add(String item) {
			var sig = new byte[LETTERS];
			
			for (int i = 0; i < item.length(); i++) {
				sig[item.charAt(i) - 'a']++;
			}
			
			int h = Arrays.hashCode(sig);
			int i = Math.floorMod(h, table.length);
			var entry = find(table[i], sig, h);
			
			if (entry == null) {
				entry = new Entry(sig, h, table[i]);
				table[i] = entry;
				values.add(entry.members);
			}
			
			entry.members.add(item);
		}
		
		private static Entry find(Entry entry, byte[] key, int hash) {
			while (entry != null &&
			       (entry.hash != hash || !Arrays.equals(entry.sig, key))) {
				entry = entry.next;
			}
			
			return entry;
		}
		
		/**
		 * Returns the list of anagram groups.
		 * 
		 * @return the list of anagram groups
		 */
		public List<List<String>> values() {
			return values;
		}
		
		private static final class Entry {
			private final byte[] sig;
			private final int hash;
			private List<String> members;
			private Entry next;
			
			public Entry(byte[] sorted, int h, Entry entry) {
				sig = sorted;
				hash = h;
				members = new LinkedList<String>();
				next = entry;
			}
		}
	}
}
