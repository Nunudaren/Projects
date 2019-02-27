package cn.design_pattern.factoryPattern.StaticFactory;

import cn.design_pattern.factoryPattern.EmsSender;
import cn.design_pattern.factoryPattern.MailSender;
import cn.design_pattern.factoryPattern.Sender;

/**
 * 将创建各个类实例对象的方法封装到工厂类中的方法中，并用静态方法封装
 */
public class SendFactory {
	public static Sender produceMail(){
		return new MailSender();
	}
	public static Sender produceEms(){
		return new EmsSender();
	}
}
