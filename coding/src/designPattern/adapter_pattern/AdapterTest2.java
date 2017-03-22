package designPattern.adapter_pattern;
// the adapter of the interface
public class AdapterTest2 {

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

interface Sourceable{
	public void method1();
	public void method2();
}

abstract class Wrapper2 implements Sourceable{

	public void method1() {}

	public void method2() {}
	
}

class SourceSub1 extends Wrapper2{
	public void method1(){
		System.out.println("the sourceable interface's first Sub1!");
	}
}

class SourceSub2 extends Wrapper2{
	public void method2(){
		System.out.println("the sourecable interface's second Sub2!");
	}
}