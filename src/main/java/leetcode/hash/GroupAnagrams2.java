package leetcode.hash;

/* Task 49. Group Anagrams */

import static java.nio.charset.StandardCharsets.US_ASCII;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The task solution using sort.
 * 
 * <p>Another approach to the problem is sorting the strings by their
 * anagram group signature. Then anagram groups will go continuously in the
 * list and it's left just to assemble them in one pass.</p>
 * 
 * <p>In practice this performed about as good as the JCL <code>groupingBy</code>
 * solution.</p>
 */
public class GroupAnagrams2 {

	/**
	 * Groups the strings in anagram groups.
	 * 
	 * @param array the string to be grouped
	 * @return the groups of anagram strings
	 */
	public static List<List<String>> groupAnagrams(String[] array) {
		int n = array.length;
		var anagramEntries = new AnagramEntry[n];
		
		for (int i = 0; i < n; i++) {
			anagramEntries[i] = new AnagramEntry(array[i]);
		}
		
		Arrays.sort(anagramEntries);
		var result = new ArrayList<List<String>>();
		int i = 0;
		
		while (i < n) {
			var entry = anagramEntries[i];
			var group = new ArrayList<String>();
			
			do {
				group.add(anagramEntries[i].toString());
				i++;
			} while (i < n && entry.compareTo(anagramEntries[i]) == 0);
			
			result.add(group);
		}
		
		return result;
	}
	
	/**
	 * A string entry sortable by the string anagram group signature.
	 */
	private static final class AnagramEntry implements Comparable<AnagramEntry>{
		private final byte[] sig;
		private final String original;
		
		/**
		 * Creates a string entry.
		 * 
		 * @param s the string
		 */
		public AnagramEntry(String s) {
			original = s;
			sig = s.getBytes(US_ASCII);
			Arrays.sort(sig);
		}

		@Override
		public int compareTo(AnagramEntry entry) {
			return Arrays.compare(sig, entry.sig);
		}
		
		/**
		 * Returns the original string.
		 * 
		 * @return the original string
		 */
		@Override
		public String toString() {
			return original;
		}
	}
}
