package cn.itcast.spring.ioc.spring.init;
//构造器初始化Bean
//要求bean对应的类提供无参可访问的构造方法
public class Bean1 {
	public Bean1(){
		System.out.println("Bean1 init....");
	}
}
