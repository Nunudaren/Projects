package cn.itcast.goods.admin.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class AdminLoginFilter implements Filter {


	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		Object admin = req.getSession().getAttribute("admin");
		if(admin == null) {
			request.setAttribute("msg", "您还没有登录，请先登录！");
			request.getRequestDispatcher("/adminjsps/login.jsp").forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	} 


	@Override
	public void destroy() {
		
	}

	
}
