package coderun.dp;

public class InterleavingString {

	public static void main(String[] args) {
		System.out.println(isInterleave("aabcc", "dbbca", "aadbbcbcac"));
		System.out.println(isInterleave("aabcc", "dbbca", "aadbbbaccc"));
		System.out.println(isInterleave("", "", ""));

	}

	/*
	 * Let F(n1, n2, n3) be the possibility of n3 first characters of s3
	 * be an interleaving of first n1 characters of s1 and n2 first characters
	 * of n2.
	 * 
	 * We notice that if n1 + n2 != n3, then F(n1, n2, n3) == false.
     * Let n1 + n2 == n3. In this case let
     * 
     *     F(n1, n2) = F(n1, n2, n1 + n2) = F(n1, n2, n3).
     * 
     * Notice that F(0, 0) = true, and if one of n1 and n2 is positive, then
     * 
     * F(n1, n2) = (n1 > 0 and s3[n3-1] == s1[n1-1] and F(n1-1, n2)) or
     *            (n2 > 0 and s3[n3-1] == s2[n2-1] and F(n1, n2-1)).
     *            
     * The function F(n1, n2) can be computed with Θ(n2) additional memory
     * by keeping only the values of F for the ending of the last row and the
     * beginning of the current row. Overall run time is Θ(n1 * n2).
	 */
	public static boolean isInterleave(String s1, String s2, String s3) {
		int n1 = s1.length();
		int n2 = s2.length();
		int n3 = s3.length();
		
		if (n1 + n2 != n3)
			return false;
		
		// n1 + n2 == n3
		var f = new boolean[n2 + 1];
		// f[j] = F(0, j)
		f[0] = true;
		
		for (int j = 1; j <= n2; j++)
			f[j] = s3.charAt(j-1) == s2.charAt(j-1) && f[j-1];
		
		for (int i = 1; i <= n1; i++) {
			// f[j] = F(n1, j)
			f[0] = s3.charAt(i-1) == s1.charAt(i-1) && f[0];
			
			for (int j = 1; j <= n2; j++)
				f[j] = s3.charAt(i + j - 1) == s1.charAt(i-1) && f[j] ||
				       s3.charAt(i + j - 1) == s2.charAt(j-1) && f[j-1];
		}
		return f[n2];
	}
}
