package cn.itcast.goods.cart.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.cart.dao.CartItemDao;
import cn.itcast.goods.cart.domain.CartItem;

public class CartItemService {
	private CartItemDao cartItemDao = new CartItemDao();
	
	/**
	 * 我的购物车功能
	 * @param uid
	 * @return
	 */
	public List<CartItem> myCart(String uid) {
		try {
			return cartItemDao.findByUser(uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 添加条目到购物车中
	 * @param cartItem
	 */
	public void addCartItem(CartItem cartItem) {
		try {
			/*
			 * 1.使用表单的uid和bid查询数据库中是否已经存在该条目
			 */
			CartItem cartItem1 = cartItemDao.findByUidAndBid(cartItem.getUser().getUid(), 
					cartItem.getBook().getBid());
			if(cartItem1 == null) {//如果不存在，设置carItemId，添加条目
				cartItem.setCartItemId(CommonUtils.uuid());
				cartItemDao.addCartItem(cartItem);
			} else {//如果原来有这个条目，修改数量
				//使用原有数量和新条目数量的和，修改数据库
				int quantity = cartItem1.getQuantity() + cartItem.getQuantity();
				cartItemDao.updateQuantity(cartItem1.getCartItemId(), quantity);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 批量删除(删除也是用这个方法)
	 * @param cartItemIds
	 */
	public void batchDelete(String cartItemIds) {
		try {
			cartItemDao.batchDelete(cartItemIds);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * '+'/'-'号修改quantity
	 * @param cartItemId
	 * @param quantity
	 * @return
	 */
	public CartItem updateQuantity(String cartItemId, int quantity) {
		try {
			cartItemDao.updateQuantity(cartItemId, quantity);
			return cartItemDao.findByCartItemId(cartItemId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * 加载购物车中要购买的图书列表
	 * @param cartItemIds
	 * @return
	 */
	public List<CartItem> loadCartItems(String cartItemIds) {
		try {
			return cartItemDao.loadCartItems(cartItemIds);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
