package cn.grammar_test;

import org.junit.Test;

public class Test1 {
	@Test
	public void test() {
		final int number = 123;
		final int number2 = 111;
		
		MyInterface myInterface = new MyInterface() {

			@Override
			public void doSomething() {
				//使用匿名内部类是无法访问外部变量的（1.8之后就可以了）
				//要访问必须改成常量：final修饰的，但是不能修改外部变量的值，否则依然报错
				//number++: error: Local variable number defined in an enclosing scope must be final or effectively final
				System.out.println(number);
				System.out.println(number2);
			}
		};
		myInterface.doSomething();
		
		System.out.println(number);
		System.out.println(number2);
	}
}
interface MyInterface {
	void doSomething();
}
