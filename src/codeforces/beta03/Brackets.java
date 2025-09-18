package codeforces.beta03;

// D. Least cost bracket sequence

import static java.nio.charset.StandardCharsets.US_ASCII;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Lazy solution found in the vast spaces of the Internet.
 * 
 * <p>First notice that the total cost of placing brackets equals</p>
 * 
 * <p>&Sigma;<sub>all unset</sub> <i>p</i><sub><i>i</i></sub> +
 * &Sigma;<sub>placed opening bracket</sub>(<i>q</i><sub><i>i</i></sub> &minus; <i>p</i><sub><i>i</i></sub>).</p>
 * 
 * <p>Since the first summand here is fixed, the optimal solution minimizes the second, which is the total
 * cost of replacing closing brackets with opening brackets.</p>
 * 
 * <p>Then let <i>Q</i><sub><i>i</i></sub>, <i>P</i><sub><i>i</i></sub> and <i>N</i><sub><i>i</i></sub>
 * be the number of opening, closing and unset brackets in the first <i>i</i> characters of
 * the input pattern.</p>
 * 
 * <p>Let's pick the first index <i>i</i> such that
 * <i>N</i><sub><i>i</i></sub> &gt; <i>Q</i><sub><i>i</i></sub> &minus; <i>P</i><sub><i>i</i></sub>.
 * If there is such an <i>i</i>, every correct bracket completion must have at least one opening bracket
 * among the first <i>i</i> characters. (All closing brackets would drive the bracket balance below zero.)</p>
 * 
 * <p>The optimal solution may have several opening brackets in this range, but the bracket that has the least
 * cost of replacing it from closed to opened will necessarily be in the optimal solution. We can set this opening
 * bracket in the input pattern and repeat this reasoning over.</p>
 * 
 * <p>Of course, if there are no unset brackets among the first <i>i</i> characters for this <i>i</i>,
 * it's impossible to place brackets for this pattern: there are too many closing brackets and not enough
 * unset positions to make the bracket balance non-negative.</p>
 * 
 * <p>If <i>N</i><sub><i>i</i></sub> &le; <i>Q</i><sub><i>i</i></sub> &minus; <i>P</i><sub><i>i</i></sub> for all <i>i</i>,
 * then it also holds for <i>n</i>: <i>N</i><sub><i>n</i></sub> &le; <i>Q</i><sub><i>n</i></sub> &minus; <i>P</i><sub><i>n</i></sub>.
 * The strict inequality means that there is not enough unset positions to close all brackets and the correctly balanced
 * solution is impossible. The equality means that the correctly balanced solution must have all unset positions filled with
 * closed brackets.</p>
 * 
 * <p>These considerations mean that we can just set opening brackets one-by-one following
 * the rules above and once we see that there can be no more opening brackets, set the rest to
 * closing brackets. Choosing the bracket with the least cost of replacing from closed to opened
 * among the yet-unset positions can be efficiently done with a priority queue.</p>
 */
public class Brackets {

	public static void main(String[] args) {
		var bufferedReader = new BufferedReader(new InputStreamReader(System.in, US_ASCII));
		var scanner = new Scanner(bufferedReader);
		var task = new Task(scanner);
		var solution = task.solve();

		var bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out, US_ASCII));
		var writer = new PrintWriter(bufferedWriter);
		solution.write(writer);
		writer.flush();
	}

	private static class Task {
		String pattern;
	
		// Costs of unset positions.
		ArrayList<UnsetPosition> unsetPositions;
		
		public Task(Scanner scanner) {
			pattern = scanner.nextLine();
			int n = pattern.length();
			unsetPositions = new ArrayList<>();
			
			for (int i = 0; i < n; ++i) {
				char c = pattern.charAt(i);
				
				if (c == '?') {
					var pos = new UnsetPosition();
					pos.index = i;
					pos.openCost = scanner.nextInt();
					pos.closeCost = scanner.nextInt();
					unsetPositions.add(pos);
				}
			}
		}
		
		public Solution solve() {
			var buffer = new StringBuilder(pattern);
			var minUnset = new PriorityQueue<UnsetPosition>();
			// balance = Q_i - P_i - N_i
			int balance = 0;
			int nextUnsetIndex = 0;
			long cost = 0;
			
			for (int i = 0; i < buffer.length() && balance >= 0; ++i) {
				var c = buffer.charAt(i);
				
				if (c == '(') {
					balance++;
				} else {
					if (c == '?') {
						var pos = unsetPositions.get(nextUnsetIndex);
						nextUnsetIndex++;
						// set it as ')' for now
						buffer.setCharAt(i, ')');
						cost += pos.closeCost;
						minUnset.offer(pos);
					}
					
					--balance;
										
					if (balance < 0) {
						var minPos = minUnset.poll();
						
						if (minPos != null) {
							// optimal solution must have an opening bracket at this index
							buffer.setCharAt(minPos.index, '(');
							cost += minPos.openCost - minPos.closeCost;
							balance += 2;
						}
					}
				}
			}
			
			var solution = new Solution();
			
			if (balance == 0) {
				solution.minCost = cost;
				solution.brackets = buffer.toString();
			} else {
				// either too many opening or closing brackets
				solution.minCost = -1;
			}
			
			return solution;
		}
	}
	
	// Unset position info.
	private static class UnsetPosition implements Comparable<UnsetPosition> {
		// The pattern character index.
		int index;
		
		// Cost of placing an opening bracket at this index.
		int openCost;
		
		// Cost of placing a closing bracket at this index.
		int closeCost;
		
		// Compares unset positions based on the cost of changing
		// the closing bracket with an opening bracket.
		@Override
		public int compareTo(UnsetPosition pos) {
			return Integer.compare(openCost - closeCost, pos.openCost - pos.closeCost);
		}
	}

	private static class Solution {
		String brackets;
		long minCost;
		
		public void write(PrintWriter writer) {
			writer.println(minCost);
			
			if (minCost != -1)
				writer.println(brackets);
		}
	}
}
