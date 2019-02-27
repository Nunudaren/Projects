package cn;

import org.junit.Test;

import java.util.List;

public class IsVariantWord {
	
	public void test1() {
		char[] ch = {'1','c','0','a'};
		int a = ch[0];//类型强转
		int b = ch[1];
		int c = ch[2];
		int d = ch[3];
		System.out.println(a + " " + b + " " + c + " " + d);
	}
	
	@Test
	public void test() {

		Integer i = 1123;
		Integer a = i / 100;
		System.out.println(a);
		test1();
		String str1 = "1235";
		String str2 = "2315";
		boolean bl = isVariantWord(str1, str2);
		System.out.println(bl);
	}
	
	public boolean isVariantWord(String str1, String str2) {
		if(str1 == null || str2 == null || str1.length() != str2.length()) {
			return false;
		}
		char[] chas1 = str1.toCharArray();
		char[] chas2 = str2.toCharArray();
		int[] map = new int[256];
		for(int i = 0; i < chas1.length; i++) {
			map[chas1[i]]++;    //chas1[i],将char类型自动强转成了int类型
		}
		for(int i = 0; i < chas2.length; i++) {
			if(map[chas2[i]]-- == 0) {  //执行优先级： 先“判断==”后“--”；若为前缀的“--”，则顺序相反；
				return false;
			}
		}
		return true;
	}
}
