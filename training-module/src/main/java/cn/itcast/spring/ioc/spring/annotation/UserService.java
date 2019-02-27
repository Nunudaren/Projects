package cn.itcast.spring.ioc.spring.annotation;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {
	/*
	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;
	*/
	
	@Resource(name="userDao")
	private UserDao userDao;
	public void add(){
		System.out.println("user service ....");
		userDao.add();
	}
}
