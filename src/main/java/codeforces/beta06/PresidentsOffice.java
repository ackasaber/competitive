package codeforces.beta06;

// B. President's Office

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.US_ASCII;

public class PresidentsOffice {
    public static void main(String[] args) {
        var reader = new InputStreamReader(System.in, US_ASCII);
        var scanner = new Scanner(new BufferedReader(reader));
        int rowCount = scanner.nextInt();
        int columnCount = scanner.nextInt();
        char presidentLetter = scanner.next().charAt(0);
        var room = new PresidentsOffice(rowCount, columnCount);
        room.read(scanner);
        var table = room.findTable(presidentLetter);
        int neighbourCount = table.countNeighbours();
        System.out.println(neighbourCount);
    }

    private final int rowCount, columnCount;
    private final char[][] layout;

    public PresidentsOffice(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        // +1 is for sentinels
        this.layout = new char[rowCount+1][columnCount+1];
    }

    public void read(Scanner scanner) {
        for (int row = 0; row < rowCount; row++) {
            String line = scanner.next();

            if (line.length() != columnCount)
                throw new IllegalArgumentException("unexpected amount of columns");

            for (int column = 0; column < columnCount; column++)
                layout[row][column] = line.charAt(column);
        }
    }

    private void addRow(LetterSet dest, int row, int column, int width) {
        for (int i = 0; i < width; i++) {
            char c = layout[row][column + i];

            if (c != '.')
                dest.add(c);
        }
    }

    private void addColumn(LetterSet dest, int column, int row, int height) {
        for (int i = 0; i < height; i++) {
            char c = layout[row + i][column];

            if (c != '.')
                dest.add(c);
        }
    }

    private class Table {
        private final int topRow, leftColumn, width, height;

        public Table(int topRow, int leftColumn, int width, int height) {
            this.topRow = topRow;
            this.leftColumn = leftColumn;
            this.width = width;
            this.height = height;
        }

        public int countNeighbours() {
            var neighbours = new LetterSet();

            if (topRow > 0)
                addRow(neighbours, topRow - 1, leftColumn, width);

            if (topRow + height < rowCount)
                addRow(neighbours, topRow + height, leftColumn, width);

            if (leftColumn > 0)
                addColumn(neighbours, leftColumn - 1, topRow, height);

            if (leftColumn + width < columnCount)
                addColumn(neighbours, leftColumn + width, topRow, height);

            return neighbours.size();
        }
    }

    public Table findTable(char letterCode) {
        int top = 0, left = 0;
        layout[rowCount][0] = letterCode;

        while (layout[top][left] != letterCode) {
            left++;

            if (left == columnCount) {
                top++;
                left = 0;
            }
        }

        if (top == rowCount)
            throw new IllegalArgumentException("table letter code not found on the layout");

        int width = 0;
        layout[top][columnCount] = '.';

        while (layout[top][left + width] == letterCode)
            width++;

        int height = 0;
        layout[rowCount][left] = '.';

        while (layout[top + height][left] == letterCode)
            height++;

        return new Table(top, left, width, height);
    }

    private static class LetterSet {
        private static final int LETTER_COUNT = 'Z' - 'A' + 1;
        private final boolean[] hasLetter = new boolean[LETTER_COUNT];

        public void add(char letter) {
            hasLetter[letter - 'A'] = true;
        }

        public int size() {
            int count = 0;

            for (int i = 0; i < LETTER_COUNT; i++) {
                if (hasLetter[i])
                    count++;
            }

            return count;
        }
    }
}
