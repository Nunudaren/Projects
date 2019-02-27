package cn.design_pattern.factoryPattern.AbstractFactory;


import cn.design_pattern.factoryPattern.EmsSender;
import cn.design_pattern.factoryPattern.Sender;

/**
 * 将创建类实例对象封装到不同的工厂类中，并让工厂类都实现统一的接口；
 */
public class SendEmsFactory implements Provider {

	@Override
	public Sender produce() {
		return new EmsSender();
	}

}
