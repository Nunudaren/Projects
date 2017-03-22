package cn.itcast.goods.order.service;

import java.sql.SQLException;

import cn.itcast.goods.order.dao.OrderDao;
import cn.itcast.goods.order.domain.Order;
import cn.itcast.goods.pager.PageBean;
import cn.itcast.jdbc.JdbcUtils;

public class OrderService {
	private OrderDao orderDao = new OrderDao();
	
	/**
	 * 我的订单
	 * @param uid
	 * @param currPage
	 * @return
	 */
	public PageBean<Order> myOrders(String uid, int currPage) {
		try {
			JdbcUtils.beginTransaction();
			PageBean<Order> pb = orderDao.findByUser(uid, currPage);
			JdbcUtils.commitTransaction();
			return pb;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 查询所有订单
	 * @param currPage
	 * @return
	 */
	public PageBean<Order> findAll(int currPage) {
		try {
			JdbcUtils.beginTransaction();
			PageBean<Order> pb = orderDao.findAll(currPage);
			JdbcUtils.commitTransaction();
			return pb;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
	}
	
	
	
	/**
	 * 生成订单
	 * @param uid
	 * @param currPage
	 * @return
	 */
	public void createOrder(Order order) {
		try {
			JdbcUtils.beginTransaction();
			orderDao.add(order);
			JdbcUtils.commitTransaction();
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 加载订单到desc.jsp
	 * @param oid
	 * @return
	 */
	public Order load(String oid) {
		try {
			JdbcUtils.beginTransaction();
			Order order = orderDao.load(oid);
			JdbcUtils.commitTransaction();
			return order;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 查询订单状态
	 * @param oid
	 * @return
	 */
	public int findStatus(String oid) {
		try {
			JdbcUtils.beginTransaction();
			int status = orderDao.findStatus(oid);
			JdbcUtils.commitTransaction();
			return status;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
	}
	/**
	 * 修改订单状态
	 * @param oid
	 * @param status
	 */
	public void updateStatus(String oid, int status) {
		try {
			JdbcUtils.beginTransaction();
			orderDao.updateStatus(oid, status);
			JdbcUtils.commitTransaction();
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * 按状态查询
	 * @param status
	 * @param currPage
	 * @return
	 */
	public PageBean<Order> findByStatus(int status, int currPage) {
		try {
			JdbcUtils.beginTransaction();
			PageBean<Order> pb = orderDao.findByStatus(status, currPage);
			JdbcUtils.commitTransaction();
			return pb;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
	}
	
	
}
