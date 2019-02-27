package cn.itcast.spring.ioc.spring.init;

public class Bean3Factory {
	public Bean3 getInst(){
		System.out.println("instance factory init....");
		return new Bean3();
	}
}
