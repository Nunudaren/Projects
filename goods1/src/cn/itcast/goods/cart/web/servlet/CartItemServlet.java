package cn.itcast.goods.cart.web.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.book.domain.Book;
import cn.itcast.goods.cart.domain.CartItem;
import cn.itcast.goods.cart.service.CartItemService;
import cn.itcast.goods.user.domain.User;
import cn.itcast.servlet.BaseServlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class CartItemServlet
 */
@SuppressWarnings("serial")
//@WebServlet("/CartItemServlet")
public class CartItemServlet extends BaseServlet {
	private CartItemService cartItemService = new CartItemService();
	
	/**
	 * 按当前登录用户的uid查询购物车条目
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String myCart(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1.通过session得到user,再通过user得到uid
		 */
		User user = (User) req.getSession().getAttribute("sessionUser");
		String uid = user.getUid();
		/*
		 * 2.通过service层得到当前用户的所有购物车条目
		 */
		List<CartItem> cartItemList = cartItemService.myCart(uid);
		/*
		 * 3.保存起来，转发到/cart/list.jsp
		 */
		req.setAttribute("cartItemList", cartItemList);
		return "f:/jsps/cart/list.jsp";
	}

	
	/**
	 * 添加购物车条目
	 * @param req
	 * @param resp
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String addCartItem(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		/*
		 * 1.封装表单数据：bid,quantity到CartItem中
		 */
		Map map = req.getParameterMap();
		CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
		
		Book book = CommonUtils.toBean(map, Book.class);
		User user = (User) req.getSession().getAttribute("sessionUser");
		cartItem.setBook(book);
		cartItem.setUser(user);
		
		/*
		 * 2. 调用service层完成条目的添加
		 */
		cartItemService.addCartItem(cartItem);
		/*
		 * 3.查询出当前用户的所有条目，转发到list.jsp显示
		 */
		return myCart(req, resp);
	}
	
	/**
	 * 批量删除
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String batchDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1.获取cartItemIds参数
		 * 2.调用service方法完成工作
		 * 3.返回list.jsp
		 */
		String cartItemIds = req.getParameter("cartItemIds");
		cartItemService.batchDelete(cartItemIds);
		return myCart(req, resp);
	}
	
	/**
	 * '+'/'-'号修改quantity
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String updateQuantity(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String cartItemId = req.getParameter("cartItemId");
		int quantity = Integer.parseInt(req.getParameter("quantity"));
		CartItem cartItem = cartItemService.updateQuantity(cartItemId, quantity);
		
		//给页面返回一个{key:value}格式的字符串。javascript对当成一个json对象来用
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"quantity\"").append(":").append(cartItem.getQuantity());
		sb.append(",");
		sb.append("\"subtotal\"").append(":").append(cartItem.getSubtotal());
		sb.append("}");
		System.out.println(sb);
		//响应客户端页面，页面通过$.ajax来接收result
		resp.getWriter().print(sb);
		return null;
	}
	
	/**
	 * 结算页面：加载购物车中选中要结算的图书条目
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String loadCartItems(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1.获取cartItemIds参数
		 */
		String cartItemIds = req.getParameter("cartItemIds");
		double total = Double.parseDouble(req.getParameter("total"));
		/*
		 * 2.通过service层来得到List<CartItem>
		 */
		List<CartItem> cartItemList = cartItemService.loadCartItems(cartItemIds);
		/*
		 * 3.保存，然后转发到/cart/showitem.jsp
		 */
		req.setAttribute("cartItemList", cartItemList);
		req.setAttribute("total", total);
		
		//二次修改，保存cartItemIds到showitem.jsp
		req.setAttribute("cartItemIds", cartItemIds);
		return "f:/jsps/cart/showitem.jsp";
	}
	
	
	
}
