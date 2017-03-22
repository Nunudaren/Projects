package string;

public class LCS {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str1 = "cnblogs";
		String str2 = "belong";
		int lcs = lcs(str1,str2);
		int lcws = lcws(str1,str2);
		System.out.println(lcs);
		System.out.println(lcws);
	}

	/**
	 * DP求最长公共子序列（可以不连续）
	 * @param str1
	 * @param str2
	 * @return 
	 */
	public static int lcs(String str1, String str2) {
		int len1 = str1.length();
		int len2 = str2.length();
		int c[][] = new int[len1 + 1][len2 + 1];
		for (int i = 0; i <= len1; i++) {
			for (int j = 0; j <= len2; j++) {
				if (i == 0 || j == 0) {
					c[i][j] = 0;
				}else if(str1.charAt(i - 1) == str2.charAt(j - 1)){
					c[i][j] = c[i-1][j-1] + 1;
				}else{
					c[i][j] = Math.max(c[i - 1][j], c[i][j - 1]);
				}
			}
		}
		return c[len1][len2];
	}
	
	/**
	 * DP求最长公共字串(连续的)
	 */
	public static int lcws(String str1, String str2){
		int len1 = str1.length();
		int len2 = str2.length();
		int result = 0;  //记录最长公共子串长度
		int c[][] = new int[len1+1][len2+1];
		for (int i = 0; i <= len1; i++) {
			for (int j = 0; j <= len2; j++) {
				if(i == 0 || j == 0){
					c[i][j] = 0;
				}else if(str1.charAt(i-1) == str2.charAt(j-1)){
					c[i][j] = c[i-1][j-1] + 1;
					result = Math.max(c[i][j],  result);
				}else {
					c[i][j] = 0;
				}
			}
		}
		return result;
	}

}
