package cn.itcast.spring.ioc.spring.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("userAction")
@Scope("prototype")
public class UserAction {
	//注解注入属性可以不写setter方法
	//1.该属性自动注入（自动装配）
	@Autowired
	//2.注入简单类型
	@Value("abc")
	private String name;
	
	@Autowired(required=false)
	//3.注入引用类型
	@Qualifier("userService")
	private UserService userService;
	
	public void add(){
		System.out.println("user action..."+name);
		userService.add();
	}
}
