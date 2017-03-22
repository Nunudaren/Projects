package designPattern.factoryPattern.StaticFactory;

import designPattern.factoryPattern.Sender;

public class FactoryTest {
	public static void main(String[] args) {
		Sender sender = SendFactory.produceMail();
		sender.Send();
	}
}
