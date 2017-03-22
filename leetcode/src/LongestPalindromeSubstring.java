public class LongestPalindromeSubstring {
	public static void main(String[] args) {
		LongestPalindromeSubstring lps = new LongestPalindromeSubstring();
		String s = "pwwkew";
		System.out.println(lps.longestPalindrome(s));
		System.out.println(LongestPalindromeSubstring.longestPalindrome02(s));
	}
	private int lo, maxLen;

	public String longestPalindrome(String s) {
		int len = s.length();
		if (len < 2)
			return s;

		for (int i = 0; i < len - 1; i++) {
			extendPalindrome(s, i, i); // assume odd length, try to extend
										// Palindrome as possible
			extendPalindrome(s, i, i + 1); // assume even length.
		}
		return s.substring(lo, lo + maxLen);
	}

	private void extendPalindrome(String s, int j, int k) {
		while (j >= 0 && k < s.length() && s.charAt(j) == s.charAt(k)) {
			j--;
			k++;
		}
		if (maxLen < k - j - 1) {
			lo = j + 1;
			maxLen = k - j - 1;
		}
	}
	public static String longestPalindrome02(String s) {
		  int n = s.length();
		  String res = null;
		    
		  boolean[][] dp = new boolean[n][n];
		    
		  for (int i = n - 1; i >= 0; i--) {
		    for (int j = i; j < n; j++) {
		      dp[i][j] = s.charAt(i) == s.charAt(j) && (j - i < 3 || dp[i + 1][j - 1]);
		            
		      if (dp[i][j] && (res == null || j - i + 1 > res.length())) {
		        res = s.substring(i, j + 1);
		      }
		    }
		  }
		    
		  return res;
		}
}
