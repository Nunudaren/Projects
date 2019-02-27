package cn.design_pattern.factoryPattern;

public class MailSender implements Sender {

	@Override
	public void send() {
		System.out.println("mail...");
	}

}
