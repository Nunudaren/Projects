package designPattern.factoryPattern.AbstractFactory;

import designPattern.factoryPattern.Sender;

/**
 * ��ʵ���ģʽ�ĺô����ǣ����������������һ�����ܣ�
����ʱ��Ϣ����ֻ����һ��ʵ���࣬ʵ��Sender�ӿڣ�
ͬʱ��һ�������࣬ʵ��Provider�ӿڣ���OK�ˣ�����ȥ�Ķ��ֳɵĴ��롣
����������չ�ԽϺã�*/

public class FactoryTest {

	public static void main(String[] args) {
		Provider provider = new SendMailFactory();
		Sender sender = provider.produce();
		sender.Send();
	}

}
