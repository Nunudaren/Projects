package cn.itcast.spring.ioc.spring.team;

public class Bean12 {
	private String msg;
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void fn(){
		System.out.println("bean12 fn......"+msg);
	}
}
