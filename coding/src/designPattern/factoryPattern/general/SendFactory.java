package designPattern.factoryPattern.general;

import designPattern.factoryPattern.EmsSender;
import designPattern.factoryPattern.MailSender;
import designPattern.factoryPattern.Sender;

//ͨ���֧�ж���������ͬ�Ķ���ʵ��
public class SendFactory {
	public Sender produce(String type){
		if ("mail".equals(type)) {  
            return new MailSender();  
        } else if ("ems".equals(type)) {  
            return new EmsSender();  
        } else {  
            System.out.println("��������ȷ������!");  
            return null;  
        }  
	}
}
