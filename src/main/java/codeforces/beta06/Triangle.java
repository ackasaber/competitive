package codeforces.beta06;

// A. Triangle

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.US_ASCII;

public class Triangle {
    private static boolean isTriangle(int a, int b, int c) {
        return a + b > c && a + c > b && b + c > a;
    }

    private static boolean isSegment(int a, int b, int c) {
        return a + b == c || a + c == b || b + c == a;
    }

    public static void main(String[] args) {
        var reader = new InputStreamReader(System.in, US_ASCII);
        var scanner = new Scanner(new BufferedReader(reader));

        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        int d = scanner.nextInt();

        if (isTriangle(a, b, c) || isTriangle(a, b, d) || isTriangle(a, c, d) || isTriangle(b, c, d))
            System.out.println("TRIANGLE");
        else if (isSegment(a, b, c) || isSegment(a, b, d) || isSegment(a, c, d) || isSegment(b, c, d))
            System.out.println("SEGMENT");
        else
            System.out.println("IMPOSSIBLE");
    }
}
