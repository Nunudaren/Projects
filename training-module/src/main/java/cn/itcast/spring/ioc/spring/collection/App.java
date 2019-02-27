package cn.itcast.spring.ioc.spring.collection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-demo.xml");
		Bean9 bean9 = (Bean9) ctx.getBean("bean9");
		bean9.fn();
	}
}
