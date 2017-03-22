package designPattern.adapter_pattern;
//the adapter of the object
public class AdpterTest_2 {
	public static void main(String[] args) {
		Targetable target = new Wrapper(new Source());
		target.method1();
		target.method2();
	}
}

class Wrapper implements Targetable{
	private Source source;

	public Wrapper(Source source) {
		super();
		this.source = source;
	}

	@Override
	public void method1() {
		// TODO Auto-generated method stub
		source.method1();
	}

	@Override
	public void method2() {
		// TODO Auto-generated method stub
		System.out.println("this is the targetable method!");
	};
	
}