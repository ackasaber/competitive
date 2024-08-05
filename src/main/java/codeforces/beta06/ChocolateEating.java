package codeforces.beta06;

// C. Alice, Bob and Chocolate

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.US_ASCII;

/**
 * We model the game turn by turn. At each turn we decide who's going to eat the next chocolate
 * bar. For both players we count how many chocolate bars they've eaten so far and the overall time
 * to eat this amount. The next chocolate bar is for the player who manages their current chocolate
 * bar first. By the task description, Alice breaks the ties.
 */
public class ChocolateEating {
    public static void main(String[] args) {
        var reader = new InputStreamReader(System.in, US_ASCII);
        var scanner = new Scanner(new BufferedReader(reader));
        int n = scanner.nextInt();
        var t = new int[n];

        for (int i = 0; i < n; i++)
            t[i] = scanner.nextInt();

        int a = 0, b = 0, tA = 0, tB = 0;

        while (a + b < n) {
            if (tA <= tB) {
                tA += t[a];
                a++;
            } else {
                tB += t[n - 1 - b];
                b++;
            }
        }

        System.out.printf("%d %d%n", a, b);
    }
}
