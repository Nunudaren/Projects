package cn.itcast.goods.book.web.servlet;

import java.io.IOException;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.book.domain.Book;
import cn.itcast.goods.book.service.BookService;
import cn.itcast.goods.pager.PageBean;
import cn.itcast.servlet.BaseServlet;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BookServlet
 */
//@WebServlet("/BookServlet")
@SuppressWarnings("serial")
public class BookServlet extends BaseServlet {      
	private BookService bookService = new BookService();
	
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
	 * 按分类查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByCategory(HttpServletRequest req, HttpServletResponse resp)
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
		 * 3.获取查询条件，本方法就是cid，即分类 
		 */
		String cid = req.getParameter("cid");
		/*
		 * 4.使用currPage和cid调用service层的方法得到PageBean
		 */
		PageBean<Book> pageBean = bookService.findByCategory(cid, currPage);
		/*
		 * 5.给PageBean设置url,保存PageBean,转发到/jsps/book/list.jsp
		 */
		pageBean.setUrl(url);
		req.setAttribute("pb", pageBean);//将pageBean返回给页面
		return "f:/jsps/book/list.jsp";
	}
	
	public String findByAuthor(HttpServletRequest req, HttpServletResponse resp)
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
		 * 3.获取查询条件，本方法就是author，即作者 
		 */
		String author = req.getParameter("author");
		/*
		 * 4.使用currPage和author调用service层的方法得到PageBean
		 */
		PageBean<Book> pageBean = bookService.findByAuthor(author, currPage);
		/*
		 * 5.给PageBean设置url,保存PageBean,转发到/jsps/book/list.jsp
		 */
		pageBean.setUrl(url);
		req.setAttribute("pb", pageBean);//将pageBean返回给页面
		return "f:/jsps/book/list.jsp";
	}
	
	public String findByPress(HttpServletRequest req, HttpServletResponse resp)
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
		 * 3.获取查询条件
		 */
		String press = req.getParameter("press");
		/*
		 * 4.使用currPage和cid调用service层的方法得到PageBean
		 */
		PageBean<Book> pageBean = bookService.findByPress(press, currPage);
		/*
		 * 5.给PageBean设置url,保存PageBean,转发到/jsps/book/list.jsp
		 */
		pageBean.setUrl(url);
		req.setAttribute("pb", pageBean);//将pageBean返回给页面
		return "f:/jsps/book/list.jsp";
	}
	
	public String findByBname(HttpServletRequest req, HttpServletResponse resp)
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
		 * 3.获取查询条件
		 */
		String bname = req.getParameter("bname");
		/*
		 * 4.使用currPage和bname调用service层的方法得到PageBean
		 */
		PageBean<Book> pageBean = bookService.findByBname(bname, currPage);
		/*
		 * 5.给PageBean设置url,保存PageBean,转发到/jsps/book/list.jsp
		 */
		pageBean.setUrl(url);
		req.setAttribute("pb", pageBean);//将pageBean返回给页面
		return "f:/jsps/book/list.jsp";
	}
	
	/**
	 * 多条件组合查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByCombination(HttpServletRequest req, HttpServletResponse resp)
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
		 * 3.获取查询条件
		 */
		Book criteria = CommonUtils.toBean(req.getParameterMap(), Book.class);
		/*
		 * 4.使用currPage和criteria调用service层的方法得到PageBean
		 */
		PageBean<Book> pageBean = bookService.findByCombination(criteria, currPage);
		/*
		 * 5.给PageBean设置url,保存PageBean,转发到/jsps/book/list.jsp
		 */
		pageBean.setUrl(url);
		req.setAttribute("pb", pageBean);//将pageBean返回给页面
		return "f:/jsps/book/list.jsp";
	}
	
	/**
	 * 按bid查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String bid = req.getParameter("bid");
		Book book = bookService.load(bid);
		req.setAttribute("book", book);
		return "f:/jsps/book/desc.jsp";
	}
	
}
