package leetcode.sort;

/* Task 18. Four Sum */

import static java.nio.charset.StandardCharsets.US_ASCII;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * The task solution via sorting.
 * 
 * <p>Look through the possible values of the first three numbers and look up
 * the forth via binary search.</p>
 */
public class FourSum {

	/**
	 * Reads the array and the sum from the standard input and writes
	 * all possible quadruples of the array elements that give the sum
	 * to the standard output. 
	 * 
	 * @param args command line arguments (unused)
	 */
	public static void main(String[] args) {
		var reader = new InputStreamReader(System.in, US_ASCII);
		var bufferedReader = new BufferedReader(reader);
		var scanner = new Scanner(bufferedReader);
		var array = readArray(scanner);
		int sum = scanner.nextInt();
		var fours = fourSum(array, sum);
		var writer = new OutputStreamWriter(System.out, US_ASCII);
		var bufferedWriter = new BufferedWriter(writer);
		var printWriter = new PrintWriter(bufferedWriter);
		reportFours(printWriter, fours);
		printWriter.flush();
	}

	private static int[] readArray(Scanner scanner) {
		int n = scanner.nextInt();
		var array = new int[n];
		
		for (int i = 0; i < n; i++) {
			array[i] = scanner.nextInt();
		}
		
		return array;
	}
	
	private static void reportFours(PrintWriter writer, List<List<Integer>> fours) {
		for (var four: fours) {
			writer.printf("%d %d %d %d%n",
			              four.get(0), four.get(1), four.get(2), four.get(3));
		}
	}
	
	/**
	 * Finds all possible unique quadruples of array elements that have the
	 * given sum.
	 * 
	 * @param nums the array
	 * @param sum the sum to find
	 * @return the list of quadruples, which contain array element values
	 * and have the given sum
	 */
	public static List<List<Integer>> fourSum(int[] nums, int sum) {
		Arrays.sort(nums);
		var result = new LinkedList<List<Integer>>();
		int n = nums.length;
		
		for (int i = 0; i < n - 3; i = nextValue(nums, i, n - 3)) {
			for (int j = i + 1; j < n - 2; j = nextValue(nums, j, n - 2)) {
				// One past the rightest possible position for the fourth number.
				int rightest = n;
				
				for (int k = j + 1; k < rightest - 1; k = nextValue(nums, k, rightest - 1)) {
					long lastValue = (long) sum - nums[i] - nums[j] - nums[k];
					int m;
					
					if (lastValue < Integer.MIN_VALUE) {
						m = k + 1;
					} else if (lastValue > Integer.MAX_VALUE) {
						m = rightest;
					} else {
						m = binarySearch(nums, (int) lastValue, k + 1, rightest);
					}
					
					if (m < rightest && nums[m] == lastValue) {
						var list = List.of(nums[i], nums[j], nums[k], nums[m]);
						result.add(list);
					}
					
					// The next value of nums[k] will be greater than the current
					// and the last number should be lesser than what we
					// have found so far. We used the binary search variation
					// that finds the first occurrence of the searched value,
					// if there are several, thus we effectively exclude
					// the found value from the consideration.
					rightest = m;
				}
				
				// We've added all the solutions with the current combination
				// of the first number nums[i] and the second number nums[j],
				// therefore we can skip the same combinations of the two first
				// numbers.
			}
			
			// We've added all the solutions with the first number nums[i],
			// therefore can skip its equals.
		}
		
		return result;
	}
	
	/**
	 * Given the sorted array, searches for the given value in its subarray.
	 * 
	 * @param nums the array
	 * @param value the value to find
	 * @param left the subarray starting index, inclusive
	 * @param right the subarray ending index, exclusive
	 * @return the minimum index <code>left</code> &le; <i>i</i> &lt; <code>right</code>
	 * such that <code>nums</code>[<i>i</i>] = <code>value</code> or, if the
	 * <code>value</code> is not present in the subarray, the index of the first
	 * greater element; if all elements in the subarray are lesser, returns
	 * <code>right</code>
	 */
	private static int binarySearch(int[] nums, int value, int left, int right) {
		while (left < right) {
			int middle = (left + right) / 2;
			
			if (nums[middle] < value) {
				left = middle + 1;
			} else {
				right = middle;
			}
		}

		return left;
	}
	
	/**
	 * Moves the index to the next distinct value.
	 * 
	 * @param nums the array
	 * @param i the valid array index
	 * @param n the array length limit
	 * @return the next distinct value index
	 */
	private static int nextValue(int[] nums, int i, int n) {
		int current = nums[i];
		
		do {
			i++;
		} while (i < n && nums[i] == current);
		
		return i;
	}
}
