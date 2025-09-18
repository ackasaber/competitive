package leetcode.hash;

/* Task 49. Group Anagrams */

import static java.nio.charset.StandardCharsets.US_ASCII;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Scanner;

/**
 * The task solution.
 */
public class GroupAnagramsLauncher {

	/**
	 * Reads the array of string from the standard input and writes the
	 * anagram groups of these strings to the standard output. 
	 * 
	 * @param args command line arguments (unused)
	 */
	public static void main(String[] args) throws IOException {
		var reader = new InputStreamReader(System.in, US_ASCII);
		var bufferedReader = new BufferedReader(reader);
		var scanner = new Scanner(bufferedReader);
		var array = readArray(scanner);
		var writer = new OutputStreamWriter(System.out, US_ASCII);
		var bufferedWriter = new BufferedWriter(writer);
		var groups1 = GroupAnagrams1.groupAnagrams(array);
		writeGroups(bufferedWriter, "JCL solution", groups1);
		var groups2 = GroupAnagrams2.groupAnagrams(array);
		writeGroups(bufferedWriter, "Sorting solution", groups2);
		var groups3 = GroupAnagrams3.groupAnagrams(array);
		writeGroups(bufferedWriter, "Custom hash table solution", groups3);
		bufferedWriter.flush();
	}

	private static String[] readArray(Scanner scanner) {
		int n = scanner.nextInt();
		var array = new String[n];
		
		for (int i = 0; i < n; i++) {
			array[i] = scanner.next();
		}
		
		return array;
	}
	
	private static void writeGroups(BufferedWriter writer, String message,
	                                List<List<String>> groups) throws IOException {
		writer.write(" > " + message);
		writer.newLine();
		
		for (var group: groups) {
			for (var item: group) {
				writer.write(item);
				writer.write(' ');
			}
			
			writer.newLine();
		}
		
		writer.newLine();
	}
}
