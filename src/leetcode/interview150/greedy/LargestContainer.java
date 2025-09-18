package leetcode.greedy;

/* Task 11. Container with Most Water */

import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.lang.Math.min;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * The task solution.
 * 
 * <p>There are certain properties of the solution that make it possible
 * to find the solution in linear time.</p>
 * 
 * <p>Let <i>i</i> be the starting index of the solution interval and <i>j</i>
 * be its ending index. Let also <i>h</i> be the water height in the resulting
 * container, that is, <i>h</i> = min(height[<i>i</i>], height[<i>j</i>]).
 * Consider elements height[<i>k</i>] for <i>k</i> &lt; <i>i</i>. Since the
 * resulting container has the maximum area, they all should be strictly less
 * than <i>h</i>. Otherwise, a container with larger width and the same or higher
 * water height can be built. Using the same logic for <i>k</i> &gt; <i>j</i>
 * height[<i>k</i>] &lt; <i>h</i> too. Given the definition of <i>h</i> we
 * conclude that height[<i>i</i>] strictly greater than the heights before it and
 * height[<i>j</i>] strictly greater than the heights after it. (Let's call this
 * property (*).)</p>
 * 
 * <p>This is the first basis for the solution. We don't have to consider
 * all possible starting and ending indices to check if they form the best
 * container. We can only limit these indices to the ones that satisfy property
 * (*).</p>
 * 
 * <p>The second basis for the solution is the fact that we don't have to look
 * through all combinations of possible starting and ending indices satisfying
 * property (*). Let's say we have an interval satisfying the
 * property (*). If the left height of the container is less than the
 * right height, all containers with the same left interval index and closer
 * right interval index that satisfy the property (*) ought to have smaller
 * area since the water height in this case doesn't change but the container
 * width only gets shorter. It means that if there is a container with a larger
 * area, its left height is higher than now. Therefore we can move to
 * the next container with the same right interval index and the next left
 * interval index satisfying the property (*).</p>
 * 
 * <p>If it's the right height of the container that's smaller, using the same
 * logic we move to the container with the same left interval index and the next
 * right interval index satisfying the property (*).</p>
 * 
 * <p>If however the left and right heights are equal, we can move both indexes.</p>
 * 
 * <p>We can start with the container formed by the first and the last heights
 * since it satisfies (*) and either is the solution or contains the solution
 * inside its interval.</p>
 * 
 * <p>Now we see that while moving the interval indices we never return them back.
 * This means that no more than <i>n</i> &minus; 1 index moves are sufficient
 * for finding the largest container, that is, the algorithm that uses these
 * considerations runs in O(<i>n</i>) time.</p>
 */
public class LargestContainer {

	/**
	 * Reads the heights array from the standard input and writes the maximum
	 * container area to the standard output. This is only for local
	 * testing, Leetcode needs only the method <code>maxArea</code>.
	 * 
	 * @param args command line arguments (unused)
	 */
	public static void main(String[] args) {
		var reader = new InputStreamReader(System.in, US_ASCII);
		var bufferedReader = new BufferedReader(reader);
		var scanner = new Scanner(bufferedReader);
		var height = readHeights(scanner);
		int maxArea = maxArea(height);
		System.out.println(maxArea);
	}

	private static int[] readHeights(Scanner scanner) {
		int n = scanner.nextInt();
		var height = new int[n];
		
		for (int i = 0; i < n; i++) {
			height[i] = scanner.nextInt();
		}
		
		return height;
	}
	
	/**
	 * Calculates the maximum area of a container built from the provided array.
	 * 
	 * @param height the array of heights
	 * @return the maximum area
	 */
	public static int maxArea(int[] height) {
		int i = 0;
		int leftHeight = height[i];
		int j = height.length - 1;
		int rightHeight = height[j];
		int bestArea = min(leftHeight, rightHeight) * (j - i);
		
		do {
			if (leftHeight <= rightHeight) {
				do {
					i++;
				} while (i < j && height[i] <= leftHeight);
			}
			
			if (leftHeight >= rightHeight) {
				do {
					j--;
				} while (i < j && height[j] <= rightHeight);
			}
			
			leftHeight = height[i];
			rightHeight = height[j];
			int area = min(leftHeight, rightHeight) * (j - i);
			
			if (area > bestArea) {
				bestArea = area;
			}
		} while (i < j);
		
		return bestArea;
	}
}
