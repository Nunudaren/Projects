package cn.itcast.spring.ioc.spring.init;

public class Bean2Factory {
	public static Bean2 getInst(){
		System.out.println("bean2 static factory run.....");
		return new Bean2();
	}
}
