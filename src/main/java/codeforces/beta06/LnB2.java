package codeforces.beta06;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.nio.charset.StandardCharsets.US_ASCII;

public class LnB2 {
    private static final Logger logger = LoggerFactory.getLogger(LnB2.class);
    
    public static void main(String[] args) {
        var reader = new InputStreamReader(System.in, US_ASCII);
        var scanner = new Scanner(new BufferedReader(reader));
        var game = new LnBGame(scanner);
        var lnb = new LnB2(game);
        var casts = lnb.findBestCasts();
        casts.report();
    }
    
    private final LnBGame game;
    
    public LnB2(LnBGame game) {
        this.game = game;
    }
    
    public CastSet findBestCasts() {
        int p = game.height[0];
        int q = game.height[1];
        int r = game.height[2];
        int n = game.height.length;
        var f = new int[n-2][][];
        f[0] = new int[q+1][r+1];
        int m0 = p/game.b + 1;
        
        for (int j = 0; j <= q; j++) {
            for (int k = 0; k <= r; k++) {
                f[0][j][k] = max(j/game.a + 1, k/game.b + 1, m0);
            }
        }
        
        logger.debug("Solution for archer 2");
        printTable(f[0]);
        
        for (int i = 1; i < n-2; i++) {
            p = q;
            q = r;
            r = game.height[i+2];
            f[i] = new int[q+1][r+1];

            for (int j = 0; j <= q; j++) {
                int m = f[i-1][0][j];

                for (int k = 1; k <= p; k++) {
                    if (f[i-1][k][j] < m)
                        m = f[i-1][k][j];
                }

                for (int k = 0; k <= r; k++) {
                   f[i][j][k] = m + max(j/game.a + 1, k/game.b + 1);
                }
            }

            logger.debug("Solution for the archer {}", i+2);
            printTable(f[i]);
        }
        
        var cumul = new int[n-2];
        int hp = game.height[n-1];
        
        for (int i = n-3; i > 0; i--) {
            logger.debug("deciding casts for archer {}", i+2);
            logger.debug("aim at hp {}", hp);
            int best = f[i][0][hp];
            int prevHp = 0;
            
            for (int k = 1; k <= game.height[i-1]; k++) {
                if (f[i][k][hp] < best) {
                    best = f[i][k][hp];
                    prevHp = k;
                }
            }
            
            logger.debug("best # of casts {} achieved when prev. archer at {} hp", best, prevHp);
            cumul[i] = best;
            hp = prevHp;
        }
        
        cumul[0] = f[0][game.height[0]][hp];
        logger.debug("best # of casts {} to shoot archer 2", cumul[0]);
        var casts = new CastSet(n);
        casts.cast(0, cumul[0]);
        
        for (int i = 1; i <= n-3; i++)
            casts.cast(i, cumul[i] - cumul[i-1]);
        
        return casts;
    }
    
    private static int max(int a, int b, int c) {
        int max = a;
        
        if (b > max)
            max = b;
        
        if (c > max)
            max = c;
        
        return max;
    }
    
    private static int max(int a, int b) {
        return a > b ? a : b;
    }
    
    private static void printTable(int[][] f) {
        int q = f.length - 1;
        int r = f[0].length - 1;

        var line = new StringBuilder();
        
        for (int k = 0; k <= r; k++) {
            line.append('\t');
            line.append(k);
        }
        
        logger.debug("{}", line);
        
        for (int h = 0; h <= q; h++) {
            line.setLength(0);
            line.append(h);
            
            for (int k = 0; k <= r; k++) {
                line.append('\t');
                line.append(f[h][k]);
            }
            
            logger.debug("{}", line);
        }
    }
}
