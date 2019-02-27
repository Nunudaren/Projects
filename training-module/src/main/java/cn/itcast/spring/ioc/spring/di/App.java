package cn.itcast.spring.ioc.spring.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-demo.xml");
		//Bean6 bean6 = (Bean6) ctx.getBean("bean6");
		//bean6.fn();
		Bean7 bean7 = (Bean7) ctx.getBean("bean7");
		bean7.fn();
		
	}
}
