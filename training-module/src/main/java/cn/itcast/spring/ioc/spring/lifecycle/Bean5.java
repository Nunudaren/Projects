package cn.itcast.spring.ioc.spring.lifecycle;
//Bean的生命周期
//init初始化方法运行在构造方法之后
public class Bean5 {
	public Bean5(){
		System.out.println("bean5 constructor init.....");
	}
	
	public void init(){
		System.out.println("init...");
	}
	
	public void destory(){
		System.out.println("destory....");
	}
}
