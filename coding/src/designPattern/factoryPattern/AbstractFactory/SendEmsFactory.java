package designPattern.factoryPattern.AbstractFactory;

import designPattern.factoryPattern.EmsSender;
import designPattern.factoryPattern.Sender;
//��������ʵ������װ����ͬ�Ĺ������У����ù����඼ʵ��ͳһ�Ľӿڣ�
public class SendEmsFactory implements Provider {

	@Override
	public Sender produce() {
		return new EmsSender();
	}

}
