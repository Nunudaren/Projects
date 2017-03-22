package cn.itcast.goods.user.web.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.user.domain.User;
import cn.itcast.goods.user.service.UserService;
import cn.itcast.goods.user.service.exception.UserException;
import cn.itcast.servlet.BaseServlet;

/**
 * 用户模块WEB层
 * @author qdmmy6
 *
 */
@SuppressWarnings("serial")
public class UserServlet extends BaseServlet {
	private UserService userService = new UserService();
	
	/**
	 * ajax用户名是否注册校验
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxValidateLoginname(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1.获取用户名
		 */
		String loginname = req.getParameter("loginname");
		/*
		 * 2. 通过Service层获取校验结果
		 */
		boolean b = userService.ajaxValidateLoginname(loginname);
		/*
		 * 3.发给客户端
		 */
		resp.getWriter().print(b);
		return null;
	}
	
	/**
	 * ajax Email是否注册校验
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxValidateEmail(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1.获取用户名
		 */
		String email = req.getParameter("email");
		/*
		 * 2. 通过Service层获取校验结果
		 */
		boolean b = userService.ajaxValidateEmail(email);
		/*
		 * 3.发给客户端
		 */
		resp.getWriter().print(b);
		return null;
	}
	
	/**
	 * 验证码校验
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxValidateVerifyCode(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1.获取输入的校验码
		 */
		String verifyCode = req.getParameter("verifyCode");
		/*
		 * 2.获取图片中的验证码
		 */
		String vCode = (String) req.getSession().getAttribute("vCode");
		/*
		 * 3.进行验证码的对比，获取结果
		 */
		boolean b = verifyCode.equalsIgnoreCase(vCode);
		
		resp.getWriter().print(b);
		return null;
	}
	
	/**
	 * 注册功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String regist(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1.封装表单数据到User对象中
		 */
		User userForm = CommonUtils.toBean(req.getParameterMap(), User.class);
		/*
		 * 2.校验参数
		 */
		Map<String,String> errors = validateRegist(userForm, req.getSession());
		if(errors.size() > 0) {
			//保存用户数据，用于页面用户数据的回显，
			//为了防止注册提交表单失败之后刷新页面之后，用户已经填写的数据丢失！
			req.setAttribute("userForm", userForm);
			req.setAttribute("errors", errors);
			return "f:/jsps/user/regist.jsp";
		}
		/*
		 * 3.把表单数据给Service完成业务
		 */
		userService.regist(userForm);
		/*
		 * 4.保存成功信息，转发到msg.jsp显示！
		 */
		req.setAttribute("code", "success");
		req.setAttribute("msg","注册成功，请到邮箱进行激活！");
		return "f:/jsps/msg.jsp";
	}
	
	/**
	 * 注册校验
	 * 对表单的字段进行逐个校验，如果有错误，使用当前字段名称为key，错误信息为value，保存到map中
	 * 返回map
	 * @param formUser
	 * @param session
	 * @return
	 */
	private Map<String,String> validateRegist(User userForm, HttpSession session) {
		Map<String,String> errors = new HashMap<String,String>();
		
		//校验登陆名
		String loginname = userForm.getLoginname();
		if(loginname == null || loginname.trim().isEmpty()){
			errors.put("loginname", "用户名不能为空！");
		} else if(loginname.length() < 3 || loginname.length() > 20) {
			errors.put("loginname", "用户名长度必须在3~20之间！");
		} else if(!userService.ajaxValidateLoginname(loginname)) {
			errors.put("loginname", "用户名已被注册！");
		}
		//校验登录密码
		String loginpass = userForm.getLoginpass();
		if(loginpass == null || loginpass.trim().isEmpty()){
			errors.put("loginpass", "密码不能为空！");
		} else if(loginpass.length() < 3 || loginpass.length() > 20) {
			errors.put("loginpass", "用户名长度必须在3~20之间！");
		}
		//校验确认密码
		String reloginpass = userForm.getReloginpass();
		if(reloginpass == null || reloginpass.trim().isEmpty()){
			errors.put("reloginpass", "确认密码不能为空！");
		} else if(!reloginpass.equals(loginpass)) {
			errors.put("reloginpass", "两次密码输入不一致！");
		}
		//校验Email
		String email = userForm.getEmail();
		if(email == null || email.trim().isEmpty()){
			errors.put("email", "Email不能为空！");
		} else if(!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
			errors.put("email", "邮箱格式不正确！");
		} else if(!userService.ajaxValidateEmail(email)) {
			errors.put("email", "该Email已被注册！");
		}
		//验证码校验
		String verifyCode = userForm.getVerifyCode();
		String vCode = (String) session.getAttribute("vCode");
		if(verifyCode  == null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCode", "验证码不能为空！");
		} else if(!verifyCode.equalsIgnoreCase(vCode)) {
			errors.put("verifyCode", "验证码输入错误！");
		}
		
		return errors;
	}
	
	/**
	 * 激活功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String activation(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 获取参数激活码
		 * 2. 用激活码调用service方法完成激活
		 *   > service方法有可能抛出异常, 把异常信息拿来，保存到request中，转发到msg.jsp显示
		 * 3. 保存成功信息到request，转发到msg.jsp显示。
		 */
		String activationCode = req.getParameter("activationCode");
		try {
			userService.activation(activationCode);
			req.setAttribute("msg", "激活成功，请登录！");
			req.setAttribute("code", "success");
		} catch (UserException e) {
			req.setAttribute("msg", e.getMessage());
			req.setAttribute("code", "error");
		}
		
		System.out.println("activation()...");
		return "f:/jsps/msg.jsp";
	}
	
	/**
	 * 登录功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String login(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
		/*
		 * 1.封装表单数据到User
		 * 2.校验表单数据
		 * 3.使用service查询，得到User
		 * 4.查看User是否存在：
		 * 	①如果不存在：
		 * 		*保存错误信息：用户名或密码错误
		 * 		*保存用户数据：为了回显
		 * 		*转发到login.jsp
		 * 	②如果存在，查看状态，如果状态为false:
		 * 		*保存错误信息：您没有激活
		 * 		*保存表单数据：为了回显
		 * 		*转发到login.jsp
		 * 5.登录成功：
		 * 		*保存当前查询出的user到session中
		 * 		*保存当前用户的名称到cookie中，注意中文需要编码处理。
		 */
		
		//1.封装表单数据到user
		User userForm = CommonUtils.toBean(req.getParameterMap(), User.class);
		//2.校验表单数据
		Map<String,String> errors = validateLogin(userForm,req.getSession());
		if(errors.size() > 0) {
			req.setAttribute("userForm", userForm);
			req.setAttribute("errors", errors);
			return "f:/jsps/user/login.jsp";
		}
		
		//3.调用userService#login()方法
		User user = userService.login(userForm);
		//4.开始判断
		if(user == null) {
			req.setAttribute("msg", "用户名或密码错误！");
			req.setAttribute("user",userForm);
			return "f:/jsps/user/login.jsp";
		} else {
			if(!user.isStatus()){
				req.setAttribute("msg", "该用户还未激活！");
				req.setAttribute("user", userForm);
				return "f:/jsps/user/login.jsp";
			} else{
				//保存用户到Session中
				req.getSession().setAttribute("sessionUser", user);
				//获取用户名保存到cookie中
				String loginname = user.getLoginname();
				//先处理编码的问题
				loginname = URLEncoder.encode(loginname, "utf-8");
				Cookie cookie = new Cookie("loginname",loginname);
				//设置cookie在客户端保存的时间(10天)
				cookie.setMaxAge(60 * 60 * 24 * 10);
				resp.addCookie(cookie);
				return "r:/index.jsp";//重定向到主页
			}
		}
	}
	
	/**
	 * 登录校验方法
	 * @param userForm
	 * @param session
	 * @return
	 */
	/*
	 * 登录校验方法，内容等你自己来完成
	 */
	private Map<String,String> validateLogin(User userForm, HttpSession session) {
		Map<String,String> errors = new HashMap<String,String>();
		
		//验证码校验
		String verifyCode = userForm.getVerifyCode();
		String vCode = (String) session.getAttribute("vCode");
		if(verifyCode  == null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCode", "验证码不能为空！");
		} else if(!verifyCode.equalsIgnoreCase(vCode)) {
			errors.put("verifyCode", "验证码输入错误！");
		}
		
		return errors;
	}
	
	/**
	 * 修改密码
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String updatePassword (HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1.封装表单数据到User中
		 * 2.从Session中获取uid
		 * 3.使用uid和用户填写表单中的oldPassword和newPassword来调用service方法
		 * 		*如果出现异常，保存异常信息到request中，转发到pwd.jsp
		 * 4.保存成功信息到request中
		 * 5.转发到msg.jsp
		 */
		User userForm = CommonUtils.toBean(req.getParameterMap(), User.class);
		User user = (User)req.getSession().getAttribute("sessionUser");
		//如果用户没有登录，返回到登录页面，显示错误信息！
		if(user == null) {
			req.setAttribute("msg","您还没有登录！");
			return "f:/jsps/user/login.jsp";
		}
		//注意这里的user和userForm的区别
		try {
			userService.updatePassword(user.getUid(), userForm.getLoginpass(), userForm.getNewloginpass());
			req.setAttribute("msg", "修改密码成功！");
			req.setAttribute("code", "success");
			return "f:/jsps/msg.jsp";
		} catch (UserException e) {
			req.setAttribute("msg", e.getMessage());//保存异常信息到request
			req.setAttribute("user", userForm);//为了用户在表单中填写的信息的回显
			return "f:/jsps/user/pwd.jsp";
		}
	}
	
	/**
	 * 退出功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String quit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getSession().invalidate(); //销毁session
		return "r:/jsps/user/login.jsp"; //重定向到login.jsp
	}
	
	
}
