package cn.arithmetic.leetcode;

import java.util.Scanner;

/**4和7是两个幸运数字，我们定义，十进制表示中，每一位只有4和7两个数的正整数都是幸运数字。
 *前几个幸运数字为：4,7,44,47,74,77,444,447...
 *现在输入一个数字K，输出第K个幸运数。
 **/

public class LucyNumber {
	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		int input = cin.nextInt();
		System.out.println(getLucky1(input));
		System.out.println(getLucky2(input));
	}

	private static String getLucky1(int input) {
		String str = Integer.toBinaryString(input + 1);

		str = str.substring(1);

		str = str.replace('1', '7');
		str = str.replace('0', '4');

		return str;
	}

	private static String getLucky2(int input) {
		int num = input + 1;
		StringBuffer sb = new StringBuffer();

		while (num != 1) {
			sb.append(num % 2 == 1 ? "7" : "4");
			num /= 2;
		}

		return sb.reverse().toString();
	}
}

/**思路是题目给定只能用4和7表示数字，类似于2进制，所以所有思路都往2进制想。
 *第一个方法，有点投机取巧，将n+1，然后转化为2进制，去掉打头的1之后（前面的0默认无视掉），将0替换成4，1替换成7即可。
 *第二个方法类似于完全二叉树找路径。
 *假设有一棵完全二叉树，路径向左代表4，路径向右代表7。默认每个节点的权值等于从根节点到该节点经过的所有的路径（类似于Huffman编码那种形式）
 *第一层设为0或者空，第二层为4，7，第三层为44，47，74，77，第四层为444，447，474，477，744，747，774，777
 *我们会发现权值也是按照从上到下从左到右依次递增的规律排列。
 *那这个问题就类比成了完全二叉树给定节点，找从根节点到该节点的路径了。
 */



