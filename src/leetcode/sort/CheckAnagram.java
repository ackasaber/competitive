package leetcode.sort;

/* Task 242. Valid Anagram */

import static java.nio.charset.StandardCharsets.US_ASCII;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * The task solution. Just sort the characters and then compare.
 */
public class CheckAnagram {

	/**
	 * Reads two string from the standard input and if they are anagrams
	 * to the standard output.
	 * 
	 * @param args command line arguments (unused)
	 * @throws IOException on reading I/O errors
	 */
	public static void main(String[] args) throws IOException {
		var reader = new InputStreamReader(System.in, US_ASCII);
		var bufferedReader = new BufferedReader(reader);
		var s = bufferedReader.readLine();
		var t = bufferedReader.readLine();
		
		if (isAnagram(s, t)) {
			System.out.println("YES");
		} else {
			System.out.println("NO");
		}
	}

	/**
	 * Checks if the two strings are anagrams.
	 * 
	 * @param s the first string
	 * @param t the second string
	 * @return <code>true</code> if the strings are anagrams,
	 * <code>false</code> otherwise
	 */
	public static boolean isAnagram(String s, String t) {
		var sbytes = s.getBytes(US_ASCII);
		var tbytes = t.getBytes(US_ASCII);
		Arrays.sort(sbytes);
		Arrays.sort(tbytes);
		return Arrays.equals(sbytes, tbytes);
	}
}
