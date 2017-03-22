package cn.itcast.jd;

import java.util.Scanner;

/**4��7�������������֣����Ƕ��壬ʮ���Ʊ�ʾ�У�ÿһλֻ��4��7�������������������������֡�
*ǰ������������Ϊ��4,7,44,47,74,77,444,447... 
*��������һ������K�������K����������
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

/**˼·����Ŀ����ֻ����4��7��ʾ���֣�������2���ƣ���������˼·����2�����롣
*��һ���������е�Ͷ��ȡ�ɣ���n+1��Ȼ��ת��Ϊ2���ƣ�ȥ����ͷ��1֮��ǰ���0Ĭ�����ӵ�������0�滻��4��1�滻��7���ɡ�
*�ڶ���������������ȫ��������·����
*������һ����ȫ��������·���������4��·�����Ҵ���7��Ĭ��ÿ���ڵ��Ȩֵ���ڴӸ��ڵ㵽�ýڵ㾭�������е�·����������Huffman����������ʽ��
*��һ����Ϊ0���߿գ��ڶ���Ϊ4��7��������Ϊ44��47��74��77�����Ĳ�Ϊ444��447��474��477��744��747��774��777
*���ǻᷢ��ȨֵҲ�ǰ��մ��ϵ��´��������ε����Ĺ������С�
*������������ȳ�����ȫ�����������ڵ㣬�ҴӸ��ڵ㵽�ýڵ��·���ˡ�
*/

