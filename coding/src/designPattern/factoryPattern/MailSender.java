package designPattern.factoryPattern;

public class MailSender implements Sender {

	@Override
	public void Send() {
		System.out.println("mail...");
	}

}
