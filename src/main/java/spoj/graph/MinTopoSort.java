package spoj.graph;

/* TOPOSORT and TOPOSORT2 problems */

import util.AsciiScanner;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * The task solution.
 * 
 * <p>Finds the lexicographically minimal topological sorting using a modified
 * Kahn's algorithm. In order to fit into the time limit of TOPOSORT task,
 * custom ASCII input of numbers is implemented. There is also a custom
 * priority queue implementation, but the major performance bottleneck for
 * the task is slow I/O.</p>
 */
public class MinTopoSort {

	/**
	 * Reads the directed graph definition from the standard input and
	 * writes the topological sorting of the graph to the standard output.
	 * 
	 * @param args command line arguments (unused)
	 */
	public static void main(String[] args) {
		var scanner = new AsciiScanner(System.in, 65536);
		var sorter = new MinTopoSort(scanner);
		sorter.sort();
		var writer = new OutputStreamWriter(System.out, StandardCharsets.US_ASCII);
		var bufferedWriter = new BufferedWriter(writer);
		var printWriter = new PrintWriter(bufferedWriter);
		sorter.report(printWriter);
		printWriter.flush();
	}
	
	/**
	 * An arc entry. Used both for incoming and outgoing arcs.
	 */
	private static class Arc {
		int vertex;
		Arc next;

		/**
		 * Creates a new arc entry.
		 * 
		 * @param vertex the ending vertex for an outgoing arc, the starting
		 * vertex for an incoming arc
		 * @param next the next arc entry in the list
		 */
		public Arc(int vertex, Arc next) {
			this.vertex = vertex;
			this.next = next;
		}
	}
		
	private int vertexCount;
	private Arc[] outgoing;
	private Arc[] incoming;

	private int[] result;
	private int resultLength;
	private PriorityQueue queue;
	private int[] incount;
	
	/**
	 * Reads the graph from the standard input and prepares for sorting.
	 * 
	 * @param scanner the data source
	 */
	private MinTopoSort(AsciiScanner scanner) {
		vertexCount = scanner.nextInt();
		outgoing = new Arc[vertexCount];
		incoming = new Arc[vertexCount];
		int arcCount = scanner.nextInt();
		
		for (int i = 0; i < arcCount; i++) {
			int u = scanner.nextInt() - 1;
			int v = scanner.nextInt() - 1;
			outgoing[u] = new Arc(v, outgoing[u]);
			incoming[v] = new Arc(u, incoming[v]);
		}
		
		result = new int[vertexCount];
		resultLength = 0;
		queue = new PriorityQueue(vertexCount);
		incount = new int[vertexCount];
	}
	
	/**
	 * Topological sorting using a modified Kahn's algorithm.
	 * 
	 * <p>Instead of a usual queue or a stack, a priority queue is used.
	 * This guarantees that the next vertex in the sorted order always has
	 * the least index.</p>
	 */
	private void sort() {
		// Count incoming arcs for every vertex.
		for (int u = 0; u < vertexCount; u++) {
			int count = 0;
			var arc = incoming[u];
			
			while (arc != null) {
				count++;
				arc = arc.next;
			}
			
			incount[u] = count;
		}
		
		// Add vertices with zero incoming arcs into the queue.
		for (int u = 0; u < vertexCount; u++) {
			if (incount[u] == 0) {
				queue.offer(u);
			}
		}
		
		// Keep removing vertices with zero incoming arcs.
		while (!queue.isEmpty()) {
			int u = queue.poll();
			result[resultLength++] = u;
			var arc = outgoing[u];
			
			while (arc != null) {
				int v = arc.vertex;
				
				if (incount[v] > 0) {
					incount[v]--;
					
					if (incount[v] == 0) {
						queue.offer(v);
					}
				}
				
				arc = arc.next;
			}
		}
	}
	
	/**
	 * Reports the found sorting.
	 * 
	 * @param printWriter where to write the result
	 */
	private void report(PrintWriter printWriter) {
		if (resultLength < vertexCount) {
			printWriter.println("Sandro fails.");
		} else {
			for (int u: result) {
				printWriter.print(u + 1);
				printWriter.print(' ');
			}
			
			printWriter.println();
		}
	}
	
	/**
	 * An integer priority queue implementation.
	 */
	private static final class PriorityQueue {
		private int[] heap;
		private int size;
		
		/**
		 * Creates an empty priority queue.
		 * 
		 * @param capacity maximum capacity of the queue
		 */
		public PriorityQueue(int capacity) {
			heap = new int[capacity];
			size = 0;
		}
		
		/**
		 * Checks if the queue is empty.
		 * 
		 * @return <code>true</code> if the queue is empty and <code>false</code>
		 * otherwise
		 */
		public boolean isEmpty() {
			return size == 0;
		}
		
		/**
		 * Insert a key into the priority queue.
		 * 
		 * @param key the inserted key
		 */
		public void offer(int key) {
			decreaseKey(size, key);
			size++;
		}
		
		/**
		 * Removes the smallest key from the priority queue.
		 * 
		 * @return the removed key
		 */
		public int poll() {
			int x = heap[0];
			size--;
			increaseKey(0, heap[size]);
			return x;
		}
		
		private static int firstChild(int i) {
			return 2 * i + 1;
		}

		private static int parent(int i) {
			return (i - 1) / 2;
		}

		private void decreaseKey(int i, int key) {
			int j = parent(i);
			
			while (i > 0 && heap[j] > key) {
				heap[i] = heap[j];
				i = j;
				j = parent(i);
			}
			
			heap[i] = key;
		}

		private void increaseKey(int i, int key) {
			int j = minInSubtree(i, key);
			
			while (j != i) {
				heap[i] = heap[j];
				i = j;
				j = minInSubtree(i, key);
			}
			
			heap[i] = key;
		}

		private int minInSubtree(int i, int key) {
			int j = firstChild(i);
			
			if (j < size && heap[j] < key) {
				i = j;
				key = heap[j];
			}
			
			j++;
			
			if (j < size && heap[j] < key) {
				i = j;
			}
			
			return i;
		}
	}
}
