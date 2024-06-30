package coderun.graph;

/* Task 7. Depth-first search */

import util.IntStack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * The task solution.
 * 
 * <p>While the task title implies a depth-first solution, a breadth-first would
 * also do.</p>
 */
public class ConnectedComponent1 {

	/**
	 * Reads the graph definition from the standard input and writes the
	 * answer to the standard output.
	 * 
	 * @param args command line arguments (unused)
	 */
	public static void main(String[] args) {
		var input = new InputStreamReader(System.in, StandardCharsets.US_ASCII);
		var bufferedInput = new BufferedReader(input);
		var scanner = new Scanner(bufferedInput);
		var adj = readAdjacencyMatrix(scanner);
		var marks = visit(adj, 0);
		
		var output = new OutputStreamWriter(System.out, StandardCharsets.US_ASCII);
		var bufferedOutput = new BufferedWriter(output);
		var writer = new PrintWriter(bufferedOutput);
		reportComponent(writer, marks);
	}

	/**
	 * Reads the undirected graph definition in the required format.
	 * 
	 * @param scanner the data source
	 * @return the graph adjacency matrix
	 */
	static boolean[][] readAdjacencyMatrix(Scanner scanner) {
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
	
	/**
	 * Performs the search starting from the given vertex.
	 * 
	 * <p>This is not actually a depth-first search. This is a depth-first
	 * search with the queue being replaced with a stack. This doesn't change
	 * the answer, it's just a bit easier to implement the stack.</p>
	 * 
	 * @param M the graph adjacency matrix
	 * @param u0 the starting vertex index
	 * @return array that marks the visited vertices
	 */
	static boolean[] visit(boolean[][] M, int u0) {
		int n = M.length;
		var marks = new boolean[n];
		var stack = new IntStack(n);
		stack.push(u0);
		marks[u0] = true;
		
		while (!stack.isEmpty()) {
			int u = stack.pop();
			
			for (int v = 0; v < n; v++) {
				if (!marks[v] && M[u][v]) {
					stack.push(v);
					marks[v] = true;
				}
			}
		}
		
		return marks;
	}
	
	/**
	 * Outputs the graph connected component.
	 * 
	 * @param writer the output writer
	 * @param marks arrays that marks the connected component vertices
	 */
	static void reportComponent(PrintWriter writer, boolean[] marks) {
		int K = countTrue(marks);
		writer.println(K);
		int n = marks.length;
		
		for (int i = 0; i < n; i++) {
			if (marks[i]) {
				writer.printf("%d ", i + 1);
			}
		}
		
		writer.println();
		writer.flush();
	}
	
	/**
	 * Counts the number of set flags in a Boolean array.
	 * 
	 * @param a the array to count set flags in
	 * @return the number of set flags
	 */
	static int countTrue(boolean[] a) {
		int n = a.length;
		int count = 0;
		
		for (int i = 0; i < n; i++) {
			if (a[i]) {
				count++;
			}
		}
		
		return count;
	}
}
