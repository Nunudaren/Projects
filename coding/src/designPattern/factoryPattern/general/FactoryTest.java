package designPattern.factoryPattern.general;

import designPattern.factoryPattern.Sender;

public class FactoryTest {

	public static void main(String[] args) {
		SendFactory factory = new SendFactory();
		Sender sender = factory.produce("ems");
		sender.Send();
	}

}
