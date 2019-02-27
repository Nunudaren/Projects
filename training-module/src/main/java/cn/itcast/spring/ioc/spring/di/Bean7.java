package cn.itcast.spring.ioc.spring.di;
//注入 
//setter注入
public class Bean7 {
	//1.提供一个私有的属性
	private String msg;
	private int year;
	
	private Bean8 abc;
	
	//2.为其提供标准封装的setter方法
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public void setAbc(Bean8 abc) {
		this.abc = abc;
	}
	//3.配置文件中为其注入数据(简单类型)
	//参看配置文件
	public void fn(){
		System.out.println("bean7 fn method is running..."+msg+","+year);
		abc.bean8Fn();
		//System.out.println(abc);
	}
}
