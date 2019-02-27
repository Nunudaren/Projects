package cn.design_pattern.adapter_pattern.interface_adapter;

/**
 * the adapter of the interface
 * 接口的适配器模式：
 * 当不希望实现一个接口中所有的方法时，可以创建一个抽象类Wrapper，实现所有方法，我们写别的类的时候，继承抽象类即可。
 * 这个功能在java8中：接口 default 权限修饰符修饰方法 即可解决的这个问题
 */
public class InterfaceAdapterTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sourceable source1 = new SourceSub1();
		Sourceable source2 = new SourceSub2();
		source1.method1();
		source1.method2();
		
		source2.method1();
		source2.method2();
	}
}