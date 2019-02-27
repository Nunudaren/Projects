package cn.itcast.spring.ioc.spring.lifecycle;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class App {
	public static void main(String[] args) {
		//Spring容器加载
		//创建SpringBean的操作，Bean对象创建
		//Spring IoC容器关闭,释放Bean对象，调用销毁方法
		//JVM退出
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-demo.xml");
		Bean5 bean50 = (Bean5) ctx.getBean("bean5");
		Bean5 bean51 = (Bean5) ctx.getBean("bean5");
		Bean5 bean52 = (Bean5) ctx.getBean("bean5");
		Bean5 bean53 = (Bean5) ctx.getBean("bean5");
		//关闭IoC容器
		ctx.close();
		
		
	}
}
