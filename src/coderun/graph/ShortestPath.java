package coderun.graph;

/* Task 13. Shortest path */

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
 * A classic depth-first search implementation.
 */
public class ShortestPath {

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
		int u = scanner.nextInt() - 1;
		int v = scanner.nextInt() - 1;
		var pathFinder = new PathFinder(adj);
		pathFinder.findFrom(v);
		
		var output = new OutputStreamWriter(System.out, StandardCharsets.US_ASCII);
		var bufferedOutput = new BufferedWriter(output);
		var writer = new PrintWriter(bufferedOutput);
		pathFinder.reportPathBackwards(u, writer);
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
	 * A helper class for finding shortest paths from a vertex.
	 */
	static class PathFinder {
		private static final int NONE = -1;
		private boolean[][] graph; // adjacency matrix of the graph
		private int[] prev;        // index of the previous vertex in the search tree or NONE
		private int[] pathLength;  // length of the path to the given vertex or NONE
		private IntQueue queue;

		/**
		 * Creates the shortest paths finder and prepares it for the search.
		 * 
		 * @param graph the undirected graph adjacency matrix
		 */
		public PathFinder(boolean[][] graph) {
			this.graph = graph;
			int n = graph.length;
			prev = new int[n];
			Arrays.fill(prev, NONE);
			pathLength = new int[n];
			Arrays.fill(pathLength, NONE);
			queue = new IntQueue(n);
		}

		/**
		 * Reports the found path in the required format in backward order.
		 * 
		 * @param writer the output
		 */
		public void reportPathBackwards(int v, PrintWriter writer) {
			writer.println(pathLength[v]);
			
			if (pathLength[v] > 0) {
				writer.print(v + 1);
				
				for (int u = prev[v]; u != NONE; u = prev[u]) {
					writer.print(' ');
					writer.print(u + 1);
				}
				
				writer.println();
			}
		}

		/**
		 * Searches for shortest paths from the given vertex.
		 * 
		 * @param u0 the graph vertex index
		 */
		private void findFrom(int u0) {
			pathLength[u0] = 0;
			queue.enqueue(u0);
			int n = graph.length;
			
			do {
				int u = queue.dequeue();
				
				for (int v = 0; v < n; v++) {
					if (graph[u][v] && pathLength[v] == NONE) {
						queue.enqueue(v);
						prev[v] = u;
						pathLength[v] = pathLength[u] + 1;
					}
				}
			} while (!queue.isEmpty());
		}
	}
	
	/**
	 * A simple integer queue implementation.
	 */
	static final class IntQueue {
		private int[] queue;
		private int head, tail;
		
		/**
		 * Creates an empty queue.
		 * 
		 * @param capacity total number of possible queue inserts
		 */
		public IntQueue(int capacity) {
			queue = new int[capacity];
			head = 0;
			tail = 0;
		}
		
		/**
		 * Inserts an item in the queue tail.
		 * 
		 * @param x the item to insert
		 */
		public void enqueue(int x) {
			queue[tail++] = x;
		}
		
		/**
		 * Removes the item from the queue head.
		 * 
		 * @return the removed item
		 */
		public int dequeue() {
			return queue[head++];
		}
		
		/**
		 * Checks if the queue is empty.
		 * 
		 * @return <code>true</code> if the queue is empty, <code>false</code>
		 * otherwise
		 */
		public boolean isEmpty() {
			return head == tail;
		}
	}
}
