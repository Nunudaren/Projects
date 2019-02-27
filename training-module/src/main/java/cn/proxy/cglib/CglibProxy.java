package cn.proxy.cglib;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor {
//	private Object object;
//	
//	public CglibProxy(Object object) {
//		this.object = object;
//	}
	
	public Object CreatCglibProxyObject(Class clazz) {
		//cglib动态字节码技术：生成类（被代理类的子类）
		//1.创建一个可以用于生成动态类的对象，表示的就是那个子类
		Enhancer eh = new Enhancer();
		//2.设置新的类的父类为指定类型
		eh.setSuperclass(clazz);
		//3.设置回调方法
		eh.setCallback(this);
		//4.创建该类的对象
		Object obj = eh.create();
		return obj;
	}

	@Override
	public Object intercept(Object proxy, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {
		System.out.println("---before---");
		Object obj = methodProxy.invokeSuper(proxy, args);//调用父类方法，执行的是子类方法（多态）
		System.out.println("---after---");
		return obj;
	}
	
	public static void main(String[] args) {
		Object obj = new CglibProxy().CreatCglibProxyObject(UserImpl.class);
		UserImpl ui = (UserImpl) obj;
		ui.add();
	}
}
