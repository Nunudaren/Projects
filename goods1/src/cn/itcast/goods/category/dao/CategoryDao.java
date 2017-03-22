package cn.itcast.goods.category.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.category.domain.Category;
import cn.itcast.jdbc.TxQueryRunner;

/**
 * 分类持久层
 * @author Andy
 *
 */
public class CategoryDao {
	private QueryRunner qr = new TxQueryRunner();
	
	/**
	 * 返回所有的分类
	 * @return
	 * @throws SQLException
	 */
	public List<Category> findAll() throws SQLException {
		/*
		 * 1. 查询出所有的一级分类
		 */
		String sql = "select * from t_category where pid is null order by orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler());
		
		List<Category> parents = toCategoryList(mapList);
		
		/*
		 * 2. 循环遍历所有的一级分类，为每个一级分类加载它的二级分类
		 */
		for(Category parent : parents) {
			//查询出当前父分类的所有子分类
			List<Category> children = findByParent(parent.getCid());
			//设置给父分类
			parent.setChildren(children);
		}
		return parents;
	}
	
	/**
	 * 通过父分类查询子分类
	 * @param pid
	 * @return
	 * @throws SQLException
	 */
	public List<Category> findByParent (String pid) throws SQLException {
		String sql = "select * from t_category where pid=?";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(), pid);
		return toCategoryList(mapList);
	}
	
	/**
	 * 可以把多个Map(<List<Map>)映射成多个Category(List<Category>)
	 * @param mapList
	 * @return
	 */
	private List<Category> toCategoryList(List<Map<String,Object>> mapList){
		List<Category> categoryList = new ArrayList<Category>();
		for(Map<String,Object> map : mapList) {
			Category c = toCategory(map);
			categoryList.add(c);
		}
		return categoryList;
	}
	
	/**
	 * 把一个Map中的数据映射到Category中
	 * 自身关联，子分类与父分类
	 * @param map
	 * @return
	 */
	private Category toCategory(Map<String,Object> map) {
		/*
		 * map:{cid:xx, cname:xx, pid:xx, desc:xx, orderBy:xx}
		 * Category: {cid:xx, cname:xx, parent:xx, desc:xx}
		 */
		Category category = CommonUtils.toBean(map, Category.class);//因为从数据库中查询出来的map中，pid不能顺利的映射成category对象的parent，所以要单独来设置这个属性
		String pid = (String) map.get("pid");//数据库表中的pid与Category类中的parent对应
		//一级分类：pid==null
		//二级分类：单独设置Category类的parent成员变量
		if(pid != null) { //如果父分类ID不为空，
			/*
			 * 创建一个相同的父分类对象来拦截pid
			 * 再把父分类设置给category
			 */
			Category parent = new Category();
			parent.setCid(pid);
			category.setParent(parent);
		}
		return category;
	}
	
	/**
	 * 添加分类
	 * @param category
	 * @throws SQLException
	 */
	public void add(Category category) throws SQLException {
		String sql = "insert into t_category(cid, cname, pid, `desc`) values(?,?,?,?)";
		/*
		 * 因为一级分类，没有parent，而二级分类有！
		 * 我们这个方法，要兼容两个分类，所以需要进行判断pid的值
		 */
		String pid = null;
		if(category.getParent() != null) {
			pid = category.getParent().getCid();
		}
		Object[] params = {category.getCid(), category.getCname(), pid, category.getDesc()};
		qr.update(sql, params);
	}
	
	/**
	 * 获取所有父分类，但不用加载子分类
	 * @return
	 * @throws SQLException
	 */
	public List<Category> findParents() throws SQLException {
		/*
		 * 1. 查询出所有的一级分类
		 */
		String sql = "select * from t_category where pid is null order by orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler());
		
		List<Category> parents = toCategoryList(mapList);
		
		return parents;
	}
	
	/**
	 * 加载分类
	 * 即可以加载一级分类，也可以加载二级分类
	 * @param cid
	 * @return
	 * @throws SQLException
	 */
	public Category load(String cid) throws SQLException {
		String sql = "select * from t_category where cid=?";
		Category category = toCategory(qr.query(sql, new MapHandler(), cid));
		return category;
	}
	
	/**
	 * 修改分类
	 * 即可以修改一级分类，也可以修改二级分类
	 * @param category
	 * @throws SQLException
	 */
	public void edit(Category category) throws SQLException {
		//`desc`:关键字，必须用``号来标识
		String sql = "update t_category set cname=?, pid=?, `desc`=? where cid=?";
		/*
		 * 判断pid的值
		 */
		String pid = null;
		if(category.getParent() != null) {
			pid = category.getParent().getCid();
		}
		Object[] params = {category.getCname(), pid, category.getDesc(), category.getCid()};
		qr.update(sql, params);
	}
	
	
	/**
	 * 查询指定父分类下子分类的个数
	 * @param pid
	 * @return
	 * @throws SQLException
	 */
	public int findChildCountByParent(String pid) throws SQLException {
		String sql = "select count(*) from t_category where pid=?";
		Number number = (Number) qr.query(sql, new ScalarHandler(), pid);
		return number == null ? 0 : number.intValue();
	}
	
	/**
	 * 删除分类（一级、二级）
	 * @param cid
	 * @throws SQLException
	 */
	public void delete(String cid) throws SQLException {
		String sql = "delete from t_category where cid=?";
		qr.update(sql, cid);
	}
	
	/**
	 * 通过cname查询分类是否已经存在
	 * @param cname
	 * @return
	 * @throws SQLException
	 */
	public int findCountByCname(String cname) throws SQLException {
		String sql = "select count(*) from t_category where cname=?";
		Number number = (Number)qr.query(sql, new ScalarHandler(), cname);
		return number == null ? 0 : number.intValue();
	}
}
