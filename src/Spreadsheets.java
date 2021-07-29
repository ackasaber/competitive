/* Task 1B. Spreadsheets */

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The task solution.
 */
public final class Spreadsheets {
	/**
	 * Read the cell coordinates from the standard input and write them in a
	 * different notation to the standard output.
	 *
	 * @param args command line arguments (ignored)
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in, "UTF-8");
		int n = scanner.nextInt();

		if (n < 1 || n > 100_000) {
			throw new InputMismatchException("Illegal N");
		}

		for (int i = 1; i <= n; ++i) {
			String token = scanner.next();
			InputItem item = InputItem.of(token);

			switch (item.format) {
			case RX_CY:
				System.out.println(item.coord.toABCString());
				break;
			case ABC_X:
				System.out.println(item.coord.toRCString());
				break;
			}
		}
	}
}

/**
 * Type of cell notation.
 */
enum CellNotation {
	/**
	 * Cell notation with numeric row and column indexes.
	 */
	RX_CY,
	/**
	 * Cell notation with alphabetic column index and numeric row index.
	 */
	ABC_X
}

/**
 * Cell coordinate: row and column index pair.
 *
 * Indexing starts from one.
 */
final class CellCoord {
	/**
	 * Cell row index.
	 */
	private final int row;
	/**
	 * Cell column index.
	 */
	private final int column;

	/**
	 * Creates a new cell coordinate.
	 *
	 * @param row    row index, >= 1
	 * @param column column index, >= 1
	 */
	public CellCoord(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Returns the cell coordinate string representation with alphabetic column
	 * index.
	 *
	 * @return the cell coordinate string representation
	 */
	public String toABCString() {
		return spellColumnName(column) + row;
	}

	/**
	 * Returns the cell coordinate string representation with numeric indices.
	 *
	 * @return the cell coordinate string representation
	 */
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
			int rem = n % 26;
			int digit;

			if (rem == 0) {
				n = n / 26 - 1;
				digit = 26;
			} else {
				n = n / 26;
				digit = rem;
			}

			buffer.append((char) ('A' + digit - 1));
		}

		buffer.reverse();
		return buffer.toString();
	}
}

/**
 * A token of input as per task description.
 */
final class InputItem {
	/**
	 * Regular expression for {@code CellNotation.ABC_X}.
	 */
	private static final Pattern ABC_PATTERN = Pattern.compile("([A-Z]+)([0-9]+)");
	/**
	 * Regular expression for {@code CellNotation.RX_CY}.
	 */
	private static final Pattern RXCY_PATTERN = Pattern.compile("R([0-9]+)C([0-9]+)");

	/**
	 * Maximum row and column indices as per task description.
	 */
	private static final int MAX_INDEX = 1_000_000;
	/**
	 * Maximum number of letters in a column alphabetic index given
	 * {@code MAX_INDEX} limit.
	 */
	private static final int MAX_LETTERS = 5;

	/**
	 * Cell index notation type.
	 */
	final CellNotation format;
	/**
	 * Cell coordinates.
	 */
	final CellCoord coord;

	/**
	 * Creates a new input item.
	 *
	 * @param format cell notation format of the input item
	 * @param row    cell row index of the input item
	 * @param column cell column index of the input item
	 */
	public InputItem(CellNotation format, int row, int column) {
		this.format = format;
		this.coord = new CellCoord(row, column);
	}

	/**
	 * Creates an input item from the given cell coordinate representation.
	 *
	 * @param token cell coordinate representation
	 * @return a new input item
	 * @throws InputMismatchException If the given token cannot be a valid cell
	 *                                coordinate representation
	 */
	public static InputItem of(String token) {
		Matcher matcherABC = ABC_PATTERN.matcher(token);
		Matcher matcherRXCY = RXCY_PATTERN.matcher(token);

		int column;
		int row;
		CellNotation format;

		if (matcherABC.matches()) {
			column = columnNumber(matcherABC.group(1));
			row = indexNumber(matcherABC.group(2));
			format = CellNotation.ABC_X;
		} else if (matcherRXCY.matches()) {
			column = indexNumber(matcherRXCY.group(2));
			row = indexNumber(matcherRXCY.group(1));
			format = CellNotation.RX_CY;
		} else {
			throw new InputMismatchException("Wrong cell notation");
		}

		return new InputItem(format, row, column);
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
		if (name.length() > MAX_LETTERS) {
			throw new InputMismatchException("Column name is too long");
		}

		int n = 0;

		for (int i = 0; i < name.length(); ++i) {
			int digitValue = name.charAt(i) - 'A' + 1;
			n = n * 26 + digitValue;
		}

		return n;
	}

	/**
	 * Parses the numeric row or column index.
	 *
	 * @param indexText the index in a string form
	 * @return the row or column numeric index
	 * @throws InputMismatchException If the index doesn't conform to the task
	 *                                description.
	 */
	private static int indexNumber(String indexText) {
		int index;

		try {
			index = Integer.parseInt(indexText);
		} catch (NumberFormatException ex) {
			throw (InputMismatchException) new InputMismatchException("Can't parse cell index").initCause(ex);
		}

		if (index < 1 || index > MAX_INDEX) {
			throw new InputMismatchException("Illegal cell index");
		}

		return index;
	}
}
