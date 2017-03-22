package cn.itcast.goods.book.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.book.domain.Book;
import cn.itcast.goods.category.domain.Category;
import cn.itcast.goods.pager.Expression;
import cn.itcast.goods.pager.PageBean;
import cn.itcast.goods.pager.PageConstants;
import cn.itcast.jdbc.TxQueryRunner;

public class BookDao {
	private QueryRunner qr = new TxQueryRunner();
	
	/**
	 * 通用查询方法(按参数(exprList)中给出的条件查询)
	 * @param exprList
	 * @param currPage
	 * @return
	 * @throws SQLException
	 */
	private PageBean<Book> findByCriteria(List<Expression> exprList, int currPage) throws SQLException {
		/*
		 * 1.得到pageSize
		 * 2.得到totalCount
		 * 3.得到beanList
		 * 4.创建PageBean，返回
		 */
		
		/*
		 * 1.得到pageSize
		 */
		int pageSize = PageConstants.BOOK_PAGE_SIZE;//每页记录数
		
		/*
		 * 2.通过exprList来生成where子句
		 */
		StringBuilder whereSql = new StringBuilder(" where 1=1");
		List<Object> params = new ArrayList<Object>();//SQL语句中有问号，它是对应问号的值
		for(Expression expr : exprList) {
			/*
			 * 添加一个条件上，
			 * 1.以and开头
			 * 2.条件的名称
			 * 3.条件的运算符，可以是=、!=、>、<、...is null, is null没有值
			 * 4.如果条件不是is null，再追加问号，然后再向params中添加相应的值
			 */
			whereSql.append(" and ").append(expr.getName()).append(" ").append(expr.getOperator()).append(" ");
			//where 1=1 and bid = ?
			if(!expr.getOperator().equals("is null")) {
				whereSql.append("?");
				params.add(expr.getValue());
			}
		}
		
		/*
		 * 3.总记录数
		 */
		String sql = "select count(*) from t_book" + whereSql;
		Number number = (Number) qr.query(sql, new ScalarHandler(), params.toArray());
		int totalCount = number.intValue();
		
		/*
		 * 4.得到beanList，即当页内容
		 */
		sql = "select * from t_book" + whereSql + "order by orderBy limit ?,?";
		params.add((currPage - 1) * pageSize);
		params.add(pageSize);
		
		List<Book> beanList = qr.query(sql, new BeanListHandler<Book>(Book.class), params.toArray());
		
		/*
		 * 5.创建Pagebean对象并设置成员变量
		 */
		PageBean<Book> pb = new PageBean<Book>();
		//其中PageBean没有url，这个任务由Servlet来完成
		pb.setBeanList(beanList);
		pb.setCurrPage(currPage);
		pb.setPageSize(pageSize);
		pb.setTotalCount(totalCount);
		return pb;
	}
	
	/**
	 * 1.按分类查询
	 * @param cid
	 * @param currPage
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Book> findByCategory(String cid, int currPage) throws SQLException {
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("cid", "=", cid));
		return findByCriteria(exprList, currPage);
	}
	
	/**
	 * 2.按书名模糊查询
	 * @param bname
	 * @param currPage
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Book> findByBname(String bname, int currPage) throws SQLException {
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("bname", "like", "%" + bname + "%"));
		return findByCriteria(exprList, currPage);
	}
	/**
	 * 3.按作者查询
	 * @param author
	 * @param currPage
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Book> findByAuthor(String author, int currPage) throws SQLException {
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("author", "like", "%" + author + "%"));
		return findByCriteria(exprList, currPage);
	}
	/**
	 * 4.按出版社查询
	 * @param press
	 * @param currPage
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Book> findByPress(String press, int currPage) throws SQLException {
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("press", "like", "%" + press + "%"));
		return findByCriteria(exprList, currPage);
	}
	
	/**
	 * 5.多条件组合查询(将表单封装成一个Book对象，作为参数传递)
	 * @param criteria
	 * @param currPage
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Book> findByCombination(Book criteria, int currPage) throws SQLException {
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("bname", "like", "%" + criteria.getBname() + "%"));
		exprList.add(new Expression("author", "like", "%" + criteria.getAuthor() + "%"));
		exprList.add(new Expression("press", "like", "%" + criteria.getPress() + "%"));
		return findByCriteria(exprList, currPage);
	}
	
	/**
	 * 按bid查询
	 * @param bid
	 * @return
	 * @throws SQLException
	 */
	public Book findByBid(String bid) throws SQLException {
		String sql = "select * from t_book b, t_category c  where b.cid=c.cid and b.bid=?";
		//结果记录中，包含了很多book的属性，还有一个cid属性
		Map<String,Object> map = qr.query(sql, new MapHandler(), bid);
		//把Map中除了cid以外的其他属性映射到Book对象中
		Book book = CommonUtils.toBean(map, Book.class);
		//把Map中cid属性映射到Category中,即这个Category只有cid
		Category category = CommonUtils.toBean(map, Category.class);
		//两者建立关系
		book.setCategory(category);
		
		//获取pid，创建一个Category parent， 把pid赋值给它，然后再把parent赋值给category
		if(map.get("pid") != null) {
			Category parent = new Category();
			parent.setCid((String) map.get("pid"));
			category.setParent(parent);
		}
		return book;
		
	}
	
	/**
	 * 按cid查询
	 * 查询指定分类下图书的个数
	 * @param cid
	 * @return
	 * @throws SQLException
	 */
	public int findByCountByCategory(String cid) throws SQLException {
		String sql = "select count(*) from t_book where cid=?";
		Number number = (Number)qr.query(sql, new ScalarHandler(), cid);
		return number == null ? 0 : number.intValue();
	}

	/**
	 * 添加图书（后台）
	 * @param book
	 * @throws SQLException 
	 */
	public void add(Book book) throws SQLException {
		String sql = "insert into t_book(bid,bname,author,price,currPrice,discount," + 
				"press,publishtime,edition,pageNum,wordNum,printtime," + 
				"booksize,paper,cid,image_w,image_b) " + 
				"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {book.getBid(),book.getBname(),book.getAuthor(),
				book.getPrice(),book.getCurrPrice(),book.getDiscount(),
				book.getPress(),book.getPublishtime(),book.getEdition(),
				book.getPageNum(),book.getWordNum(),book.getPrinttime(),
				book.getBooksize(),book.getPaper(),book.getCategory().getCid(),
				book.getImage_w(),book.getImage_b()};
		qr.update(sql, params);
	}
	
	/**
	 * 修改图书信息（这里我们不去修改book的大小图片）
	 * @param book
	 * @throws SQLException 
	 */
	public void edit(Book book) throws SQLException {
		String sql = "update t_book set bname=?,author=?,price=?,currPrice=?,discount=?," + 
				"press=?,publishtime=?,edition=?,pageNum=?,wordNum=?,printtime=?," + 
				"booksize=?,paper=?,cid=? where bid=?";
		Object[] params = {book.getBname(),book.getAuthor(),
				book.getPrice(),book.getCurrPrice(),book.getDiscount(),
				book.getPress(),book.getPublishtime(),book.getEdition(),
				book.getPageNum(),book.getWordNum(),book.getPrinttime(),
				book.getBooksize(),book.getPaper(),book.getCategory().getCid(),
				book.getBid()};
		qr.update(sql, params);
	}
	
	/**
	 * 删除图书
	 * @param bid
	 * @throws SQLException
	 */
	public void delete(String bid) throws SQLException {
		String sql = "delete from t_book where bid=?";
		qr.update(sql, bid);
	}
	
	
}
