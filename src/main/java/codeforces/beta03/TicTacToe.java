package codeforces.beta03;

// C. Tic-tac-toe

import static java.lang.System.out;
import static java.nio.charset.StandardCharsets.US_ASCII;

import java.util.Scanner;

public class TicTacToe {

	public static void main(String[] args) {
		var scanner = new Scanner(System.in, US_ASCII);
		var board = readBoard(scanner);
		var stats = board.stats();
		
		if (stats.crossesWon())
			out.println("the first player won");
		else if (stats.zeroesWon())
			out.println("the second player won");
		else if (stats.drawn())
			out.println("draw");
		else if (stats.illegal())
			out.println("illegal");
		else
			out.println(stats.nextMove() == 'X' ? "first" : "second");
	}

	// Enumeration of all relevant lines on the board.
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
	
	private static class Board {

		static final int SIZE = 3;
		
		// The board state, with X, 0 and . markings.
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
				if (marks[r][c] == mark)
					++count;
				
				r += line.dR;
				c += line.dC;
			}
			
			return count;
		}
		
		public BoardStats stats() {
			var stats = new BoardStats();
			
			for (int r = 0; r < SIZE; ++r) {
				for (int c = 0; c < SIZE; ++c) {
					switch (marks[r][c]) {
						case 'X' -> stats.crossesTotal++;
						case '0' -> stats.zeroesTotal++;
						default -> stats.unfilled++;
					}
				}
			}
			
			for (var line: BoardLine.values()) {
				if (countAlong(line, 'X') == SIZE)
					stats.crossesLine = true;
				
				if (countAlong(line, '0') == SIZE)
					stats.zeroesLine = true;
			}
			
			return stats;
		}
	}
	
	private static class BoardStats {
		int zeroesTotal;
		int crossesTotal;
		int unfilled;
		boolean crossesLine;
		boolean zeroesLine;
		
		boolean crossesWon() {
			return crossesLine &&
			       !zeroesLine &&
			       crossesTotal == zeroesTotal + 1;
		}
		
		boolean zeroesWon() {
			return zeroesLine &&
			       !crossesLine &&
			       zeroesTotal == crossesTotal;
		}
		
		boolean drawn() {
			return unfilled == 0 &&
			       crossesTotal == zeroesTotal + 1 &&
			       !crossesLine &&
			       !zeroesLine;
		}
		
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
	
	private static Board readBoard(Scanner scanner) {
		var board = new Board();
		
		for (int i = 0; i < Board.SIZE; ++i)
			readRow(board.marks[i], scanner);

		return board;
	}
	
	private static void readRow(char[] row, Scanner scanner) {
		var line = scanner.nextLine().trim();
		
		if (line.length() != row.length)
			throw new IllegalStateException("wrong line length");
		
		for (int i = 0; i < row.length; ++i)
			row[i] = line.charAt(i);
	}
}
