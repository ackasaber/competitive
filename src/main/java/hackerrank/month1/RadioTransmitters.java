package hackerrank.month1;

import java.util.Arrays;

public class RadioTransmitters {
	
	public static int hackerlandRadioTransmitters(int[] x, int k) {
        Arrays.sort(x);
        int n = x.length;
        var s = new int[n+1];
        s[0] = 0;
        // The first transmitter that covers the current one.
        int j = 0;
        // The first house covered by transmitter j.
        int t = 0;
        
        for (int i = 0; i < n; i++) {
            while (x[i] - x[j] > k)
                j++;

            while (x[j] - x[t] > k)
                t++;
                
            s[i+1] = s[t] + 1;
        }

        return s[n];
    }

}
