package coderun.graph;

/* Task 9. Cheating */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Scanner;

/**
 * The task solution.
 * 
 * Implements the standard two-coloring of the undirected graph to check
 * if it's bipartite.
 */
public class BipartiteCheck {

	/**
	 * Reads the undirected graph definition from the standard output and
	 * reports whether the graph is bipertite to the standard output.
	 * 
	 * @param args command line arguments (unused)
	 */
	public static void main(String[] args) {
		var input = new InputStreamReader(System.in, StandardCharsets.US_ASCII);
		var bufferedInput = new BufferedReader(input);
		var scanner = new Scanner(bufferedInput);
		var M = readAdjacencyMatrix(scanner);
		
		if (isBipartite(M)) {
			System.out.println("YES");
		} else {
			System.out.println("NO");
		}
	}

	/**
	 * The bipartite graph check.
	 * 
	 * @param M the graph adjacency matrix
	 * @return <code>true</code> when the graph is bipartite, <code>false</code>
	 * otherwise
	 */
	private static boolean isBipartite(boolean[][] M) {
		int n = M.length;
		var color = new int[n];
		var stack = new ArrayDeque<Integer>(n);
		
		for (int u0 = 0; u0 < n; u0++) {
			if (color[u0] == 0) {
				stack.push(u0);
				
				do {
					int u = stack.pop();
					
					for (int v = 0; v < n; v++) {
						if (M[u][v]) {
							if (color[v] == 0) {
								stack.push(v);
								color[v] = 3 - color[u];
							} else if (color[v] == color[u]) {
								return false;
							}
						}
					}
				} while (!stack.isEmpty());
			}
		}
		
		return true;
	}

	/**
	 * Reads the undirected graph definition.
	 * 
	 * @param scanner the data source
	 * @return the graph adjacency matrix
	 */
	private static boolean[][] readAdjacencyMatrix(Scanner scanner) {
		int n = scanner.nextInt();
		int m = scanner.nextInt();
		var M = new boolean[n][n];
		
		for (int i = 0; i < m; i++) {
			int u = scanner.nextInt() - 1;
			int v = scanner.nextInt() - 1;
			M[u][v] = true;
			M[v][u] = true;
		}
		
		return M;
	}

}
