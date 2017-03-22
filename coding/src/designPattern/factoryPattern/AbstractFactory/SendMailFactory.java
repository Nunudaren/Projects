package designPattern.factoryPattern.AbstractFactory;

import designPattern.factoryPattern.MailSender;
import designPattern.factoryPattern.Sender;

public class SendMailFactory implements Provider {

	@Override
	public Sender produce() {
		return new MailSender();
	}

}
