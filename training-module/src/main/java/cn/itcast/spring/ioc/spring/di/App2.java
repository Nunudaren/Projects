package cn.itcast.spring.ioc.spring.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//验证P命名空间
public class App2 {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-demo.xml");
		Bean7 bean7 = (Bean7) ctx.getBean("bean7");
		bean7.fn();
	}
}
