package codeforces.beta01;

// B. Spreadsheets

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.nio.charset.StandardCharsets.US_ASCII;

public class Spreadsheets {
	public static void main(String[] args) {
		var reader = new InputStreamReader(System.in, US_ASCII);
		Scanner scanner = new Scanner(new BufferedReader(reader));
		int n = scanner.nextInt();

		if (n < 1 || n > 100_000)
			throw new InputMismatchException("n out of range");

		var item = new InputItem();
		for (int i = 1; i <= n; i++) {
			String token = scanner.next();
			item.parseFrom(token);
			String formatted = switch (item.format) {
				case RX_CY -> item.coordinate.toABCString();
				case ABC_X -> item.coordinate.toRCString();
			};
			System.out.println(formatted);
		}
	}

	enum CellNotation {
		RX_CY,
		ABC_X
	}

	private static final int LETTER_COUNT = 'Z' - 'A' + 1;

	static final class CellCoordinate {
		private int row;
		private int column;

		public String toABCString() {
			return spellColumnName(column) + row;
		}

		public String toRCString() {
			return "R" + row + "C" + column;
		}

		/**
		 * Returns the column alphabetic index by the given numeric index.
		 *
		 * @param n numeric column index
		 * @return column alphabetic index
		 */
		private static String spellColumnName(int n) {
			var buffer = new StringBuilder();

			while (n > 0) {
				int rem = n % LETTER_COUNT;
				int digit;

				if (rem == 0) {
					n = n / LETTER_COUNT - 1;
					digit = LETTER_COUNT;
				} else {
					n = n / LETTER_COUNT;
					digit = rem;
				}

				buffer.append((char) ('A' + digit - 1));
			}

			buffer.reverse();
			return buffer.toString();
		}
	}

	static final class InputItem {
		private static final Pattern ABC_PATTERN = Pattern.compile("([A-Z]+)([0-9]+)");
		private static final Pattern RXCY_PATTERN = Pattern.compile("R([0-9]+)C([0-9]+)");

		/**
		 * Maximum row and column indices as per task description.
		 */
		private static final int MAX_INDEX = 1_000_000;
		/**
		 * Maximum number of letters in a column alphabetic index given the {@code MAX_INDEX} limit.
		 */
		private static final int MAX_LETTERS = 5;

		CellNotation format = CellNotation.RX_CY;
		CellCoordinate coordinate = new CellCoordinate();

		/**
		 * Parse the input item from the given cell coordinate representation.
		 *
		 * @param token cell coordinate representation
		 * @throws InputMismatchException If the given token
		 * cannot be a valid cell coordinate representation
		 */
		public void parseFrom(String token) {
			Matcher matcherABC = ABC_PATTERN.matcher(token);
			Matcher matcherRXCY = RXCY_PATTERN.matcher(token);

			if (matcherABC.matches()) {
				coordinate.column = columnNumber(matcherABC.group(1));
				coordinate.row = indexNumber(matcherABC.group(2));
				format = CellNotation.ABC_X;
			} else if (matcherRXCY.matches()) {
				coordinate.column = indexNumber(matcherRXCY.group(2));
				coordinate.row = indexNumber(matcherRXCY.group(1));
				format = CellNotation.RX_CY;
			} else {
				throw new InputMismatchException("Wrong cell notation");
			}
		}

		/**
		 * Parses the column index from its alphabetic representation.
		 *
		 * @param name the column alphabetic index
		 * @return the column numeric index
		 * @throws InputMismatchException If the column index doesn't conform to the
		 *                                task description.
		 */
		private static int columnNumber(String name) {
			if (name.length() > MAX_LETTERS)
				throw new InputMismatchException("Column name is too long");

			int n = 0;

			for (int i = 0; i < name.length(); ++i) {
				int digitValue = name.charAt(i) - 'A' + 1;
				n = n * LETTER_COUNT + digitValue;
			}

			return n;
		}

		/**
		 * Parses the numeric row or column index.
		 *
		 * @param indexText the index in a string form
		 * @return the row or column numeric index
		 * @throws InputMismatchException If the index doesn't conform to the task description.
		 */
		private static int indexNumber(String indexText) {
			int index;

			try {
				index = Integer.parseInt(indexText);
			} catch (NumberFormatException ex) {
				// This exception doesn't have the full set of constructors.
				var newEx = new InputMismatchException("Can't parse cell index");
				throw (InputMismatchException) newEx.initCause(ex);
			}

			if (index < 1 || index > MAX_INDEX)
				throw new InputMismatchException("Illegal cell index");

			return index;
		}
	}
}