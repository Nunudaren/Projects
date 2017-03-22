package designPattern.factoryPattern.StaticFactory;

import designPattern.factoryPattern.EmsSender;
import designPattern.factoryPattern.MailSender;
import designPattern.factoryPattern.Sender;
//������������ʵ�����ķ�����װ���������еķ����У����þ�̬������װ
public class SendFactory {
	public static Sender produceMail(){
		return new MailSender();
	}
	public static Sender produceEms(){
		return new EmsSender();
	}
}
