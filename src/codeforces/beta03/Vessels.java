package codeforces.beta03;

// B. Filling the truck

import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.lang.Math.min;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

// This task is a variation of the knapsack problem.
// Note that there are only two nominations of item values which
// makes this problem similar to the case with only a single nomination.

public class Vessels {

	public static void main(String[] args) {
		var bufferedReader = new BufferedReader(new InputStreamReader(System.in, US_ASCII));
		var scanner = new Scanner(bufferedReader);
		var task = new Task();
		task.read(scanner);
		
		var solver = new Solver(task);
		var solution = solver.solve();

		var bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out, US_ASCII));
		var writer = new PrintWriter(bufferedWriter);
		solution.write(task, writer);
		writer.flush();
	}

	// The vessel description. The vessel type is not recorded since the vessels of different types
    // will go into separate lists.
	private static class Vessel {
		int serialno;
		int load;
	}

	private static class Task {
		// Maximum space in the truck.
		int V;
		
		// Vessels of type 1.
		List<Vessel> vessels1;
		
		// Vessels of type 2.
		List<Vessel> vessels2;
		
		public void read(Scanner scanner) {
			int n = scanner.nextInt();
			V = scanner.nextInt();
			vessels1 = new ArrayList<>(); 
			vessels2 = new ArrayList<>();
			
			for (int i = 0; i < n; ++i) {
				var vessel = new Vessel();
				vessel.serialno = i + 1;
				int volume = scanner.nextInt();
				vessel.load = scanner.nextInt();
				
                switch (volume) {
                    case 1 -> vessels1.add(vessel);
                    case 2 -> vessels2.add(vessel);
                    default -> throw new IllegalArgumentException("illegal vessel type " + volume);
                }
			}		
		}
	}

	/**
	 * The task solution: the optimal set of vessels to fill the truck to the maximum.
	 * 
	 * <p>Note that if a vessel is in the optimal set, all of the vessels of the same type
	 * and higher load should also be in the optimal set. (Otherwise, we could replace this
	 * vessel with a vessel of the same type and higher load and thus improve the total load.)</p>
	 * 
	 * <p>This means that the solution should be of a very limited form: it's just a certain number
	 * of vessels of both types of the highest load among vessels of their type.</p>
	 */
	private static class Solution {
		// Number of vessels of type 1 with the highest load in the solution.
		int n1;
		
		// Number of vessels of type 2 with the highest load in the solution.
		int n2;
		
		// The total load of the solution.
		int load;
		
		public void write(Task task, PrintWriter writer) {
			writer.println(load);
			
			for (int i = 0; i < n1; ++i)
				writer.println(task.vessels1.get(i).serialno);
			
			for (int i = 0; i < n2; ++i)
				writer.println(task.vessels2.get(i).serialno);
		}
	}
	
	private static class Solver {
		final Task task;
		
		// Cumulative sums of top vessel loads (type 1 vessels).
		final int[] cumul1;
		
		// Cumulative sums of top vessel loads (type 2 vessels).
		final int[] cumul2;
		
		// Initializes the solver.
        // The vessels in the task definition will be sorted as a side effect.
		public Solver(Task task) {
			this.task = task;
			// Sort and compute cumulative sums for fast lookup.
			Collections.sort(task.vessels1, byLoadDesc);
			cumul1 = accumulateLoad(task.vessels1);
			Collections.sort(task.vessels2, byLoadDesc);
			cumul2 = accumulateLoad(task.vessels2);
		}
		
		public Solution solve() {
            // Since the solution has such a limited form, we can just
	        // look through all possible solutions in that form and pick the best.
			var solution = new Solution();
			
			// Let's fix the number of type 2 vessels in the solution and
			// find how many type 1 vessels can fit in the leftover space.
			for (int n2 = 0; n2 <= task.vessels2.size(); ++n2) {
				// free space (type 2 vessels occupy 2 m^3 of truck space)
				int freespace = task.V - 2 * n2;

				// freespace < 0 means that the type 2 vessels already don't fit
				if (freespace >= 0) {
					int n1 = min(freespace, task.vessels1.size());
					int load = cumul1[n1] + cumul2[n2]; 
					
					if (load > solution.load) {
						solution.n1 = n1;
						solution.n2 = n2;
						solution.load = load;
					}
				}
			}
			
			return solution;
		}
	}

	// Computes cumulative sums of vessel loads.
	private static int[] accumulateLoad(List<Vessel> vessels) {
		int load = 0;
		var cumulLoad = new int[vessels.size() + 1];
		cumulLoad[0] = 0;
		
		for (int i = 0; i < vessels.size(); ++i) {
			var vessel = vessels.get(i);
			load += vessel.load;
			cumulLoad[i + 1] = load;
		}

		return cumulLoad;
	}

	// Vessel comparator to sort by load, decreasing.
	private static final Comparator<Vessel> byLoadDesc =
            (Vessel o1, Vessel o2) -> Integer.compare(o2.load, o1.load);	
}
