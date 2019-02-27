package cn.itcast.spring.ioc.spring.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
	public static void main(String[] args) {
		//ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-demo.xml");
		//UserAction action1 = (UserAction) ctx.getBean("userAction");
		//UserAction action2 = (UserAction) ctx.getBean("userAction");
		//System.out.println(action1);
		//System.out.println(action2);
		//action.add();
		//System.out.println(ctx.getBean("user"));
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		//将所配置的注解bean告诉我（注册）
		ctx.register(AnnotationConfig.class);
		//最后刷新上下文
		ctx.refresh();
		System.out.println(ctx.getBean("user"));
	}
}
