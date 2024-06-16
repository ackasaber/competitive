package codeforces.beta03;

/* Task 3A. The shortest king's path. */

import java.util.Scanner;
import java.util.regex.Pattern;
import static java.lang.System.out;

import java.nio.charset.StandardCharsets;

import static java.lang.Integer.max;
 
/**
 * The task solution.
 */
public class KingsPath {
 
	/**
	 * Reads the board positions and outputs the shortest king's path from one to
	 * the other.
	 *
	 * @param args command line arguments (unused)
	 */
	public static void main(String[] args) {
		var scanner = new Scanner(System.in, StandardCharsets.US_ASCII);
		var s = ChessPosition.next(scanner);
		var t = ChessPosition.next(scanner);
 
		// For a single step we only change the column/row by one,
		// thus we take 'steps' steps to just reach the farthest coordinate.
		// The nearest coordinate doesn't increase the steps count,
		// thus 'steps' is the optimal number of steps.
		int steps = max(abs(s.column - t.column), abs(s.row - t.row));
		out.println(steps);
 
		// simulate the movement
		while (s.column != t.column || s.row != t.row) {
			if (s.column < t.column) {
				out.print('R');
				++s.column;
			} else if (s.column > t.column) {
				out.print('L');
				--s.column;
			}
 
			if (s.row < t.row) {
				out.print('U');
				++s.row;
			} else if (s.row > t.row) {
				out.print('D');
				--s.row;
			}
 
			out.println();
		}
	}
 
	/**
	 * Absolute value of the integer number.
	 *
	 * @param x an integer
	 * @return its absolute value
	 */
	private static int abs(int x) {
		return x >= 0 ? x : -x;
	}
}
 
/**
 * A position of a chess piece on the board.
 */
class ChessPosition {
	/**
	 * Chess board column, a letter a-h.
	 */
	public char column;
	/**
	 * Chess board row, a number 1-8.
	 */
	public char row;
 
	/**
	 * Pattern matcher for reading the chess piece position.
	 */
	private static final Pattern PATTERN = Pattern.compile("[a-h][1-8]");
 
	/**
	 * Reads the chess piece position in the standard chess notation.
	 *
	 * @param scanner the scanner to read the position from
	 * @return the read chess piece position
	 */
	public static ChessPosition next(Scanner scanner) {
		var token = scanner.next(PATTERN);
		var pos = new ChessPosition();
		pos.column = token.charAt(0);
		pos.row = token.charAt(1);
		return pos;
	}
}