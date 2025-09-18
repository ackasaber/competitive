package leetcode.hash;

/* Task 49. Group Anagrams */

import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The task solution using Java Stream API.
 * 
 * <p>The <code>groupingBy</code> collector seems to be specifically
 * created for this use case. As the map key we use the sorted sequence
 * of string characters which is the same for all strings in an anagram
 * group.</p>
 */
public class GroupAnagrams1 {

	/**
	 * Groups the strings in anagram groups.
	 * 
	 * @param array the string to be grouped
	 * @return the groups of anagram strings
	 */
	public static List<List<String>> groupAnagrams(String[] array) {
		var map = Arrays.stream(array)
		                .collect(groupingBy(AnagramSignature::new));
		var list = new ArrayList<>(map.values());
		return list;
	}
	
	/**
	 * Anagram group signature class. Each distinct anagram group can be
	 * identified by the sorted sequence of its characters. Here we use it
	 * as the unique anagram group signature.
	 */
	private static final class AnagramSignature {
		private final byte[] sig;
		
		/**
		 * Creates the anagram group signature from the string.
		 * 
		 * @param s the source string
		 */
		public AnagramSignature(String s) {
			sig = s.getBytes(US_ASCII);
			Arrays.sort(sig);
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(sig);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			AnagramSignature other = (AnagramSignature) obj;
			return Arrays.equals(sig, other.sig);
		}
	}
}
