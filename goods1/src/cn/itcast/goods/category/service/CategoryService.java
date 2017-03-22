package cn.itcast.goods.category.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.goods.category.dao.CategoryDao;
import cn.itcast.goods.category.domain.Category;

/**
 * 分类模块业务层
 * @author Andy
 *
 */
public class CategoryService {
	private CategoryDao categoryDao = new CategoryDao();
	
	/**
	 * 查询所有分类
	 * @return
	 */
	public List<Category> findAll() {
		try {
			return categoryDao.findAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 添加所有的分类
	 * @param category
	 */
	public void add(Category category) {
		try {
			categoryDao.add(category);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 获取所有的父分类，但不加载子分类
	 * @return
	 */
	public List<Category> findParents() {
		try {
			return categoryDao.findParents();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 加载分类（一级、二级）
	 * @param cid
	 * @return
	 */
	public Category load(String cid) {
		try {
			return categoryDao.load(cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 修改分类（一级、二级）
	 * @param category
	 */
	public void edit(Category category) {
		try {
			categoryDao.edit(category);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 查询指定父分类下子分类的个数
	 * @param pid
	 * @return
	 */
	public int findChildCountByParent(String pid) {
		try {
			return categoryDao.findChildCountByParent(pid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 删除分类(一级、二级)
	 * @param cid
	 */
	public void delete(String cid) {
		try {
			categoryDao.delete(cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 通过cname查询分类是够已经存在
	 * @param cname
	 * @return
	 */
	public int findCountByCname(String cname) {
		try {
			return categoryDao.findCountByCname(cname);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 查询指定父分类下所有的子分类
	 * @param pid
	 * @return
	 */
	public List<Category> findChildren(String pid) {
		try {
			return categoryDao.findByParent(pid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
