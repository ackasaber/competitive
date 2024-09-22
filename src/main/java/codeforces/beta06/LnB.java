package codeforces.beta06;

// D. Lizards and Basements

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Arrays;

import static java.nio.charset.StandardCharsets.US_ASCII;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple backtracking solution.
 */
public class LnB {

    public static void main(String[] args) {
        var reader = new InputStreamReader(System.in, US_ASCII);
        var scanner = new Scanner(new BufferedReader(reader));
        var game = new LnBGame(scanner);
        var lnb = new LnB(game);
        var sol = lnb.findBestCasts();
        var writer = new OutputStreamWriter(System.out, US_ASCII);
        var printer = new PrintWriter(new BufferedWriter(writer));
        sol.report(printer);
        printer.flush();
    }

    private static final Logger logger = LoggerFactory.getLogger(LnB.class);
    
    private final int a, b;
    private final int[] hp;
    private final CastSet current, best;

    public LnB(LnBGame game) {
        a = game.a;
        b = game.b;
        hp = game.hp.clone();
        current = new CastSet(hp.length);
        best = new CastSet(hp.length);
        best.total = Integer.MAX_VALUE;
    }
    
    public CastSet findBestCasts() {
        searchBestCasts(1);
        return best;
    }

    private void searchBestCasts(int k) {
        logger.atDebug().setMessage("searchBestCasts {} {}")
                .addArgument(k).addArgument(() -> Arrays.toString(hp)).log();
        
        if (current.total >= best.total)
            return;

        int n = hp.length;

        if (k == n-2) {
            int lastHits = max(
                    killingBarrage(k-1, b),
                    killingBarrage(k, a),
                    killingBarrage(k+1, b)
            );
            logger.debug("last position hits = {}", lastHits);

            if (current.total + lastHits < best.total) {
                fire(k, lastHits);
                logger.debug("better solution with {} hits", current.total);
                best.copyFrom(current);
                fire(k, -lastHits);
            }
            return;
        }

        int minHits = killingBarrage(k-1, b);
        int maxHits = killingBarrage(k, a);
        logger.debug("minHits = {} to maxHits = {}", minHits, maxHits);
        fire(k, minHits);
        searchBestCasts(k+1);

        if (maxHits > minHits) {
            for (int hits = minHits + 1; hits <= maxHits; hits++) {
                fire(k, 1);
                searchBestCasts(k+1);
            }

            fire(k, -maxHits);
        } else
            fire(k, -minHits);
    }

    private void fire(int k, int times) {
        hp[k-1] -= b*times;
        hp[k] -= a*times;
        hp[k+1] -= b*times;
        current.casts[k-1] += times;
        current.total += times;
    }

    private int max(int x, int y, int z) {
        return Math.max(Math.max(x, y), z);
    }

    public int killingBarrage(int k, int damage) {
        if (hp[k] < 0)
            return 0;
        return hp[k]/damage + 1;
    }
}
