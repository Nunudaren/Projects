package cn.demo_test;

public class Demo2 {
	public static void main(String[] args) {
		StringBuffer str = new StringBuffer();
		StringBuffer str1 = str.append("We are happy.");
		System.out.println(replaceSpace(str1));
	}

	public static String replaceSpace(StringBuffer str) {
		if (str == null) {
			return null;
		}
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == ' ') {
				str.replace(i, i + 1, "%20");
			}
		}
		String newstr = str.toString();
		return newstr;
	}
}
