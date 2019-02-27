package cn.design_pattern.factoryPattern.AbstractFactory;


import cn.design_pattern.factoryPattern.Sender;

public interface Provider {
	public Sender produce();
}
