package cn.design_pattern.factoryPattern.StaticFactory;

import cn.design_pattern.factoryPattern.Sender;

public class FactoryTest {
	public static void main(String[] args) {
		Sender sender = SendFactory.produceMail();
		sender.send();
	}
}
