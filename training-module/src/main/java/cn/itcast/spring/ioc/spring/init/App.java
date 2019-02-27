package cn.itcast.spring.ioc.spring.init;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-demo.xml");
		Bean1 bean1 = (Bean1) ctx.getBean("bean1");
		Bean1 bean2 = (Bean1) ctx.getBean("bean1");
		System.out.println(bean1);
		System.out.println(bean2);
		//Bean2 bean = (Bean2) ctx.getBean("bean2");
		//Bean3 bean = (Bean3) ctx.getBean("bean3");
	}
}
