package codeforces.beta05;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.US_ASCII;

public class TrafficRules {
    public static void main(String[] args) {
        var reader = new InputStreamReader(System.in, US_ASCII);
        var scanner = new Scanner(new BufferedReader(reader));
        int a = scanner.nextInt();
        int v = scanner.nextInt();
        int el = scanner.nextInt();
        int d = scanner.nextInt();
        int w = scanner.nextInt();
        System.out.printf("%.5f%n", movementTime(a, v, el, d, w));
    }

    public static double movementTime(int a, int v, int el, int d, int w) {
        double t;

        if (w >= v || 2*a*d <= w*w) {
            if (v*v <= 2*a*el)
                t = (double) el/v + v/(2.0*a);
            else
                t = Math.sqrt(2.0*el/a);
        } else {
            double tD;

            if (2*v*v - w*w <= 2*a*d)
                tD = (2.0*v - w)/a + (d - (2.0*v*v - w*w)/(2.0*a))/v;
            else
                tD = (Math.sqrt(2*w*w + 4.0*a*d) - w)/a;

            if (v*v - w*w <= 2*a*(el - d))
                t = tD + (double) (v - w)/a + (el - d - (v*v - w*w)/(2.0*a))/v;
            else
                t = tD + (Math.sqrt(w*w + 2.0*a*(el - d)) - w)/a;
        }

        return t;
    }
}
