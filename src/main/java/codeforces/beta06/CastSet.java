package codeforces.beta06;

import java.io.PrintWriter;

public final class CastSet {
    
    public int[] casts;
    public int total;

    public CastSet(int n) {
        casts = new int[n - 2];
    }

    public void copyFrom(CastSet source) {
        total = source.total;
        System.arraycopy(source.casts, 0, casts, 0, casts.length);
    }

    public void report(PrintWriter writer) {
        writer.println(total);
        for (int i = 0; i < casts.length; i++) {
            for (int j = 1; j <= casts[i]; j++) {
                writer.print(i + 2);
                writer.print(' ');
            }
        }
        writer.println();
    }
    
    public boolean beats(LnBGame game) {
        int a = game.a;
        int b = game.b;
        var hp = game.hp.clone();
        
        for (int i = 0; i < casts.length; i++) {
            hp[i] -= b*casts[i];
            hp[i+1] -= a*casts[i];
            hp[i+2] -= b*casts[i];
        }
        
        for (int i = 0; i < hp.length; i++) {
            if (hp[i] >= 0)
                return false;
        }
        
        return true;
    }
}
