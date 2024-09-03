package codeforces.beta06;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.nio.charset.StandardCharsets.US_ASCII;

public class LnB {
    private static final Logger logger = LoggerFactory.getLogger(LnB.class);
    
    public static void main(String[] args) {
        var reader = new InputStreamReader(System.in, US_ASCII);
        var scanner = new Scanner(new BufferedReader(reader));
        var game = new LnBGame(scanner);
        var lnb = new LnB(game);
        var sol = lnb.findBestCasts();
        sol.report();
    }

    private LnBGame game;
    private CastSet current, best;

    public LnB(LnBGame game) {
        this.game = game;
        current = new CastSet(game.height.length);
        best = CastSet.anyKilling(game);
    }
    
    public CastSet findBestCasts() {
        searchBestCasts(2);
        return best;
    }

    private void searchBestCasts(int k) {
        logger.debug("searchBestCasts {}", k);
        report();
        
        if (current.total >= best.total)
            return;

        int n = game.height.length;

        if (k == n-1) {
            int lastHits = max(
                    killingBarrage(k-1, game.b),
                    killingBarrage(k, game.a),
                    killingBarrage(k+1, game.b)
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

        int minHits = killingBarrage(k-1, game.b);
        int maxHits = killingBarrage(k, game.a);
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
        damage(k-1, game.b*times);
        damage(k, game.a*times);
        damage(k+1, game.b*times);
        current.cast(k-2, times);
    }

    private int max(int x, int y, int z) {
        int m = x;

        if (y > m)
            m = y;

        if (z > m)
            m = z;

        return m;
    }

    public void damage(int k, int units) {
        game.height[k-1] -= units;
    }

    public int killingBarrage(int k, int damage) {
        int hp = game.height[k-1];

        if (hp < 0)
            return 0;

        return hp/damage + 1;
    }

    public void report() {
        var line = new StringBuilder();
        
        for (int j = 0; j < game.height.length; j++) {
            line.append(game.height[j]);
            line.append(' ');
        }
        
        logger.debug("{}", line);
    }
}
