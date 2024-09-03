package codeforces.beta06;

public class LnBTest {
    public static void main(String[] args) {
        test("Example 1", new LnBGame(2, 1, new int[]{ 2, 2, 2 }));
        test("Example 2", new LnBGame(3, 1, new int[]{ 1, 4, 1, 1 }));
        test("Pyramid", new LnBGame(2, 1, new int[]{ 1, 2, 3, 4, 5, 4, 3, 2, 1 }));
    }
    
    private static void test(String caseName, LnBGame game) {
        var lnb = new LnB(game);
        var sol = lnb.findBestCasts();
        var lnb2 = new LnB2(game);
        var sol2 = lnb2.findBestCasts();
        compareSolutions(caseName, sol, sol2);
    }
    
    private static void compareSolutions(String caseName, CastSet sol, CastSet sol2) {
        System.out.println(caseName);
        sol.compare(sol2);
    }
}
