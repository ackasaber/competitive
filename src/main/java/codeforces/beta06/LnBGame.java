package codeforces.beta06;

import java.util.Scanner;

public class LnBGame {
    public int a, b;
    public int[] height;

    public LnBGame(int a, int b, int[] height) {
        this.a = a;
        this.b = b;
        this.height = height;
    }
    
    public LnBGame(Scanner scanner) {
        int n = scanner.nextInt();
        
        if (n < 3)
            throw new IllegalArgumentException("n < 3");
        
        a = scanner.nextInt();
        b = scanner.nextInt();
        
        if (a <= b)
            throw new IllegalArgumentException("a >= b");
        
        height = new int[n];
        
        for (int i = 0; i < n; i++) {
            int h = scanner.nextInt();
            
            if (h < 1 || h > 15)
                throw new IllegalArgumentException("h must be between 1 and 15");
            
            height[i] = h;
        }
    }
}
