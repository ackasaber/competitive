package codeforces.beta06;

public final class CastSet {
    
    private int[] casts;
    int total;

    public CastSet(int n) {
        casts = new int[n - 2];
        total = 0;
    }

    public static CastSet anyKilling(LnBGame game) {
        int n = game.height.length;
        CastSet casts = new CastSet(n);
        casts.total = Integer.MAX_VALUE;
        return casts;
    }

    public void cast(int k, int times) {
        casts[k] += times;
        total += times;
    }

    public void copyFrom(CastSet source) {
        total = source.total;
        System.arraycopy(source.casts, 0, casts, 0, casts.length);
    }

    public void report() {
        System.out.println(total);
        for (int i = 0; i < casts.length; i++) {
            for (int j = 1; j <= casts[i]; j++) {
                System.out.print(i + 2);
                System.out.print(' ');
            }
        }
        System.out.println();
    }
    
    public void compare(CastSet other) {
        if (total != other.total)
            System.out.println("Total mismatch: " + total + " vs " + other.total);
        
        report();
        other.report();
    }
}
