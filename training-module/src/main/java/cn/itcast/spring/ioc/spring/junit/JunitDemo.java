//package cn.itcast.spring.ioc.spring.junit;
//
//import cn.itcast.spring.ioc.spring.annotation.UserAction;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.demo.context.ContextConfiguration;
//import org.springframework.demo.context.junit4.SpringJUnit4ClassRunner;
//
//import cn.itcast.spring.annotation.UserAction;
////2.使用SpringJunit类加载器(该类加载器是在test包中的)
//@RunWith(SpringJUnit4ClassRunner.class)
////3.指定配置文件
//@ContextConfiguration(locations="classpath:/applicationContext-demo.xml")
//public class JunitDemo {
//	//1.junit整合spring是先将要测试的对象注入到当前类中
//	@Autowired
//	//@Qualifier("userAction")
//	private UserAction userAction;
//	//注解开发时，如果不写@Qualifier默认按照类型匹配
//	//如果使用的是接口，那么将自动找到该接口的实现类（开发中每个接口只有一个实现类）
//	//如果一个接口有多个实现类，报错
//
//
//	@Test
//	public void testAction(){
//		userAction.add();
//	}
//}
//
///*
//XML
//Bean初始化（pass)
//Bean作用于 scope prototype
//Bean生命周期	init-method destory-method(pass)
//Bean的注入	构造器注入(pass)
//(重点)setter注入	property name value ref
//P
//EL
//集合注入（List Properties)
//团队开发	import name
//
//注解
//
//JUnit
//*/
//
//
//
//
//
//
