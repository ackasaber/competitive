package codeforces.beta05;

// B. Longest Regular Bracket Sequence

import util.IntStack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.nio.charset.StandardCharsets.US_ASCII;

/**
 * The dynamic programming solution with a stack to track brackets.
 *
 * <p>For all prefixes of the input sequence we'll find the longest suffix of this prefix that forms
 * a regular bracket sequence. The answer then corresponds to such prefixes of maximum length.</p>
 *
 * <p>Let <i>P</i><sub><i>i</i></sub> be the prefix of the input sequence of the length <i>i</i>
 * and <i>L</i><sub><i>i</i></sub> is the length of its longest suffix that is a regular bracket
 * sequence. Let's find a recurrence that defines <i>L</i><sub><i>i</i></sub>.</p>
 *
 * <p>Clearly, if the prefix <i>P</i><sub><i>i</i></sub> ends in an opening bracket,
 * <i>L</i><sub><i>i</i></sub> = 0. When the prefix ends in a closing bracket, there are two
 * cases possible. If this closing bracket corresponds to a prior opening bracket and
 * <i>b</i> is this opening bracket (zero-based) index, then</p>
 *
 * <p><i>L</i><sub><i>i</i></sub> = (<i>i</i> - <i>b</i> + 1) +
 * <i>L</i><sub><i>b</i></sub>.</p>
 *
 * <p>Here <i>i</i> - <i>b</i> + 1 is the length of the last fully regular segment of the prefix
 * between the last closing bracket and its corresponding opening bracket inclusive.</p>
 *
 * <p>If the closing bracket doesn't correspond to an opening bracket,
 * <i>L</i><sub><i>i</i></sub> = 0.</p>
 *
 * <p>The index <i>b</i> can be found using a stack of open brackets with no corresponding
 * closing brackets found yet.</p>
 */
public class BracketSequence {

    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in, US_ASCII));
        var sequence = reader.readLine();
        var answer = longestRegularSubstring(sequence);
        System.out.println(answer.length + " " + answer.count);
    }

    private static Answer longestRegularSubstring(String s) {
        var answer = new Answer();
        int n = s.length();
        var openBrackets = new IntStack(n);
        var longestRegularSuffix = new int[n + 1];

        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '(': openBrackets.push(i); break;
                case ')':
                    if (!openBrackets.isEmpty()) {
                        int lastOpen = openBrackets.pop();
                        int currentSegment = i - lastOpen + 1;
                        int bestBefore = longestRegularSuffix[lastOpen];
                        int length = bestBefore + currentSegment;
                        longestRegularSuffix[i + 1] = length;
                        answer.update(length);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("not a bracket sequence");
            }
        }

        return answer;
    }

    private static class Answer {
        int length = 0;
        int count = 1;

        public void update(int candidate) {
            if (candidate > length) {
                length = candidate;
                count = 1;
            } else if (candidate == length)
                count++;
        }
    }
}
