package leetcode.greedy;

/* Task 977. Squares of a Sorted Array */

import static java.nio.charset.StandardCharsets.US_ASCII;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Task solution.
 *
 * <p>As it's seen from the example the catch is that there can be negative
 * numbers. It doesn't change much though &ndash; we just merge the squares of
 * the non-negative numbers with the squares of the negative numbers with
 * the latter ones coming in the reverse order.</p>
 */
public class SortedSquares {

	/**
	 * Reads the sorted integer array from the standard input and writes
	 * the sorted array of squared elements to the standard output. 
	 * 
	 * @param args command line arguments (unused)
	 */
	public static void main(String[] args) {
		var reader = new InputStreamReader(System.in, US_ASCII);
		var bufferedReader = new BufferedReader(reader);
		var scanner = new Scanner(bufferedReader);
		var array = readArray(scanner);
		var sorted = sortedSquares(array);
		var writer = new OutputStreamWriter(System.out, US_ASCII);
		var bufferedWriter = new BufferedWriter(writer);
		var printer = new PrintWriter(bufferedWriter);
		writeArray(printer, sorted);
		printer.flush();
	}

	private static int[] readArray(Scanner scanner) {
		int n = scanner.nextInt();
		var array = new int[n];
		
		for (int i = 0; i < n; i++) {
			array[i] = scanner.nextInt();
		}
		
		return array;
	}
	
	private static void writeArray(PrintWriter printer, int[] array) {
		for (int x: array) {
			printer.print(x);
			printer.print(' ');
		}
		
		printer.println();
	}
	
	/**
	 * Generates the sorted array of squares from the given sorted array.
	 * 
	 * @param nums the sorted integer array
	 * @return the sorted array of squares
	 */
	public static int[] sortedSquares(int[] nums) {
		int n = nums.length;
		int r = 0;
		
		while (r < n && nums[r] < 0) {
			r++;
		}
		
		var result = new int[n];
		fancyMerge(result, nums, 0, r, n - 1);
		
		for (int i = 0; i < n; i++) {
			result[i] *= result[i];
		}
		
		return result;
	}
	
	/**
	 * Merges the negative and non-negative parts of the sorted array into
	 * the sorted by absolute value array.
	 * 
	 * <p>Subarrays src[<i>p</i> .. <i>r</i> &minus; 1] and src[<i>r</i>
	 * .. <i>q</i>]
	 * are merged into the dest array and the first subarray goes in the
	 * reverse order.</p>
	 * <p>LeetCode users propose a more succinct solution with merging from
	 * the ends.</p> 
	 * 
	 * @param dest the destination array
	 * @param src the source array
	 * @param p the start of the negative part
	 * @param r the start of the non-negative part
	 * @param q the end of the non-negative part
	 */
	private static void fancyMerge(int[] dest, int[] src, int p, int r, int q) {
		int t = r - 1;
		int j = 0;
		
		while (t >= p && r <= q) {
			if (-src[t] < src[r]) {
				dest[j++] = src[t--];
			} else {
				dest[j++] = src[r++];
			}
		}
		
		while (t >= p) {
			dest[j++] = src[t--];
		}
		
		while (r <= q) {
			dest[j++] = src[r++];
		}
	}
}
