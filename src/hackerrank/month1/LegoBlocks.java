package hackerrank.month1;

public class LegoBlocks {
	
	private static final int MOD = 1000_000_007;
	
    public static int legoBlocks(int n, int m) {
		// First let's find the solution when the vertical break is allowed.
		// For a single row.
		var s = new int[m+1];
		s[0] = 1;
		s[1] = 1;
		
		if (m >= 2)
			s[2] = 2;
			
		if (m >= 3)
			s[3] = 4;

		for (int i = 4; i <= m; i++)
			s[i] = add(add(add(s[i-1], s[i-2]), s[i-3]), s[i-4]);
		
		// For n rows: without the vertical break rule, all combinations are possible.
		for (int i = 0; i <= m; i++)
			s[i] = pow(s[i], n);
		
		// Now introduce the vertical breaks ban.
		// We exclude the combinations where the wall consists of two subwalls:
		// left one with no vertical breaks of width i and the right one of width m-i
		// without vertical breaks restriction, 1 <= i < m.
		// The number of right subwalls is computed recurrently.
		var d = new int[m+1];
		d[0] = 0; // actually don't need this one
		
		for (int k = 1; k <= m; k++) {
			d[k] = s[k];
			
			for (int i = 1; i < k; i++)
				d[k] = sub(d[k], times(d[i], s[k-i]));
		}

		return d[m];
    }
	
	private static int pow(int base, int power) {
		int result = 1;
		
		for (int i = 0; i < power; i++) {
			result = times(result, base);
		}
		
		return result;
	}
	
	private static int add(int a, int b) {
		return (a + b) % MOD;
	}
	
	private static int times(int a, int b) {
		long p = a;
		p = (p * b) % MOD;
		return (int) p;
	}
	
	private static int sub(int a, int b) {
		return Math.floorMod(a - b, MOD);
	}
}
