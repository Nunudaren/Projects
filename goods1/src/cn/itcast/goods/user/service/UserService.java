package cn.itcast.goods.user.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;









import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.user.dao.UserDao;
import cn.itcast.goods.user.domain.User;
import cn.itcast.goods.user.service.exception.UserException;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;

/**
 * 用户模块业务层
 * @author qdmmy6
 *
 */
public class UserService {
	private UserDao userDao = new UserDao();
	
	/**
	 * 用户名注册校验
	 * @param loginname
	 * @return
	 */
	public boolean ajaxValidateLoginname(String loginname) {
		try {
			return userDao.ajaxValidateLoginname(loginname);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Email注册校验
	 * @param email
	 * @return
	 */
	public boolean ajaxValidateEmail(String email) {
		try {
			return userDao.ajaxValidateEmail(email);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * 注册功能
	 * @param user
	 */
	public void regist(User user) {
		/*
		 * 1.补齐其他数据（id、状态、激活码）
		 */
		user.setUid(CommonUtils.uuid());
		user.setStatus(false);
		user.setActivationCode(CommonUtils.uuid() + CommonUtils.uuid());
		
		 // 2.userDao来把数据保存到数据库中
		try {
			userDao.add(user);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		//3. 发邮件
			//获取属性对象，把配置文件内容加载到prop中
		Properties prop = new Properties();
		try {
			prop.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties")); //注意先要获取ClassLoader
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
			//登录邮件服务器，得到session
		String host = prop.getProperty("host");
		String loginname = prop.getProperty("username");
		String loginpass = prop.getProperty("password");
			//创建Session对象
		Session session = MailUtils.createSession(host, loginname, loginpass);
			//创建Mail对象
		String from = prop.getProperty("from");
		String to = user.getEmail();
		String subject = prop.getProperty("subject");
		String content = MessageFormat.format(prop.getProperty("content"), user.getActivationCode());
		Mail mail = new Mail(from, to, subject, content);
		
			//发邮件
		
		try {
			MailUtils.send(session, mail);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 激活功能
	 * @param activationCode
	 * @throws UserException
	 */
	public void activation(String activationCode) throws UserException {
		/*
		 * 1. 通过激活码查询用户
		 * 2. 如果User为null，说明是无效激活码，抛出异常，给出异常信息（无效激活码）
		 * 3. 查看用户状态是否为true，如果为true，抛出异常，给出异常信息（请不要二次激活）
		 * 4. 修改用户状态为true
		 */
			try {
				User user = userDao.findByActivationCode(activationCode);
				if(user == null) throw new UserException("您的激活码无效，请重新激活！");
				if(user.isStatus()) throw new UserException("该用户已经激活，请不要重复操作！");
				userDao.updateStatus(user.getUid(), true);//修改用户状态
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
	}
	
	/**
	 * 登录功能
	 * @param user
	 * @return
	 */
	public User login(User user) {
		try {
			return userDao.findByLoginnameAndLoginpass(user.getLoginname(), user.getLoginpass());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 修改密码
	 * @param uid
	 * @param newPassword
	 * @param oldPassword
	 * @throws UserException
	 */
	public void updatePassword(String uid, String oldPassword, String newPassword) throws UserException {
		try {
			/*
			 * 1.校验老密码
			 */
			boolean bl = userDao.findByUidAndPassword(uid, oldPassword);
			if(!bl) {
				throw new UserException("老密码输入错误！");
			}
			//2.修改密码
			userDao.updatePassword(uid, newPassword);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
