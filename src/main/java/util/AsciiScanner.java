package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.InputMismatchException;

/**
 * A custom scanner for an ASCII input stream.
 */
public final class AsciiScanner {
    private final InputStream input;
    private final byte[] buffer;
    private int start;
    private int end; // one after the end

    private static final byte ZERO = (byte) '0';
    private static final byte NINE = (byte) '9';
    private static final byte SPACE = (byte) ' ';
    private static final byte NL = (byte) '\n';
    private static final byte TAB = (byte) '\t';
    private static final byte CR = (byte) '\r';

    /**
     * Creates a new scanner attached to the given input stream.
     *
     * @param in         the source input stream
     * @param bufferSize the buffer size
     */
    public AsciiScanner(InputStream in, int bufferSize) {
        input = in;
        buffer = new byte[bufferSize];
        start = 0;
        end = 0;
    }

    /**
     * Reads an unsigned integer.
     *
     * @return the read integer
     * @throws InputMismatchException If the number couldn't be read: either
     *                                a non-digit character is encountered or EOF is reached.
     */
    public int nextInt() {
        skipSpace();

        if (end == -1) {
            throw new InputMismatchException("expected an unsigned number, got eof");
        }

        byte c = buffer[start];
        int x = 0;

        if (c < ZERO || c > NINE) {
            throw new InputMismatchException("expected an unsigned number");
        }

        while (end != -1 && ZERO <= buffer[start] && buffer[start] <= NINE) {
            x = x * 10 + (buffer[start] - ZERO);
            start++;
            nextByte();
        }

        return x;
    }

    private void nextByte() {
        if (start == end) {
            start = 0;

            try {
                end = input.read(buffer);
            } catch (IOException io) {
                throw new UncheckedIOException(io);
            }
        }
    }

    private void skipSpace() {
        nextByte();

        while (end != -1 && isSpace(buffer[start])) {
            start++;
            nextByte();
        }
    }

    private static boolean isSpace(byte c) {
        return c == SPACE || c == NL || c == CR || c == TAB;
    }
}
