package cn.itcast.goods.order.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.book.domain.Book;
import cn.itcast.goods.order.domain.Order;
import cn.itcast.goods.order.domain.OrderItem;
import cn.itcast.goods.pager.Expression;
import cn.itcast.goods.pager.PageBean;
import cn.itcast.goods.pager.PageConstants;
import cn.itcast.jdbc.TxQueryRunner;

public class OrderDao {
	private QueryRunner qr = new TxQueryRunner();
	
	/**
	 * 通用查询方法(按参数(exprList)中给出的条件查询)
	 * @param exprList
	 * @param currPage
	 * @return
	 * @throws SQLException
	 */
	private PageBean<Order> findByCriteria(List<Expression> exprList, int currPage) throws SQLException {
		/*
		 * 1.得到pageSize
		 * 2.得到totalCount
		 * 3.得到beanList
		 * 4.创建PageBean，返回
		 */
		
		/*
		 * 1.得到pageSize
		 */
		int pageSize = PageConstants.ORDER_PAGE_SIZE;//每页记录数
		
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
		String sql = "select count(*) from t_order" + whereSql;
		Number number = (Number) qr.query(sql, new ScalarHandler(), params.toArray());
		int totalCount = number.intValue();
		
		/*
		 * 4.得到beanList，即当页内容
		 */
		sql = "select * from t_order" + whereSql + " order by ordertime desc limit ?,?";
		params.add((currPage - 1) * pageSize);
		params.add(pageSize);
		
		List<Order> beanList = qr.query(sql, new BeanListHandler<Order>(Order.class), params.toArray());
		/*
		 * 注意：这里与bookDao不同的是：虽然已经获取了所有的订单，但是每个订单中并没有订单条目
		 * 我们还要：遍历每个订单，为其加载它的所有的订单条目
		 */
		for(Order order : beanList) {
			loadOrderItem(order);
		}
		
		/*
		 * 5.创建Pagebean对象并设置成员变量
		 */
		PageBean<Order> pb = new PageBean<Order>();
		//其中PageBean没有url，这个任务由Servlet来完成
		pb.setBeanList(beanList);
		pb.setCurrPage(currPage);
		pb.setPageSize(pageSize);
		pb.setTotalCount(totalCount);
		return pb;
	}
	
	/**
	 * 为指定的order加载它的所有orderItem
	 * @param order
	 * @throws SQLException 
	 */
	private void loadOrderItem(Order order) throws SQLException {
		/*
		 * 1.给sql语句select * from t_orderitem where oid=?
		 * 2.执行sql，得到List<OrderItem>
		 * 3.设置给Order对象
		 */
		String sql = "select * from t_orderitem where oid=?";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(), order.getOid());
		List<OrderItem> orderItemList = toOrderItemList(mapList);
		
		order.setOrderItemList(orderItemList);
	}

	/**
	 * 把多个Map转换成多个OrderItem
	 * @param mapList
	 * @return
	 */
	private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		for(Map<String,Object> map : mapList) {
			OrderItem orderItem = toOrderItem(map);
			orderItemList.add(orderItem);
		}
		return orderItemList;
	}

	/**
	 * 把一个Map转换成一个OrderItem
	 * @param map
	 * @return
	 */
	private OrderItem toOrderItem(Map<String, Object> map) {
		OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
		Book book = CommonUtils.toBean(map, Book.class);
		orderItem.setBook(book);
		return orderItem;
	}

	/**
	 * 按照用户名查询订单
	 * @param uid
	 * @param currPage
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Order> findByUser(String uid, int currPage) throws SQLException {
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("uid", "=", uid));
		return findByCriteria(exprList, currPage);
	}
	
	/**
	 * 查找所有订单
	 * @param currPage
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Order> findAll(int currPage) throws SQLException {
			List<Expression> exprList = new ArrayList<Expression>();
			return findByCriteria(exprList, currPage);
	}
	
	
	/**
	 * 生成订单
	 * @param order
	 * @throws SQLException
	 */
	public void add(Order order) throws SQLException {
		/*
		 * 1.插入订单
		 */
		String sql = "insert into t_order values(?,?,?,?,?,?)";
		Object[] params = {order.getOid(), order.getOrdertime(), order.getTotal(),
				order.getStatus(), order.getAddress(), order.getOwner().getUid()};
		qr.update(sql, params);
		
		/*
		 * 2.循环遍历订单的所有条目，让每个条目生成一个Object[]
		 * 多个条目就对应Object[][]
		 * 执行批处理，完成插入订单条目
		 */
		
		sql = "insert into t_orderitem values(?,?,?,?,?,?,?,?)";
		int len = order.getOrderItemList().size();
		Object[][] objs = new Object[len][];
		for(int i = 0; i < len; i++) {
			OrderItem item = order.getOrderItemList().get(i);
			objs[i] = new Object[]{item.getOrderItemId(), item.getQuantity(),
					item.getSubtotal(),item.getBook().getBid(),
					item.getBook().getBname(),item.getBook().getCurrPrice(),
					item.getBook().getImage_b(), order.getOid()};
		}
		qr.batch(sql, objs);
	}
	
	/**
	 * 加载订单（查看、取消、确认收货等按钮的连接操作）到desc.jsp
	 * @param oid
	 * @return
	 * @throws SQLException
	 */
	public Order load(String oid) throws SQLException {
		String sql = "select * from t_order where oid=?";
		Order order = qr.query(sql, new BeanHandler<Order>(Order.class), oid);
		//加载当前订单中的订单条目
		loadOrderItem(order);
		return order;
	}
	
	/**
	 * 查询订单状态
	 * @param oid
	 * @return
	 * @throws SQLException
	 */
	public int findStatus(String oid) throws SQLException {
		String sql = "select status from t_order where oid=?";
		Number number = (Number)qr.query(sql, new ScalarHandler(), oid);
		return number.intValue();
	}
	
	/**
	 * 修改订单状态
	 * @param oid
	 * @param status
	 * @throws SQLException 
	 */
	public void updateStatus(String oid, int status) throws SQLException {
		String sql = "update t_order set status=? where oid=?";
		qr.update(sql, status, oid);
	}
	
	
	
	/**
	 * 按状态查询
	 * @param status
	 * @param currPage
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Order> findByStatus(int status, int currPage) throws SQLException {
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("status", "=", status + ""));
		return findByCriteria(exprList, currPage);
	}
	
	
	
	
	
}
