package codeforces.beta03;

/* Task 3C. Tic-tac-toe. */

import static java.lang.System.out;
import static java.nio.charset.StandardCharsets.US_ASCII;

import java.util.Scanner;

/**
 * The task solution.
 */
public class TicTacToe {

	/**
	 * Reads the board state from the standard output and
	 * writes its assessment to the standard output.
	 *  
	 * @param args command line parameters (unused)
	 */
	public static void main(String[] args) {
		var scanner = new Scanner(System.in, US_ASCII);
		var board = readBoard(scanner);
		var stats = board.stats();
		
		if (stats.crossesWon()) {
			out.println("the first player won");
		} else if (stats.zeroesWon()) {
			out.println("the second player won");
		} else if (stats.drawn()) {
			out.println("draw");
		} else if (stats.illegal()) {
			out.println("illegal");
		} else {
			out.println(stats.nextMove() == 'X' ? "first" : "second");
		}
	}

	/**
	 * Enumeration of all relevant lines on the board.
	 */
	private enum BoardLine {
		ROW1(0, 0, 0, 1),
		ROW2(1, 0, 0, 1),
		ROW3(2, 0, 0, 1),
		COLUMN1(0, 0, 1, 0),
		COLUMN2(0, 1, 1, 0),
		COLUMN3(0, 2, 1, 0),
		MAIN_DIAGONAL(0, 0, 1, 1),
		SECONDARY_DIAGONAL(0, 2, 1, -1);
		
		// starting point of the line and the direction
		int r0, c0, dR, dC;

		/**
		 * Defines the board direction.
		 * 
		 * @param r0 starting row
		 * @param c0 starting column
		 * @param dR next row vector
		 * @param dC next column vector
		 */
		private BoardLine(int r0, int c0, int dR, int dC) {
			this.r0 = r0;
			this.c0 = c0;
			this.dR = dR;
			this.dC = dC;
		}
	};
	
	/**
	 * Tic-tac-toe board.
	 */
	private static class Board {

		/**
		 * Board size.
		 */
		static final int SIZE = 3;
		
		/**
		 * The board state, with X, 0 and . markings.
		 */
		char[][] marks = new char[SIZE][SIZE];

		/**
		 * Counts the mark on the given board line.
		 * 
		 * @param line the board line
		 * @param mark the mark character
		 * @return count of the marks on the board line
		 */
		int countAlong(BoardLine line, char mark) {
			int count = 0;
			int r = line.r0;
			int c = line.c0;
			
			for (int i = 0; i < SIZE; ++i) {
				if (marks[r][c] == mark) {
					++count;
				}
				
				r += line.dR;
				c += line.dC;
			}
			
			return count;
		}
		
		/**
		 * Computes the board state statistics.
		 * 
		 * @return board statistics
		 */
		public BoardStats stats() {
			var stats = new BoardStats();
			
			for (int r = 0; r < SIZE; ++r) {
				for (int c = 0; c < SIZE; ++c) {
					switch (marks[r][c]) {
						case 'X': stats.crossesTotal++; break;
						case '0': stats.zeroesTotal++; break;
						default:  stats.unfilled++; break;
					}
				}
			}
			
			for (var line: BoardLine.values()) {
				if (countAlong(line, 'X') == SIZE) {
					stats.crossesLine = true;
				}
				
				if (countAlong(line, '0') == SIZE) {
					stats.zeroesLine = true;
				}
			}
			
			return stats;
		}
	}
	
	/**
	 * Tic-tac-toe board state statistics.
	 */
	private static class BoardStats {
		/**
		 * Total count of zeroes on the board.
		 */
		int zeroesTotal;
		
		/**
		 * Total count of crosses on the board.
		 */
		int crossesTotal;
		
		/**
		 * Total count of unfilled cells on the board.
		 */
		int unfilled;
		
		/**
		 * Are there lines filled with crosses?
		 */
		boolean crossesLine;
		
		/**
		 * Are there lines filled with zeroes?
		 */
		boolean zeroesLine;
		
		/**
		 * Crosses win condition.
		 * 
		 * @return {@code true}, if the first player won, {@code false} otherwise
		 */
		boolean crossesWon() {
			return crossesLine &&
			       !zeroesLine &&
			       crossesTotal == zeroesTotal + 1;
		}
		
		/**
		 * Zeroes win condition.
		 * 
		 * @return {@code true}, if the second player won, {@code false} otherwise
		 */
		boolean zeroesWon() {
			return zeroesLine &&
			       !crossesLine &&
			       zeroesTotal == crossesTotal;
		}
		
		/**
		 * Draw condition.
		 * 
		 * @return {@code true}, if the game ended in a draw, {@code false} otherwise
		 */
		boolean drawn() {
			return unfilled == 0 &&
			       crossesTotal == zeroesTotal + 1 &&
			       !crossesLine &&
			       !zeroesLine;
		}
		
		/**
		 * Illegal board check.
		 * 
		 * @return {@code true}, if no legal game could produce such a board state, {@code false} otherwise
		 */
		boolean illegal() {
			return zeroesTotal != crossesTotal && crossesTotal != zeroesTotal + 1 ||
			       crossesLine && zeroesLine ||
			       crossesLine && crossesTotal != zeroesTotal + 1 ||
			       zeroesLine && zeroesTotal != crossesTotal;
		}
		
		/**
		 * The next player to make a move given the board state is legal and there are moves left.
		 * 
		 * @return {@code 'X'} if the first player goes next, {@code '0'} if the second player goes next
		 */
		char nextMove() {
			return (zeroesTotal == crossesTotal) ? 'X' : '0';
		}
	}
	
	/**
	 * Reads the board state from the scanner.
	 * 
	 * @param scanner the scanner
	 * @return the board state
	 */
	private static Board readBoard(Scanner scanner) {
		var board = new Board();
		
		for (int i = 0; i < Board.SIZE; ++i) {
			readRow(board.marks[i], scanner);
		}

		return board;
	}
	
	/**
	 * Reads a single board row from the scanner.
	 * 
	 * @param row the board row
	 * @param scanner the scanner
	 */
	private static void readRow(char[] row, Scanner scanner) {
		var line = scanner.nextLine().trim();
		
		if (line.length() != row.length) {
			throw new IllegalStateException("wrong line length");
		}
		
		for (int i = 0; i < row.length; ++i) {
			row[i] = line.charAt(i);
		}
	}
}
