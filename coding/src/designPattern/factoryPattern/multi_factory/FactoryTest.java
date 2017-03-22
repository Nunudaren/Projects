package designPattern.factoryPattern.multi_factory;

import designPattern.factoryPattern.Sender;

public class FactoryTest {
	public static void main(String[] args) {
		SendFactory factory = new SendFactory();
		Sender sender = factory.produceMail();
		sender.Send();
	}
}
