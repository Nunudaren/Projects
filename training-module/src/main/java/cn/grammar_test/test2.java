package cn.grammar_test;

import org.junit.Test;

public class test2 {

	public static void main(String[] args) {
		final int a = 1;
		new Runnable() {
			public void run() {
				System.out.println("Anonymous Runnable Interface" + a);
			}
		}.run();
	}

	@Test
	public void test() {
		int b = 2;
		final int c = 3;
		new Runnable() {
			public void run() {
//				Cannot refer to a non-final variable be inside an inner class defined in a different method
//				System.out.println("Anonymous Runnable Interface2 " + b);
				System.out.println("Anonymous Runnable Interface2 " + c);
			}
		}.run();

	}

}
