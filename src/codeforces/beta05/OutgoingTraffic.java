package codeforces.beta05;

// A. Chat Server's Outgoing Traffic

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Pattern;
import static java.nio.charset.StandardCharsets.US_ASCII;

public class OutgoingTraffic {
    static final Pattern ADD_PATTERN = Pattern.compile("\\+(\\p{Alnum}+)");
    static final Pattern REMOVE_PATTERN = Pattern.compile("-(\\p{Alnum}+)");
    static final Pattern SEND_PATTERN = Pattern.compile("(\\p{Alnum}+):([\\p{Alnum} ]*)");

    public static void main(String[] args) {
        var reader = new InputStreamReader(System.in, US_ASCII);
        var scanner = new Scanner(new BufferedReader(reader));
        int userCount = 0;
        int traffic = 0;

        while (scanner.hasNextLine()) {
            var line = scanner.nextLine();

            if (ADD_PATTERN.matcher(line).matches())
                userCount++;
            else if (REMOVE_PATTERN.matcher(line).matches())
                userCount--;
            else {
                var sendMatcher = SEND_PATTERN.matcher(line);

                if (!sendMatcher.matches())
                    throw new IllegalArgumentException("incorrect command syntax");

                var message = sendMatcher.group(2);
                traffic += userCount * message.length();
            }
        }

        System.out.println(traffic);
    }
}
