package cn.itcast.spring.ioc.spring.annotation;

import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDao {
	public void add(){
		System.out.println("user dao ....");
	}
}
