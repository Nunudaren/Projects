package cn.itcast.spring.ioc.spring.team;

public class Bean11 {
	private Bean12 bean12;

	public void setBean12(Bean12 bean12) {
		this.bean12 = bean12;
	}
	public void fn(){
		bean12.fn();
	}
}
