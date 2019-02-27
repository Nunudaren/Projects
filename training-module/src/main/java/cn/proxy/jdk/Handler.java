package cn.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Test;

public class Handler implements InvocationHandler{
	private Object target;
	
	public Handler(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("---before---");
		Object result = method.invoke(target, args);
		System.out.println("---after---");
		return result;
	}
	
	//生成代理对象
	public Object getProxy() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Class<?>[] interfaces = target.getClass().getInterfaces();
		return Proxy.newProxyInstance(loader, interfaces, this);
	}
	
}
