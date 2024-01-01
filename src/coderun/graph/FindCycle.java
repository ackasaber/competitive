package coderun.graph;

/* Task 11. Finding a cycle */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The task solution.
 * 
 * <p>We build the search tree and check for back edges during the search.
 * Any kind of search is suitable, not necessarily depth-first or breadth-first,
 * as long as all the vertices are visited systematically.</p>
 * 
 * <p>The cycle is restored by inverting the path from a back edge vertex in
 * the search tree. We close the cycle this way.</p>
 */
public class FindCycle {

	/**
	 * Reads the undirected graph adjacency matrix from the standard input
	 * and writes the answer to the standard output.
	 * 
	 * @param args command line arguments (unused)
	 */
	public static void main(String[] args) {
		var input = new InputStreamReader(System.in, StandardCharsets.US_ASCII);
		var bufferedInput = new BufferedReader(input);
		var scanner = new Scanner(bufferedInput);
		var adj = readAdjacencyMatrix(scanner);
		var cycleFinder = new CycleFinder(adj);
		cycleFinder.find();
		
		var output = new OutputStreamWriter(System.out, StandardCharsets.US_ASCII);
		var bufferedOutput = new BufferedWriter(output);
		var writer = new PrintWriter(bufferedOutput);
		cycleFinder.report(writer);
		writer.flush();
	}
	
	/**
	 * Reads the undirected graph definition in the required format.
	 * 
	 * @param scanner the data source
	 * @return the graph adjacency matrix
	 */
	static boolean[][] readAdjacencyMatrix(Scanner scanner) {
		int n = scanner.nextInt();
		var M = new boolean[n][n];
		
		for (int u = 0; u < n; u++) {
			for (int v = 0; v < n; v++) {
				M[u][v] = (scanner.nextInt() != 0);
			}
		}
		
		return M;
	}
	
	/**
	 * A helper class for finding a cycle in the undirected graph.
	 */
	static class CycleFinder {
		private static final int NONE = -1;
		private boolean[][] graph; // adjacency matrix of the graph
		private boolean[] visited; // whether the vertex was visited
		private int[] prev;        // index of the previous vertex in the search tree or NONE
		private int cycleStart;    // a cycle vertex or NONE if not found
		private IntStack stack;

		/**
		 * Creates the cycle finder and prepares it for the search.
		 * 
		 * @param graph the undirected graph adjacency matrix
		 */
		public CycleFinder(boolean[][] graph) {
			this.graph = graph;
			int n = graph.length;
			visited = new boolean[n];
			prev = new int[n];
			Arrays.fill(prev, NONE);
			cycleStart = NONE;
			stack = new IntStack(n);
		}

		/**
		 * Reports the found cycle in the required format.
		 * 
		 * @param writer the output
		 */
		public void report(PrintWriter writer) {
			if (cycleStart == NONE) {
				writer.println("NO");
			} else {
				writer.println("YES");
				int k = 1;
				
				for (int u = prev[cycleStart]; u != cycleStart; u = prev[u]) {
					k++;
				}
				
				writer.println(k);
				writer.print(cycleStart + 1);
				
				for (int u = prev[cycleStart]; u != cycleStart; u = prev[u]) {
					writer.print(' ');
					writer.print(u + 1);
				}
				
				writer.println();
			}
			
		}

		/**
		 * Launches cycle search.
		 */
		public void find() {
			int n = graph.length;
			
			for (int u = 0; u < n && cycleStart == NONE; u++) {
				if (!visited[u]) {
					visit(u);
				}
			}
		}

		/**
		 * Visits the connected component containing the vertex.
		 * 
		 * @param u0 the graph vertex index
		 */
		private void visit(int u0) {
			visited[u0] = true;
			stack.push(u0);
			int n = graph.length;
			
			do {
				int u = stack.pop();
				
				for (int v = 0; v < n; v++) {
					if (graph[u][v] && v != prev[u]) {
						if (visited[v]) {
							invertPath(v);
							prev[v] = u; // complete the cycle
							cycleStart = v;
							return;
						}
						
						stack.push(v);
						visited[v] = true;
						prev[v] = u;
					}
				}
			} while (!stack.isEmpty());
		}

		/**
		 * Inverts the path from the vertex.
		 * 
		 * <p>The element <code>prev[v]</code> is not touched.</p>
		 * @param v the new starting vertex in the path
		 */
		private void invertPath(int v) {
			int u = prev[v];
			
			while (u != NONE) {
				int t = prev[u];
				prev[u] = v;
				v = u;
				u = t;
			}
		}
	}
}
