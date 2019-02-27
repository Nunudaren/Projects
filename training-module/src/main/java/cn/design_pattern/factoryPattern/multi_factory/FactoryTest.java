package cn.design_pattern.factoryPattern.multi_factory;

import cn.design_pattern.factoryPattern.Sender;

public class FactoryTest {
	public static void main(String[] args) {
		SendFactory factory = new SendFactory();
		Sender sender = factory.produceMail();
		sender.send();
	}
}
