package cn.itcast.spring.ioc.spring.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App3 {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-demo.xml");
		Bean7 bean7 = (Bean7) ctx.getBean("bean7");
		bean7.fn();
	}
}
