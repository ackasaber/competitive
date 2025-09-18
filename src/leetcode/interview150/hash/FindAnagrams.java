package leetcode.hash;

/* Task 438. Find All Anagrams in a String */

import static java.nio.charset.StandardCharsets.US_ASCII;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The task solution.
 * 
 * <p>We can count letter frequencies of each candidate substring of the first
 * string and compare them with the letter frequencies of the second string.
 * Letter frequencies of the consequent candidate substrings differ only
 * by two letters which we use to update the letter frequencies efficiently.
 * Also, we don't have to start over comparing all frequencies, we can just
 * keep track of the number of matched letters from the second string.
 * This allows for a very efficient solution.</p>
 */
public class FindAnagrams {

	/**
	 * Reads two string from the standard input and reports the parts
	 * of the first string that are anagrams of the second string to the
	 * standard output.
	 * 
	 * @param args command line arguments (unused)
	 * @throws IOException on reading I/O errors
	 */
	public static void main(String[] args) throws IOException {
		var reader = new InputStreamReader(System.in, US_ASCII);
		var bufferedReader = new BufferedReader(reader);
		var line = bufferedReader.readLine();
		var anagram = bufferedReader.readLine();
		var positions = findAnagrams(line, anagram);
		var writer = new OutputStreamWriter(System.out, US_ASCII);
		var bufferedWriter = new BufferedWriter(writer);
		var printer = new PrintWriter(bufferedWriter);
		reportAnagrams(printer, line, positions, anagram.length());
		printer.flush();
	}

	/**
	 * Writes the partitioning. The parts are separated with a space.
	 * 
	 * @param printer where to write
	 * @param line the partitioning input string
	 * @param partLengths part lengths in order
	 */
	private static void reportAnagrams(PrintWriter printer,
	                                   String line,
	                                   List<Integer> positions,
	                                   int length) {
		Collections.sort(positions);
		
		for (int pos: positions) {
			printer.print(pos);
			printer.print(" \"");
			printer.print(line.substring(pos, pos + length));
			printer.println('"');
		}
	}
	
	/**
	 * Finds anagrams of the given string in the other string.
	 * 
	 * @param line the heystack string
	 * @param anagram the niddle string
	 * @return the list of substring starting indices
	 */
	public static List<Integer> findAnagrams(String line, String anagram) {
		var anagrammer = new Anagrammer(anagram);
		var positions = new ArrayList<Integer>();
		int n = line.length();
		int m = anagram.length();
		
		if (m <= n) {
			for (int i = 0; i < m; i++) {
				anagrammer.add(line.charAt(i));
			}
			
			if (anagrammer.matches()) {
				positions.add(0);
			}
			
			for (int i = m; i < n; i++) {
				anagrammer.add(line.charAt(i));
				anagrammer.remove(line.charAt(i - m));
				
				if (anagrammer.matches()) {
					positions.add(i - m + 1);
				}
			}
		}
		
		return positions;
	}

	/**
	 * A specialized data structure to keep track of matched anagram letters.
	 */
	private static final class Anagrammer {
		private static final int LETTER_COUNT = 'z' - 'a' + 1;
		private int[] anagramCount;
		private int[] count;
		private int length;
		private int matched;
		
		/**
		 * Constructs an anagram matcher for the given string.
		 * 
		 * @param anagram the niddle string
		 */
		public Anagrammer(String anagram) {
			anagramCount = new int[LETTER_COUNT];
			count = new int [LETTER_COUNT];
			matched = 0;
			length = anagram.length();
			
			for (int i = 0; i < length; i++) {
				anagramCount[anagram.charAt(i) - 'a']++;
			}
		}
		
		/**
		 * Adds a letter to the currently tracked substring.
		 * 
		 * @param c the letter to add
		 */
		public void add(char c) {
			int i = c - 'a';
			count[i]++;
			
			// a new letter matches the anagram
			if (count[i] <= anagramCount[i]) {
				matched++;
			}
		}
		
		/**
		 * Removes a letter from the currently tracked substring.
		 * 
		 * @param c the existing letter to remove
		 */
		public void remove(char c) {
			int i = c - 'a';
			
			// we're removing a letter that's matching the anagram
			if (count[i] <= anagramCount[i]) {
				matched--;
			}
			
			count[i]--;
		}
		
		/**
		 * Checks if the currently tracked substring matches the anagram.
		 * 
		 * @return <code>true</code> if it matches, <code>false</code> otherwise
		 */
		public boolean matches() {
			return matched == length;
		}
	}
}
