package designPattern.adapter_pattern;
//the adapter of the class
public class AdaterTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Targetable target = new Adapter();
		target.method1();
		target.method2();
	}

}
interface Targetable{
	public void method1();
	public void method2();
}

class Source{
	public void method1(){
		System.out.println("this is original method!");
	}
}

class Adapter extends Source implements Targetable{
	public void method2(){
		System.out.println("this is the targetable method!");
	}
}