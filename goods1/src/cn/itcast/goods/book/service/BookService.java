package cn.itcast.goods.book.service;

import java.sql.SQLException;

import cn.itcast.goods.book.dao.BookDao;
import cn.itcast.goods.book.domain.Book;
import cn.itcast.goods.pager.PageBean;

public class BookService {
	private BookDao bookDao = new BookDao();
	
	/**
	 * 1.按分类查询
	 * @param cid
	 * @param currPage
	 * @return
	 */
	public PageBean<Book> findByCategory(String cid, int currPage) {
		try {
			return bookDao.findByCategory(cid, currPage);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 2.按书名查询
	 * @param cid
	 * @param currPage
	 * @return
	 */
	public PageBean<Book> findByBname(String bname, int currPage) {
		try {
			return bookDao.findByBname(bname, currPage);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 3.按作者查询
	 * @param cid
	 * @param currPage
	 * @return
	 */
	public PageBean<Book> findByAuthor(String author, int currPage) {
		try {
			return bookDao.findByAuthor(author, currPage);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 4.按出版社查询
	 * @param cid
	 * @param currPage
	 * @return
	 */
	public PageBean<Book> findByPress(String press, int currPage) {
		try {
			return bookDao.findByPress(press, currPage);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 5.多条件组合查询
	 * @param cid
	 * @param currPage
	 * @return
	 */
	public PageBean<Book> findByCombination(Book criteria, int currPage) {
		try {
			return bookDao.findByCombination(criteria, currPage);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 加载图书（按bid查询出图书信息）
	 * @param bid
	 * @return
	 */
	public Book load(String bid) {
		try {
			return bookDao.findByBid(bid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * 返回当前分类下的图书个数
	 * @param cid
	 * @return
	 */
	public int findBookCountByCategory(String cid) {
		try {
			return bookDao.findByCountByCategory(cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 添加图书
	 * @param book
	 */
	public void add(Book book) {
		try {
			bookDao.add(book);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 修改图书
	 * @param book
	 */
	public void edit(Book book) {
		try {
			bookDao.edit(book);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 删除图书
	 * @param bid
	 */
	public void delete(String bid) {
		try {
			bookDao.delete(bid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
