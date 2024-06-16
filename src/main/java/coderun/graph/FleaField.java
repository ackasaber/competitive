package coderun.graph;

/* Task 1. Fleas */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

/**
 * The task solution.
 * 
 * A creative application for the shortest paths search in an unweighed
 * undirected graph. The task effectively asks for the sum of shortest path
 * lengths from the fixed vertex to selected other vertices. Vertices here are
 * field cells and edges connect vertices in a knight's move fashion.
 */
public class FleaField {

	/**
	 * Reads in the field description and fleas position on the field,
	 * finds the shortest paths to the feeder, and writes out the total
	 * shortest path length to the standard output.
	 * 
	 * @param args command line arguments (unused)
	 */
	public static void main(String[] args) {
		var input = new InputStreamReader(System.in, StandardCharsets.US_ASCII);
		var bufferedInput = new BufferedReader(input);
		var scanner = new Scanner(bufferedInput);
		var field = new FleaField(scanner);
		field.findPaths();
		field.reportPathsLength();
	}
	
	private int rowCount;
	private int columnCount;
	private boolean[][] fleaPresent;
	private int feederRow;
	private int feederColumn;
	
	private static final int NONE = -1;
	private int[][] pathLength;
	
	/**
	 * Reads the task inputs from the scanner.
	 * 
	 * @param scanner the data source
	 */
	public FleaField(Scanner scanner) {
		rowCount = scanner.nextInt();
		columnCount = scanner.nextInt();
		feederRow = scanner.nextInt() - 1;
		feederColumn = scanner.nextInt() - 1;
		int fleaCount = scanner.nextInt();
		fleaPresent = new boolean[rowCount][columnCount];
		
		for (int i = 0; i < fleaCount; i++) {
			int fleaRow = scanner.nextInt() - 1;
			int fleaColumn = scanner.nextInt() - 1;
			fleaPresent[fleaRow][fleaColumn] = true;
		}
		
		pathLength = new int[rowCount][columnCount];
		
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				pathLength[i][j] = NONE;
			}
		}
	}

	/**
	 * Writes the answer to the standard output.
	 */
	private void reportPathsLength() {
		if (allFleasCanFeed()) {
			int totalJumps = countMinimumJumpCount();
			System.out.println(totalJumps);
		} else {
			System.out.println("-1");
		}
	}

	/**
	 * Checks that all fleas can reach the feeder.
	 * 
	 * @return <code>true</code> if all fleas can reach the feeder,
	 * <code>false</code> when there is a flea that cannot.
	 */
	private boolean allFleasCanFeed() {
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				if (fleaPresent[i][j] && pathLength[i][j] == NONE) {
					return false;
				}
			}
		}
		
		return true;
	}

	/**
	 * Counts the total minimum jump count necessary for the fleas to
	 * reach the feeder.
	 * 
	 * @return the total minimum jump count
	 */
	private int countMinimumJumpCount() {
		int total = 0;
		
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				if (fleaPresent[i][j]) {
					total += pathLength[i][j];
				}
			}
		}
		
		return total;
	}
	
	// Definition of possible knight moves.
	private static final int JUMPS = 8;
	private static final int[] rowJump    = { +1, +1, -1, -1, +2, +2, -2, -2 };
	private static final int[] columnJump = { +2, -2, +2, -2, +1, -1, +1, -1 };

	/**
	 * Breadth-first search in the field from the feeder cell.
	 */
	private void findPaths() {
		Queue<CellLocation> queue = new ArrayDeque<>();
		queue.offer(new CellLocation(feederRow, feederColumn));
		pathLength[feederRow][feederColumn] = 0;
		
		do {
			var cell = queue.poll();
			int cellPathLength = pathLength[cell.row][cell.column];
			
			for (int i = 0; i < JUMPS; i++) {
				int newRow = cell.row + rowJump[i];
				int newColumn = cell.column + columnJump[i];
				
				if (0 <= newRow && newRow < rowCount &&
				    0 <= newColumn && newColumn < columnCount &&
				    pathLength[newRow][newColumn] == NONE) {
					queue.offer(new CellLocation(newRow, newColumn));
					pathLength[newRow][newColumn] = cellPathLength + 1;
				}
			}
		} while (!queue.isEmpty());
	}

	private static final class CellLocation {
		final int row, column;
		
		public CellLocation(int row, int column) {
			this.row = row;
			this.column = column;
		}
	}
}
