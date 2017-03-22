package cn.itcast.goods.cart.domain;

import java.math.BigDecimal;

import cn.itcast.goods.book.domain.Book;
import cn.itcast.goods.user.domain.User;

public class CartItem {
	private String cartItemId;//主键
	private int quantity;//主键
	private Book book;//条目对应的图书
	private User user;//所属用户
	
	
	
	//添加小计方法:购买数量*购买单价
	@SuppressWarnings("unused")
	private double subtotal;
	
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getSubtotal() {
		/*
		 * 为了没有误差，这里必须用BigDecimal类型来运算
		 * 而且必须使用BigDecimal的String类型的构造器
		 */
		BigDecimal b1 = new BigDecimal(book.getCurrPrice() + "");//将参数转换成String类型
		BigDecimal b2 = new BigDecimal(quantity + "");
		BigDecimal b3 = b1.multiply(b2);
		return b3.doubleValue();
	}
	
	public String getCartItemId() {
		return cartItemId;
	}
	public void setCartItemId(String cartItemId) {
		this.cartItemId = cartItemId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
