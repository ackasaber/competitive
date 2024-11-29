package hackerrank.month1;

public class NewYearChaos {

    public static int minimumBribes(int[] q) {
        // Count the inversions but also limit the number
        // of inversions with two per the maximum element of an inversion.
        int totalBribes = 0;
        int n = q.length;
        
        for (int i = n - 2; i >= 0; i--) {
            int x = q[i];
            int bribeCount = 0;
            int j = i;
            
            while (j+1 < n && bribeCount <= 2 && q[j+1] < x) {
                q[j] = q[j+1];
                j++;
                bribeCount++;
            }
            
            if (bribeCount > 2)
                return -1;
            
            q[j] = x;
            totalBribes += bribeCount;
        }
        
        return totalBribes;
    }
	
}
