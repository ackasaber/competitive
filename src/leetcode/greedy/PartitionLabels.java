package leetcode.greedy;

/* Task 763. Partition Labels */

import static java.nio.charset.StandardCharsets.US_ASCII;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Task solution.
 * 
 * <p>While the task description is quite scary, after some thinking you realize
 * that the solution structure isn't very complicated.</p>
 * 
 * <p>Notice that if a part contains a certain letter, then that part should end
 * no earlier than the last occurrence of that letter. Since there may be other
 * letters between the first occurrence and the last occurrence, the same
 * applies to the letters in between too. Eventually the letters in consideration
 * exhaust and a minimal-length part containing the starting letter is
 * established. We in principle can join this part with the preceding or the
 * following, but the task asks for a partition with the maximum number of parts,
 * therefore we don't join minimal-length parts.</p>
 * 
 * <p>We start from the first letter of the input string and recover minimal-length
 * parts from the beginning one-by-one and that would be the solution.</p>
 * 
 * <p>In order to quickly identify the last occurrence of a character we first
 * make a pass over the input string and store the position of each character
 * in a direct address hash table.</p>
 */
public class PartitionLabels {

	/**
	 * Reads the input string from the standard input and writes the maximum
	 * part partitioning to the standard output.
	 * 
	 * @param args command line arguments (unused)
	 * @throws IOException on reading I/O errors
	 */
	public static void main(String[] args) throws IOException {
		var reader = new InputStreamReader(System.in, US_ASCII);
		var bufferedReader = new BufferedReader(reader);
		var line = bufferedReader.readLine();
		var partLengths = partitionLabels(line);
		var writer = new OutputStreamWriter(System.out, US_ASCII);
		var bufferedWriter = new BufferedWriter(writer);
		var printer = new PrintWriter(bufferedWriter);
		reportLabelling(printer, line, partLengths);
		printer.flush();
	}

	/**
	 * Writes the partitioning. The parts are separated with a space.
	 * 
	 * @param printer where to write
	 * @param line the partitioning input string
	 * @param partLengths part lengths in order
	 */
	private static void reportLabelling(PrintWriter printer, String line, List<Integer> partLengths) {
		int i = 0;

		for (int partLength: partLengths) {
			var part = line.substring(i, i + partLength);
			printer.print(part);
			printer.print(' ');
			i += partLength;
		}
		
		printer.println();
	}
	
	/**
	 * Finds the maximum part partitioning of the string.
	 * 
	 * @param line the input string of lower-case English letters
	 * @return the list of part lengths in order
	 */
	public static List<Integer> partitionLabels(String line) {
		var lastLetter = new LetterMap();
		int n = line.length();
		
		for (int i = 0; i < n; i++) {
			lastLetter.set(line.charAt(i), i);
		}
		
		int i = 0;
		var lengths = new ArrayList<Integer>();
		
		while (i < n) {
			int k = lastLetter.get(line.charAt(i));
			int j = i + 1;
			
			while (j <= k) {
				int currentLast = lastLetter.get(line.charAt(j));
				
				if (currentLast > k) {
					k = currentLast;
				}
				
				j++;
			}
			
			lengths.add(j - i);
			i = j;
		}
		
		return lengths;
	}

	/**
	 * Direct access hash table for English letters.
	 */
	private static class LetterMap {
		private static final int LETTER_COUNT = 'z' - 'a' + 1;
		private int[] map = new int[LETTER_COUNT];
		
		/**
		 * Sets the hash table entry value.
		 * 
		 * @param key the hash table entry key
		 * @param value the hash table entry value
		 */
		public void set(char key, int value) {
			map[key - 'a'] = value;
		}
		
		/**
		 * Returns the stored hash table entry value.
		 * 
		 * @param key the hash table key
		 * @return the corresponding hash table entry value
		 */
		public int get(char key) {
			return map[key - 'a'];
		}
	}
}
