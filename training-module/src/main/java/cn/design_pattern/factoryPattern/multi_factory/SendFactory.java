package cn.design_pattern.factoryPattern.multi_factory;

import cn.design_pattern.factoryPattern.EmsSender;
import cn.design_pattern.factoryPattern.MailSender;
import cn.design_pattern.factoryPattern.Sender;

/**
 * 将创建各个类实例对象的方法封装到工厂类中
 */
public class SendFactory {
	public Sender produceMail(){
		return new MailSender();
	}
	public Sender produceEms(){
		return new EmsSender();
	}
}
