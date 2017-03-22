package cn.itcast.goods.admin.book.web.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.book.domain.Book;
import cn.itcast.goods.book.service.BookService;
import cn.itcast.goods.category.domain.Category;
import cn.itcast.goods.category.service.CategoryService;
import cn.itcast.goods.pager.PageBean;
import cn.itcast.servlet.BaseServlet;

public class AdminBookServlet extends BaseServlet {
	private BookService bookService = new BookService();
	private CategoryService categoryService = new CategoryService();
	
	/**
	 * 显示所有分类
	 * 直接从前台的CategoryServlet中拿过来
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findCategoryAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1.通过service层得到所有的分类
		 * 2.保存到request中，转发到left.jsp
		 */
		List<Category> parents = categoryService.findAll();
		req.setAttribute("parents",parents);
		return "f:/adminjsps/admin/book/left.jsp";
	}
	
	
	
	/*---------------一下直接方法直接从BookServlet中拿来用，只要把返回的url给修改了就行----------------------------------------------------------------*/
	
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
		return "f:/adminjsps/admin/book/list.jsp";
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
		return "f:/adminjsps/admin/book/list.jsp";
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
		return "f:/adminjsps/admin/book/list.jsp";
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
		return "f:/adminjsps/admin/book/list.jsp";
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
		return "f:/adminjsps/admin/book/list.jsp";
	}
	
	
	/**
	 * 添加图书：第一步（查询出所有的一级分类）
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1.获取所有一级分类，保存之
		 * 2.转发到add.jsp,该页面会在下拉列表中显示所有的一级分类
		 */
		List<Category> parents = categoryService.findParents();
		req.setAttribute("parents", parents);
		return "f:/adminjsps/admin/book/add.jsp";
	}
	
	/**
	 * 添加图书：第二步（通过一级分类的cid(也是二级分类的pid),查询出它的所有子类）
	 * 用异步请求来做
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxFindChildren(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		/*
		 * 1.获取pid
		 * 2.通过pid查询出所有的2级分类
		 * 3.把List<category>转换成json，输出给客户端
		 */
		String pid = req.getParameter("pid");
		List<Category> children = categoryService.findChildren(pid);
		
		String json = toJson(children);//返回ajax：String字符串 -- json对象
//		System.out.println(json);
		resp.getWriter().print(json);
		return null;
	}
	
	//{"cid":"asdgasg","cname":"sfdgwe"}
	private String toJson(Category category) {
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"cid\"").append(":").append("\"").append(category.getCid()).append("\"");
		sb.append(",");
		sb.append("\"cname\"").append(":").append("\"").append(category.getCname()).append("\"");
		sb.append("}");
		return sb.toString();
	}

	//[{"cid":"asdgasg","cname":"sfdgwe"},{"cid":"asdgasg","cname":"sfdgwe"}...]
	private String toJson(List<Category> categoryList) {
		StringBuilder sb = new StringBuilder("[");
		for(int i = 0; i < categoryList.size(); i++) {
			sb.append(toJson(categoryList.get(i)));
			if(i < categoryList.size() - 1) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	
	
	/**
	 * 加载图书
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		/*
		 * 1.获取bid，得到Book对象，保存
		 */
		String bid = req.getParameter("bid");
		Book book = bookService.load(bid);
		req.setAttribute("book", book);
		
		/*
		 * 2.获取所有一级分类，保存
		 */
		req.setAttribute("parents", categoryService.findParents());
		/*
		 * 3.获取当前图书所属的一级分类下所有的2级分类	
		 * 这里注意：加载book的时候 要将BookDao的findByBid()方法修改成多表查询
		 * 方法里要创建一个parent类，设置它的cid，然后将parent设置给category
		 * 否则book.getCategory().getParent()会出现空指针异常；
		 */
		String pid = book.getCategory().getParent().getCid();
		req.setAttribute("children", categoryService.findChildren(pid));
		/*
		 * 4.转发到desc.jsp显示
		 */
		return "f:/adminjsps/admin/book/desc.jsp";
	}
	
	
	/**
	 * 修改图书信息（这里不修改图片）
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String edit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		/*
		 * 1.把表单数据封装到Book对象中
		 * 2.封装cid到Category类中
		 * 3.把Category赋值给Book
		 * 4.调用service层完成工作
		 * 5.保存成功信息，转发到msg.jsp
		 */
		Map map = req.getParameterMap();
		Book book = CommonUtils.toBean(map, Book.class);
		Category category = CommonUtils.toBean(map, Category.class);
		book.setCategory(category);
		
		//调用service
		bookService.edit(book);
		req.setAttribute("msg", "修改图书成功！");
		return "f:/adminjsps/msg.jsp";
	}
	
	/**
	 * 删除图书
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String bid = req.getParameter("bid");
		Book book = bookService.load(bid);
		/*
		 * 1.删除book的image_b和image_w
		 */
		String savepath = this.getServletContext().getRealPath("/");//获取真实的路径，即WebContent的路径
		new File(savepath, book.getImage_w()).delete();//删除文件
		new File(savepath, book.getImage_b()).delete();//删除文件
		/*
		 * 2.调用service层删除图书
		 */
		bookService.delete(bid);
		req.setAttribute("msg", "删除图书成功!");
		return "f:/adminjsps/msg.jsp";
	}
	
	
}
