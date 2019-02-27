package cn.itcast.spring.ioc;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class UserService {
	public static void main(String[] args) {
		//早期写法
		/*
		UserDaoImpl userDao = new UserDaoImpl();
		userDao.add();
		*/
		
		//Spring使用方法
		
		//1.获取Spring的通用工厂
		//ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-demo.xml");
		//2.获取对应的资源（bean）
		//UserDaoImpl userDao = (UserDaoImpl) ctx.getBean("userDao");
		//userDao.add();
		
		
		/*
		//Spring早期格式兼容格式
		ApplicationContext ctx = new FileSystemXmlApplicationContext("src/applicationContext-demo.xml");
		UserDaoImpl userDao = (UserDaoImpl) ctx.getBean("userDao");
		userDao.add();
		*/
		
		//BeanFactory接口
		Resource resource = new ClassPathResource("applicationContext-demo.xml");
		BeanFactory bf = new XmlBeanFactory(resource);
		//UserDaoImpl userDao = (UserDaoImpl) bf.getBean("userDao");
		//userDao.add();
		
	}
}




