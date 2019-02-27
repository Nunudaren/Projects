package cn.design_pattern.adapter_pattern.class_adapter;

import cn.design_pattern.adapter_pattern.Targetable;

/**
 * the adapter of the class
 * 类的适配器模式：
 * 当希望将一个类转换成满足另一个新接口的类时，可以使用类的适配器模式，创建一个新类，继承原有的类，实现新的接口即可
 * 这样就可以在source的基础上扩展，从而达到 target 的要求
 */
public class ClassAdaterTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Targetable target = new ClassAdapter();
		target.method1();
		target.method2();
	}

}