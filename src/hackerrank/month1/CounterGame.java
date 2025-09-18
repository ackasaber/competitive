package hackerrank.month1;

// Counter game

public class CounterGame {

	public static String counterGame(long n) {
        // First count the trailing zeroes: they correspond to divisions by 2 for powers of 2.
        int powerReductCount = countTrailing0s(n);
		// Then count the set bits in n.
        int setBitCount = count1s(n);
		// Number of game turns.
        int turnCount = setBitCount - 1 + powerReductCount;
        return turnCount % 2 == 0 ? "Richard" : "Louise";
    }
    
    static int count1s(long x) {
        x = (x & 0x5555555555555555L) + ((x & 0xAAAAAAAAAAAAAAAAL) >>> 1);
        x = (x & 0x3333333333333333L) + ((x & 0xCCCCCCCCCCCCCCCCL) >>> 2);
        x = (x & 0x0F0F0F0F0F0F0F0FL) + ((x & 0xF0F0F0F0F0F0F0F0L) >>> 4);
        x = (x & 0x00FF00FF00FF00FFL) + ((x & 0xFF00FF00FF00FF00L) >>> 8);
        x = (x & 0x0000FFFF0000FFFFL) + ((x & 0xFFFF0000FFFF0000L) >>> 16);
        x = (x & 0x00000000FFFFFFFFL) + ((x & 0xFFFFFFFF00000000L) >>> 32);
        return (int) x;
    }

    static int countTrailing0s(long x) {
		assert x != 0;
        int count = 0;
        if ((x & 0xFFFFFFFFL) == 0) {
            count += 32;
            x >>>= 32;
        }
        
        if ((x & 0xFFFFL) == 0) {
            count += 16;
            x >>>= 16;
        }
        
        if ((x & 0xFF) == 0) {
            count += 8;
            x >>>= 8;
        }
        
        if ((x & 0xF) == 0) {
            count += 4;
            x >>>= 4;
        }
        
        if ((x & 0x3) == 0) {
            count += 2;
            x >>>= 2;
        }
        
        if ((x & 0x1) == 0)
            count += 1;
        
        return count;
    }
}
