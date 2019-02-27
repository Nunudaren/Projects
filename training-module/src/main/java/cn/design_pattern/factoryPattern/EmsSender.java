package cn.design_pattern.factoryPattern;

public class EmsSender implements Sender {
	@Override
	public void send() {
		System.out.println("ems...");
	}

}
