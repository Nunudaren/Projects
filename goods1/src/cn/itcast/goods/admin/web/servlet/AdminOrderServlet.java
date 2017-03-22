package cn.itcast.goods.admin.web.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.goods.order.domain.Order;
import cn.itcast.goods.order.service.OrderService;
import cn.itcast.goods.pager.PageBean;
import cn.itcast.servlet.BaseServlet;

public class AdminOrderServlet extends BaseServlet {
	private OrderService orderService = new OrderService();
	
	/**
	 * 获取当前页码
	 * @param req
	 * @return
	 */
	private int getCurrPage(HttpServletRequest req) {
		int currPage = 1;
		String param = req.getParameter("currPage");//从页面获取请求参数
		if(param != null && !param.trim().isEmpty()) {
			try {
				currPage = Integer.parseInt(param);
			} catch (RuntimeException e) {}
		}
		return currPage;
	}
	
	/**
	 * 获取url并截取，页面中的分页导航中需要使用它作为超链接的目标，目的是为了当前保存查询条件
	 * http://localhost:8080/goods1/BookServlet?method=findByCategory&cid=xx&currPage=3
	 * req.getRequestURI():'/goods1/BookServlet'
	 * req.getQueryString():'method=findByCategory&cid=xx&currPage=3'
	 * @param req
	 * @return
	 */
	private String getUrl(HttpServletRequest req) {
		String url = req.getRequestURI() + "?" + req.getQueryString();
		/*
		 * 如果url中存在pc参数，截取掉，如果不存在那就不用截取。
		 */
		int index = url.lastIndexOf("&currPage=");
		if(index != -1){
			url = url.substring(0, index);
		}
		return url;
	}
	
	/**
	 * 查询所有订单
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1.得到currPage:
		 * 		*如果页面传递参数，使用页面的currPage
		 * 		*如果没有传，默认currPage = 1;
		 */
		int currPage = getCurrPage(req);
		/*
		 * 2.得到url：...
		 */
		String url = getUrl(req);
		/*
		 * 4.使用currPage和cid调用service层的方法得到PageBean
		 */
		PageBean<Order> pageBean = orderService.findAll(currPage);
		/*
		 * 5.给PageBean设置url,保存PageBean,转发到/jsps/book/list.jsp
		 */
		pageBean.setUrl(url);
		req.setAttribute("pb", pageBean);//将pageBean返回给页面
		return "f:/adminjsps/admin/order/list.jsp";
	}
	
	
	public String findByStatus(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1.得到currPage:
		 * 		*如果页面传递参数，使用页面的currPage
		 * 		*如果没有传，默认currPage = 1;
		 */
		int currPage = getCurrPage(req);
		/*
		 * 2.得到url：...
		 */
		String url = getUrl(req);
		/*
		 * 3.获取链接参数：status
		 */
		int status = Integer.parseInt(req.getParameter("status"));
		/*
		 * 4.使用currPage和cid调用service层的方法得到PageBean
		 */
		PageBean<Order> pageBean = orderService.findByStatus(status, currPage);
		/*
		 * 5.给PageBean设置url,保存PageBean,转发到/jsps/book/list.jsp
		 */
		pageBean.setUrl(url);
		req.setAttribute("pb", pageBean);//将pageBean返回给页面
		return "f:/adminjsps/admin/order/list.jsp";
	}
	
	/**
	 * 查看订单详细信息
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String oid = req.getParameter("oid");
		Order order = orderService.load(oid);
		req.setAttribute("order", order);
		
		String btn = req.getParameter("btn");
		//保存list.jsp上面的超链接的操作,可以知道用户点击了哪个超链接来访问的本方法
		
		req.setAttribute("btn", btn);
		return "f:/adminjsps/admin/order/desc.jsp";
	}
	
	/**
	 * 取消订单
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String cancel(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String oid = req.getParameter("oid");
		/*
		 * 校验订单状态
		 */
		int status = orderService.findStatus(oid);
		if(status != 1) {
			req.setAttribute("msg", "订单状态不对，不能取消订单！");
			return "f:/adminjsps/msg.jsp";
		}
		orderService.updateStatus(oid, 5);//设置状态为取消！
		req.setAttribute("msg", "您的订单已取消！");
		return "f:/adminjsps/msg.jsp";
	}
	
	/**
	 * 发货
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deliver(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String oid = req.getParameter("oid");
		/*
		 * 校验订单状态
		 */
		int status = orderService.findStatus(oid);
		if(status != 2) {
			req.setAttribute("msg", "订单状态不对，不能发货！");
			return "f:/adminjsps/msg.jsp";
		}
		orderService.updateStatus(oid, 3);//设置状态为发货！
		req.setAttribute("msg", "您的订单已发货！请查看订单，及时确认！");
		return "f:/adminjsps/msg.jsp";
	}
	
}
