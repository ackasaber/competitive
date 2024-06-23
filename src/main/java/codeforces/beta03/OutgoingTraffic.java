package codeforces.beta03;

/* Task 5A. Chat Server's Outgoing Traffic */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.US_ASCII;

/**
 * A straightforward solution using Java Pattern class.
 */
public class OutgoingTraffic {
    static final Pattern ADD_PATTERN = Pattern.compile("\\+(\\p{Alnum}+)");
    static final Pattern REMOVE_PATTERN = Pattern.compile("-(\\p{Alnum}+)");
    static final Pattern SEND_PATTERN = Pattern.compile("(\\p{Alnum}+):([\\p{Alnum} ]*)");

    /**
     * Reads the command sequence from the standard input and writes the total traffic amount for the session to the
     * standard output.
     *
     * @param args command line argument (unused)
     */
    public static void main(String[] args) {
        var reader = new InputStreamReader(System.in, US_ASCII);
        var buffered = new BufferedReader(reader);
        var scanner = new Scanner(buffered);
        int userCount = 0;
        int traffic = 0;

        while (scanner.hasNextLine()) {
            var line = scanner.nextLine();
            var addMatcher = ADD_PATTERN.matcher(line);

            if (addMatcher.matches()) {
                userCount++;
            } else {
                var removeMatcher = REMOVE_PATTERN.matcher(line);

                if (removeMatcher.matches()) {
                    userCount--;
                } else {
                    var sendMatcher = SEND_PATTERN.matcher(line);

                    if (!sendMatcher.matches())
                        throw new IllegalArgumentException("incorrect command syntax");

                    var message = sendMatcher.group(2);
                    traffic += userCount * message.length();
                }
            }
        }

        System.out.println(traffic);
    }
}
