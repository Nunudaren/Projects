package cn.itcast.spring.ioc.spring.team;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		//ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-bean11.xml","applicationContext-demo.xml");
		//ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"applicationContext-bean11.xml","applicationContext-demo.xml"});
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-bean11.xml");
		Bean11 bean11 = (Bean11) ctx.getBean("bean11");
		bean11.fn();
	}
}
