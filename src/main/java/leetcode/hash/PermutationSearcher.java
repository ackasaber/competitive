package leetcode.hash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermutationSearcher {

	public static void main(String[] args) {
		var words1 = new String[] { "foo", "bar" };
		System.out.println(findSubstring("barfoothefoobarman", words1));
		
		var words2 = new String[] { "word", "good", "best", "word" };
		System.out.println(findSubstring("wordgoodgoodgoodbestword", words2));
		
		var words3 = new String[] { "bar", "foo", "the" };
		System.out.println(findSubstring("barfoofoobarthefoobarman", words3));
		
		var words4 = new String[] { "aa", "aa" };
		System.out.println(findSubstring("aaaaaaaaaaaaaa", words4));
		
		var words5 = new String[] { "bbb", "bbb" };
		System.out.println(findSubstring("b", words5));
	}

	public static List<Integer> findSubstring(String s, String[] words) {
		var haystack = new EnglishString(s);
		var needles = new EnglishStringSet(words);
		var searcher = new PermutationSearcher(haystack, needles, words[0].length());
		searcher.search();
		return searcher.result();
	}
	
	private EnglishStringSet words;
	private List<Integer> positions;
	// Index of a word from the needles that starts at the given haystack index.
	private int[] indexed;
	private int wordLength;
	
	public PermutationSearcher(EnglishString haystack, EnglishStringSet needles, int wordLength) {
		words = needles;
		positions = new ArrayList<>();
		this.wordLength = wordLength;
		indexed = new int[Math.max(haystack.length() - wordLength + 1, 0)];
		
		for (int i = 0; i < indexed.length; i++) {
			var word = new EnglishString(haystack, i, wordLength);
			indexed[i] = words.index(word);
		}
	}
	
	public void search() {
		// Make sure that startShift a valid index.
		if (wordLength <= indexed.length) {
			for (int startShift = 0; startShift < wordLength; startShift++) {
				addSolutionsFor(startShift);
			}
		}
	}
	
	public List<Integer> result() {
		return positions;
	}

	// Add all solutions of the form startShift + k * wordLength.
	private void addSolutionsFor(int startShift) {
		int windowStart = startShift;
		int windowEnd = windowStart;
		
		// [windowStart; windowEnd[ is a valid prefix of the solution
		while (windowEnd < indexed.length) {
			int nextWord = indexed[windowEnd];
			
			if (words.contains(nextWord)) {
				windowEnd += wordLength;
				words.remove(nextWord);
				
				if (words.size() == 0)
					positions.add(windowStart);
			} else if (windowStart == windowEnd) {
				windowStart += wordLength;
				windowEnd = windowStart;
			} else {
				words.add(indexed[windowStart]);
				windowStart += wordLength;
			}
		}
		
		// restore words for the next call
		while (windowStart < windowEnd) {
			words.add(indexed[windowStart]);
			windowStart += wordLength;
		}
	}
	
	private final static class EnglishStringSet {
		// each element index
		private Map<EnglishString, Integer> index;
		// number of duplicates for index
		private int[] count;
		// total number of elements
		private int size;
	
		public EnglishStringSet(EnglishString[] words) {
			index = new HashMap<>();
			size = words.length;
			var wordIndex = new int[size];
			
			for (int i = 0; i < size; i++)
				wordIndex[i] = index.computeIfAbsent(words[i], k -> index.size());
			
			count = new int[index.size()];
			
			for (int index : wordIndex) {
				count[index]++;
			}
		}
		
		public EnglishStringSet(String[] words) {
			this(compress(words));
		}
		
		private static EnglishString[] compress(String[] words) {
			var result = new EnglishString[words.length];
			
			for (int i = 0; i < words.length; i++)
				result[i] = new EnglishString(words[i]);
			
			return result;
		}
		
		public int index(EnglishString s) {
			return index.getOrDefault(s, -1);
		}
		
		public boolean contains(int index) {
			return index != -1 && count[index] != 0;
		}
		
		public void remove(int index) {
			count[index]--;
			size--;
		}
		
		public void add(int index) {
			count[index]++;
			size++;
		}
		
		public int size() {
			return size;
		}
		
		@Override
		public String toString() {
			var buffer = new StringBuilder();
			buffer.append('{');
			var entries = index.entrySet();
			var iter = entries.iterator();
			
			if (iter.hasNext()) {
				var entry = iter.next();
				buffer.append(entry.getKey());
				buffer.append(": ");
				buffer.append(count[entry.getValue()]);
			}
			
			while (iter.hasNext()) {
				var entry = iter.next();
				buffer.append(", ");
				buffer.append(entry.getKey());
				buffer.append(": ");
				buffer.append(count[entry.getValue()]);
			}
			
			buffer.append('}');
			return buffer.toString();
		}
	}
	
	// String of lower-case English letters.
	private final static class EnglishString {
		private final byte[] source;
		private final int start;
		private final int end;
		private final int hash;
		
		public EnglishString(EnglishString s, int start, int len) {
			source = s.source;
			this.start = start;
			end = start + len;
			hash = precomputeHash(source, start, end);
		}
		
		public EnglishString(String s) {
			source = compress(s);
			start = 0;
			end = source.length;
			hash = precomputeHash(source, start, end);
		}

		public int length() {
			return end - start;
		}

		private static int precomputeHash(byte[] source, int start, int end) {
			final int LETTERS = 'z' - 'a' + 1;
			int hash = 0;
			
			for (int i = start; i < end; i++)
				hash = hash * LETTERS + source[i];
			
			return hash;
		}

		@Override
		public int hashCode() {
			return hash;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj instanceof EnglishString other) {
				return hash == other.hash &&
						Arrays.equals(source, start, end,
								other.source, other.start, other.end);
			}
			return false;
		}
		
		@Override
		public String toString() {
			var buffer = new StringBuilder();
			
			for (int i = start; i < end; i++)
				buffer.append((char)('a' + source[i]));
			
			return buffer.toString();
		}
		
		// Convert the string of English lower case letters to a more
		// economic encoding.
		private static byte[] compress(String s) {
			var a = new byte[s.length()];
			
			for (int i = 0; i < s.length(); i++)
				a[i] = (byte) (s.charAt(i) - 'a');
			
			return a;
		}
	}
}
