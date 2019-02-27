package cn.design_pattern.adapter_pattern.object_adapter;


import cn.design_pattern.adapter_pattern.Source;
import cn.design_pattern.adapter_pattern.Targetable;

/**
 * the adapter of the object
 * 对象的适配器模式：
 * 当希望将一个对象转换成满足另一个新接口的对象时，可以创建一个Wrapper类，持有原类的一个实例，
 * 在Wrapper类的方法中，调用实例的方法就行。
 */
public class ObjectAdpterTest {
	public static void main(String[] args) {
		Targetable target = new Wrapper(new Source()); // 构造器注入
		target.method1();
		target.method2();
	}
}