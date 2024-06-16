package coderun.graph;

/* Task 10. Topological sorting */

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
 * Classic topological sort algorithm, for a graph represented via adjacency
 * list.
 */
public class TopoSort {

	/**
	 * Reads the graph from the standard input and writes the result
	 * to the standard output.
	 * 
	 * @param args command line arguments (unused)
	 */
	public static void main(String[] args) {
		var input = new InputStreamReader(System.in, StandardCharsets.US_ASCII);
		var bufferedInput = new BufferedReader(input);
		var scanner = new Scanner(bufferedInput);
		var graph = readGraph(scanner);
		var sort = topologicalSort(graph);
		var output = new OutputStreamWriter(System.out, StandardCharsets.US_ASCII);
		var bufferedOutput = new BufferedWriter(output);
		var writer = new PrintWriter(bufferedOutput);
		reportSort(writer, sort);
	}
	
	/**
	 * An arc of the graph, an item of the adjacency list.
	 */
	static private final class Arc {
		int vertex;
		Arc next;
	}

	/**
	 * Read the directed graph definition.
	 * 
	 * @param scanner the data source
	 * @return the directed graph as an array of adjacency lists for each vertex
	 */
	private static Arc[] readGraph(Scanner scanner) {
		int n = scanner.nextInt();
		int m = scanner.nextInt();
		var graph = new Arc[n];

		for (int i = 0; i < m; i++) {
			int u = scanner.nextInt() - 1;
			int v = scanner.nextInt() - 1;
			var arc = new Arc();
			arc.vertex = v;
			arc.next = graph[u];
			graph[u] = arc;
		}

		return graph;
	}
	
	/**
	 * Writes the sorted vertex sequence.
	 * 
	 * @param writer where to write
	 * @param sort the sorted sequence or <code>null</code> is sorting is
	 * impossible
	 */
	private static void reportSort(PrintWriter writer, int[] sort) {
		if (sort == null) {
			writer.println("-1");
		} else {
			for (int v : sort) {
				writer.printf("%d ", v + 1);
			}

			writer.println();
		}

		writer.flush();
	}

	/**
	 * Sorts the graph vertices topologically.
	 * 
	 * @param graph the directed graph
	 * @return the sorted vertex sequence or <code>null</code> if the vertices
	 * can't be sorted
	 */
	private static int[] topologicalSort(Arc[] graph) {
		var sorter = new Sorter(graph);
		sorter.sort();
		
		if (sorter.hasLoop()) {
			return null;
		}
		
		return sorter.result();
	}
	
	/**
	 * A helper class to perform topological sorting.
	 * 
	 * <p>Encapsulates all the machinery necessary for the sorting process.</p>
	 */
	static private class Sorter {
		// Vertex marks
		// UNVISITED should be 0 for default array initialization
		private static final byte UNVISITED = 0;
		private static final byte PROCESSING = 1;
		private static final byte VISITED = 2;

		private Arc[] graph;     // the graph
		private byte[] mark;     // vertex marks (see above)
		private Arc[] last;      // first unvisited arc for each vertex
		private int[] result;    // the sorted sequence
		private boolean loop;    // loop detected?
		private int k;           // result has k final values found
		private IntStack stack;  // DFS stack
		
		/**
		 * Initializes the sorter.
		 * 
		 * @param graph the directed graph
		 */
		public Sorter(Arc[] graph) {
			int n = graph.length;
			this.graph = graph;
			mark = new byte[n];
			last = graph.clone();
			result = new int[n];
			k = 0;
			stack = new IntStack(n);
			loop = false;
		}
		
		/**
		 * Performs the sorting.
		 */
		public void sort() {
			int n = graph.length;

			for (int u = 0; u < n && !loop; u++) {
				if (mark[u] == UNVISITED) {
					visit(u);
				}
			}
		}
		
		/**
		 * Reports whether a loop was found during sorting.
		 * 
		 * @return <code>true</code> if there was a loop, <code>false</code>
		 * otherwise
		 */
		public boolean hasLoop() {
			return loop;
		}
		
		/**
		 * Returns the sorted vertex sequence, if there were no loops detected.
		 * 
		 * @return the sorted vertex sequence
		 */
		public int[] result() {
			return result;
		}
		
		/**
		 * A depth-first search pass starting from the given vertex.
		 * 
		 * @param u the starting vertex index
		 */
		void visit(int u) {
			int n = graph.length;
			mark[u] = PROCESSING;
			stack.push(u);

			do {
				u = stack.peek();
				var arc = last[u];
				
				while (arc != null &&
				       mark[arc.vertex] == VISITED) {
					arc = arc.next;
				}
				
				last[u] = arc;
				
				if (arc == null) {
					mark[u] = VISITED;
					stack.pop();
					k++;
					result[n - k] = u;
				} else {
					int v = arc.vertex;
					
					if (mark[v] == UNVISITED) {
						mark[v] = PROCESSING;
						stack.push(v);
					} else { // PROCESSING
						loop = true;
						return;
					}
				}
			} while (!stack.isEmpty());
		}	
	}
}
