package cn.itcast.spring.ioc.spring.scope;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class App {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-demo.xml");
		Bean4 bean41 = (Bean4) ctx.getBean("bean4");
		Bean4 bean42 = (Bean4) ctx.getBean("bean4");
		System.out.println(bean41);
		System.out.println(bean42);
	}
}
