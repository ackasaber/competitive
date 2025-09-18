package codeforces.beta06;

// D. Lizards and Basements

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.US_ASCII;

/**
 * A dynamic programming solution with 3 parameters: number of archers, hits on the last archer
 * and hits on the one after it.
 */

public class LnB2 {
    public static void main(String[] args) {
        var reader = new InputStreamReader(System.in, US_ASCII);
        var scanner = new Scanner(new BufferedReader(reader));
        var game = new LnBGame(scanner);
        var lnb = new LnB2(game);
        var sol = lnb.findBestCasts();
        var writer = new OutputStreamWriter(System.out, US_ASCII);
        var printer = new PrintWriter(new BufferedWriter(writer));
        sol.report(printer);
        printer.flush();
    }
    
    private final int a, b;
    private final int[] hp;

    public LnB2(LnBGame game) {
        a = game.a;
        b = game.b;
        hp = game.hp;
    }
    
    private final static int IMPOSSIBLE = Integer.MAX_VALUE;
    
    public CastSet findBestCasts() {
        int n = hp.length;
        var casts = new CastSet(n);
        
        if (n == 3) {
            int c = 1 + max(hp[0]/b, hp[1]/a, hp[2]/b);
            casts.casts[0] = c;
            casts.total = c;
            return casts;
        }

        // Let number archers from 0 to n-1. Archers 0 and n-1 cannot be targeted.
        // The maximum number of casts at archer i is limits[i-1]. Hitting more than that
        // won't lead to the optimal solution.
        var limits = new int[n-2];

        for (int i = 1; i <= n-2; i++) {
            limits[i-1] = 1 + max(hp[i-1]/b, hp[i]/a, hp[i+1]/b);
        }

        // Let the minumum number of casts to have first 2<=j<=n-2 archers dead while having cast
        // precisely m times at archer j-1 and k times at archer j be minCasts[j-2][m][k].
        // minChoice contains the number of casts at archer j-2 in the best solution for this configuration.
        var minCasts = new int[n-3][][]; // 0, 1 and n-1 are not valid indices for j
        var minChoice = new int[n-3][][];
        minCasts[0] = new int[limits[0] + 1][limits[1] + 1];
        minChoice[0] = new int[limits[0] + 1][limits[1] + 1];

        // Base case for j=2.
        for (int m = 0; m <= limits[0]; m++) {
            for (int k = 0; k <= limits[1]; k++)
                minCasts[0][m][k] = (b*m <= hp[0] || a*m + b*k <= hp[1]) ? IMPOSSIBLE : m+k;
        }
        
        for (int j = 3; j <= n-2; j++) {
            // Looking for first j archers dead.
            // Trying various combinations of casts at archers j-2, j-1 and j.
            int limI = limits[j-3];
            int limM = limits[j-2];
            int limK = limits[j-1];
            minCasts[j-2] = new int[limM + 1][limK + 1];
            minChoice[j-2] = new int[limM + 1][limK + 1];
            
            // m is the number of hits on archer j-1,
            // k is the number of hits on archer j,
            // i is the number of hits on archer j-2.

            for (int m = 0; m <= limM; m++) {
                for (int k = 0; k <= limK; k++) {
                    int minSol = IMPOSSIBLE;
                    int minI = 0;

                    // minCasts[j-3][i][m] gives the minimum number of casts to have first j-1
                    // archers killed while having cast precisely i times at archer j-2 and
                    // m times at archer j-1. We are not interested in this solution if this
                    // combination of m, k and i doesn't kill archer j-1.

                    for (int i = 0; i <= limI; i++) {
                        if (b*(i + k) + a*m > hp[j-1] && minCasts[j-3][i][m] < minSol) {
                            minSol = minCasts[j-3][i][m];
                            minI = i;
                        }
                    }

                    if (minSol != IMPOSSIBLE)
                        minSol += k;

                    minCasts[j-2][m][k] = minSol;
                    minChoice[j-2][m][k] = minI;
                }
            }
        }

        // Find the best suitable solution overall in the minCasts[n-4].
        int minSol = IMPOSSIBLE;
        int bestI = 0;
        int bestM = 0;
        int limI = limits[n-4];
        int limM = limits[n-3];

        for (int i = 0; i <= limI; ++i) {
            for (int m = 0; m <= limM; ++m) {
                if (b*i + a*m > hp[n-2] && b*m > hp[n-1] && minCasts[n-4][i][m] < minSol) {
                    minSol = minCasts[n-4][i][m];
                    bestI = i;
                    bestM = m;
                }
            }
        }

        // Reconstruct the solution using the back references from minChoice.
        casts.total = minSol;
        casts.casts[n-3] = bestM;
        casts.casts[n-4] = bestI;

        for (int j = n-2; j > 2; j--) {
            int newBestI = minChoice[j-2][bestI][bestM];
            casts.casts[j-3] = newBestI;
            bestM = bestI;
            bestI = newBestI;
        }

        return casts;
    }
    
    private static int max(int a, int b, int c) {
        return Math.max(Math.max(a, b), c);
    }
}
