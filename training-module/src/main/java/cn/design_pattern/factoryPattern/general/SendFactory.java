package cn.design_pattern.factoryPattern.general;

import cn.design_pattern.factoryPattern.EmsSender;
import cn.design_pattern.factoryPattern.MailSender;
import cn.design_pattern.factoryPattern.Sender;

/**
 * 通过分支判断来创建不同的对象实例
 */
public class SendFactory {
	public Sender produce(String type){
		if ("mail".equals(type)) {  
            return new MailSender();
        } else if ("ems".equals(type)) {  
            return new EmsSender();
        } else {  
            System.out.println("请输入正确的类型!");
            return null;  
        }  
	}
}
