package codeforces.beta06;

import java.util.random.RandomGeneratorFactory;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

public class LnBTest {
    
    private static final int MAX_ARCHERS = 10;
    private static final int RAND_SEED = 310721;
    private static final int RAND_TRIES = 50000;
    
    @Test
    void randomlyGenerated() {
        var game = new LnBGame(5, 2, new int[MAX_ARCHERS]);
        var randFactory = RandomGeneratorFactory.of("Xoroshiro128PlusPlus");
        var rand = randFactory.create(RAND_SEED);
        
        for (int i = 0; i < RAND_TRIES; i++) {
            for (int j = 0; j < game.hp.length; j++)
                game.hp[j] = rand.nextInt(1, 16);
            checkSolutionsFor(game);
        }
    }
    
    @ParameterizedTest
    @MethodSource
    void smallCases(LnBGame game) {
        checkSolutionsFor(game);
    }
    
    private static Stream<LnBGame> smallCases() {
        return Stream.of(
            new LnBGame(2, 1, new int[]{ 2, 2, 2 }),
            new LnBGame(3, 1, new int[]{ 1, 4, 1, 1 }),
            new LnBGame(2, 1, new int[]{ 1, 2, 3, 4, 5, 4, 3, 2, 1 }),
            new LnBGame(3, 2, new int[]{ 1, 2, 1, 2, 1 }),
            new LnBGame(2, 1, new int[]{ 1, 2, 1, 2, 1 })
        );
    }
    
    private void checkSolutionsFor(LnBGame game) {
        var lnb = new LnB(game);
        var sol = lnb.findBestCasts();
        var lnb2 = new LnB2(game);
        var sol2 = lnb2.findBestCasts();
        assertEquals(sol2.total, sol.total);
        assertTrue(sol.beats(game));
        assertTrue(sol2.beats(game));
    }
}
