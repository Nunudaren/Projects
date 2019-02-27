package cn.arithmetic.leetcode;

public class ZigZagCoversion {
	public static void main(String[] args) {
		String s = "PAYPALISHIRING";
		System.out.println(covert(s, 3));
	}
	public static String covert(String s, int numRows){
		char[] c = s.toCharArray();
		int len = c.length;
		StringBuffer[] sb = new StringBuffer[numRows];
		for (int i = 0; i < numRows; i++) {
			sb[i] = new StringBuffer();
		}
		int i = 0;
		while(i < len){
			for (int j = 0; j < numRows && i < len; j++) {   //vertically down
				sb[j].append(c[i++]);
			}
			for (int j = numRows - 2; j >= 1 && i < len; j--) {  //obliquely up
				sb[j].append(c[i++]);
			}
		}
		for (int j = 1; j < numRows; j++) {
			sb[0].append(sb[j]);
		}
		return sb[0].toString();
	}
}
