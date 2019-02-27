package cn.grammar_test.string;

public class ReverseString {
public static void main(String[] args) {
	String s = "sgwher";
	System.out.println(reverse1(s));
}
public static String reverse(String s){
	int length = s.length();
	String reverse = "";
	for (int i = 0; i < length; i++) {
		reverse = s.charAt(i) + reverse;
	}
	return reverse;
}
public static String reverse1(String s){
	return new StringBuffer(s).reverse().toString();
}
}
