package codeforces.beta03;

// A. The shortest king's path

import java.util.Scanner;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.lang.Integer.max;
import static java.lang.Math.abs;
import static java.lang.System.out;
 
public class KingsPath {
 
	public static void main(String[] args) {
		var scanner = new Scanner(System.in, US_ASCII);
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
    
    private static class ChessPosition {
        // Chess board column, a-h.
        char column;
        // Chess board row, 1-8.
        char row;

        // Pattern matcher for reading the chess piece position.
        static final Pattern PATTERN = Pattern.compile("[a-h][1-8]");

        public static ChessPosition next(Scanner scanner) {
            var token = scanner.next(PATTERN);
            var pos = new ChessPosition();
            pos.column = token.charAt(0);
            pos.row = token.charAt(1);
            return pos;
        }
    }
}
 
