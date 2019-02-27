package cn.proxy.jdk;

//使用动态代理
public class ProxyTest {
	public static void main(String[] args) {
		Service service = new ServiceImpl();
		Handler handler = new Handler(service);
		Service serviceProxy = (Service) handler.getProxy();
		serviceProxy.add();
	}
}
