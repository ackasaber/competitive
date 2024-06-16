package leetcode.greedy;

/* Task 424. Longest Repeating Character Replacement */

import static java.nio.charset.StandardCharsets.US_ASCII;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * The task solution.
 */
public class LongestRepeat {

	/**
	 * Reads the input string from the standard input and writes the maximum
	 * part partitioning to the standard output.
	 * 
	 * @param args command line arguments (unused)
	 * @throws IOException on reading I/O errors
	 */
	public static void main(String[] args) {
		var reader = new InputStreamReader(System.in, US_ASCII);
		var bufferedReader = new BufferedReader(reader);
		@SuppressWarnings("resource")
		var scanner = new Scanner(bufferedReader);
		var line = scanner.nextLine();
		int k = scanner.nextInt();
		System.out.println(characterReplacement(line, k));
	}

	/**
	 * Computes the longest length of a substring that can be filled with
	 * a single letter by no more than <i>k</i> corrections.
	 * 
	 * @param s the source string
	 * @param k the corrections limit
	 * @return the maximum length
	 */
	public static int characterReplacement(String s, int k) {
		var table = new LetterFrequencyTable();
		int length = 0; // window length
		int maxFreq = 0; // historical maximum letter frequency in the window
		
		for (int i = 0; i < s.length(); i++) {
			// i is the new end of the window
			int freq = table.increase(s.charAt(i));
			
			if (freq > maxFreq) {
				maxFreq = freq;
			}
			
			if (length + 1 - maxFreq <= k) {
				// The window allows one more letter in.
				length++;
			} else {
				// 1. While maxFreq is not necessarily the maximum frequency
				// of a letter in the current window, it's no less. Therefore
				// length + 1 - realMaxFreq >= length + 1 - maxFreq > k, that is,
				// there can be no window with the current length starting at
				// s[i - length] that allows no more than k corrections to be
				// filled with a single letter.
				//
				// 2. Since there exists a window of the current length that
				// allows no more than k corrections to be filled with a single
				// letter, and we want to find the maximum length, we are not
				// interested in windows starting at s[i - length] anymore.
				// We move the window one letter right.
				//
				// Notice how the historical maximum frequency relates to the
				// window length in this case: length = maxFreq + k. The window
				// will continue moving right one letter until there is a
				// letter that beats maxFreq; only then can we increase the
				// window size.
				table.decrease(s.charAt(i - length));
			}
		}
		
		return length;
	}
	
	/**
	 * English capital letter frequency table.
	 */
	private static final class LetterFrequencyTable {
		private static final int LETTER_COUNT = 'Z' - 'A' + 1;
		private int[] table;
		
		/**
		 * Creates the letter frequency table with all zero frequencies.
		 */
		public LetterFrequencyTable() {
			table = new int[LETTER_COUNT];
		}
		
		/**
		 * Increases the letter frequency by one.
		 * 
		 * @param c the letter
		 * @return the new letter frequency
		 */
		public int increase(char c) {
			return ++table[c - 'A'];
		}
		
		/**
		 * Decreases the letter frequency by one.
		 * 
		 * @param c the letter
		 */
		public void decrease(char c) {
			table[c - 'A']--;
		}
	}
}
