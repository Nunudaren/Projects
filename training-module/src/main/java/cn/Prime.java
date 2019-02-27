package cn;

//��һ������������������
public class Prime {

	public static void main(String[] args) {
		String str1 = "hello";
		String str2 = "he" + new String("llo");
		String s = "o";
		String str3 = "hell" + "o";
		System.out.println(str2 == str1);
		System.out.println(str1 == str3);
		System.out.println();
//		System.out.println("$1");
////		System.out.println(Prime.class);
////		// TODO Auto-generated method stub
//////		demo();
////		int a = 1;
////		a = a + ++a +(a + 1);
//////		a += ++a + (a + 1);
////		System.out.println(a);
	}

	public static void test() {
		int n = 40;
		int i = 2;
		while (true) {
			if (n == i) {
				System.out.println(i);
				break;
			}
			if (n % i == 0) {
				System.out.println(i);
				n = n / i;
			} else {
				i = i + 1;
			}
		}
	}

}
