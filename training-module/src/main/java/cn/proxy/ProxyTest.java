package cn.proxy;

interface Subject {
	void request();
}

class RealSubject implements Subject {
	public void request() {
		System.out.println("RealSubject..");
	}
}

//使用静态代理
class Proxy implements Subject {
	private Subject subject;
	
	public Proxy(Subject subject) {
		this.subject = subject;
	}
	
	public void request() {
		System.out.println("begin");
		subject.request();
		System.out.println("after");
	}
}
public class ProxyTest {
	public static void main(String[] args) {
		RealSubject subject = new RealSubject();
		Proxy p = new Proxy(subject);
		p.request();
	}
}