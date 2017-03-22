package designPattern.factoryPattern.multi_factory;

import designPattern.factoryPattern.EmsSender;
import designPattern.factoryPattern.MailSender;
import designPattern.factoryPattern.Sender;
//������������ʵ�����ķ�����װ����������
public class SendFactory {
	public Sender produceMail(){
		return new MailSender();
	}
	public Sender produceEms(){
		return new EmsSender();
	}
}
