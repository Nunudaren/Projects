package cn.design_pattern.factoryPattern.AbstractFactory;


import cn.design_pattern.factoryPattern.MailSender;
import cn.design_pattern.factoryPattern.Sender;

public class SendMailFactory implements Provider {

	@Override
	public Sender produce() {
		return new MailSender();
	}

}
