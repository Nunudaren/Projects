package cn.itcast.spring.ioc.spring.di;
//注入
//构造器注入
public class Bean6 {
	private String msg;
	private int year;
	public Bean6(int year,String msg){
		this.msg = msg;
		this.year = year;
	}
	
	public void fn(){
		System.out.println("bean6 fn method is running...."+msg+","+year);
	}
}
