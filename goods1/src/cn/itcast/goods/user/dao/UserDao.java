package cn.itcast.goods.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.goods.user.domain.User;
import cn.itcast.jdbc.TxQueryRunner;

/**
 * 用户模块持久层
 * @author qdmmy6
 *
 */
public class UserDao {
	private QueryRunner qr = new TxQueryRunner();
	
	/**
	 * 校验用户名是否已经注册过
	 * @param loginname
	 * @return
	 * @throws SQLException
	 */
	public boolean ajaxValidateLoginname(String loginname) throws SQLException {
		String sql = "select count(1) from t_user where loginname=?";
		Number number = (Number)qr.query(sql, new ScalarHandler(), loginname);
		return number.intValue() == 0;
	}
	/**
	 * 校验Email是否已经注册过
	 * @param email
	 * @return
	 * @throws SQLException
	 */
	public boolean ajaxValidateEmail(String email) throws SQLException {
		String sql = "select count(1) from t_user where email=?";
		Number number = (Number)qr.query(sql, new ScalarHandler(), email);
		return number.intValue() == 0;
	}
	
	/**
	 * 添加用户
	 * @param user
	 * @throws SQLException
	 */
	public void add(User user) throws SQLException {
		String sql = "insert into t_user values(?,?,?,?,?,?)";//注意中间加上“，”
		Object[] params = {user.getUid(), user.getLoginname(), user.getLoginpass(), user.getEmail(), user.isStatus(), user.getActivationCode()};
		qr.update(sql, params);
	}
	
	/**
	 * 通过激活码查询用户
	 * @param activationCode
	 * @return
	 * @throws SQLException
	 */
	public User findByActivationCode(String activationCode) throws SQLException {
		String sql = "select * from t_user where activationCode=?";
		return qr.query(sql, new BeanHandler<User>(User.class), activationCode);
	}
	
	/**
	 * 修改用户状态
	 * @param uid
	 * @param status
	 * @throws SQLException
	 */
	public void updateStatus(String uid, boolean status) throws SQLException {
		String sql = "update t_user set status=? where uid=?";
		qr.update(sql, status,uid);
	}
	
	/**
	 * 登陆时查询，按uid和password查询
	 * @param loginname
	 * @param loginpass
	 * @return
	 * @throws SQLException
	 */
	public User findByLoginnameAndLoginpass(String loginname, String loginpass) throws SQLException {
		String sql = "select * from t_user where loginname=? and loginpass=?";
		return qr.query(sql, new BeanHandler<User>(User.class), loginname, loginpass);
	}
	
	/**
	 * 按照uid和password查询用户
	 * @param uid
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public boolean findByUidAndPassword(String uid, String password) throws SQLException {
		String sql = "select count(*) from t_user where uid=? and loginpass=?";
		Number number = (Number) qr.query(sql, new ScalarHandler(), uid, password);
		return number.intValue() > 0 ;
	}
	
	/**
	 * 修改密码
	 * @param uid
	 * @param passwrod
	 * @throws SQLException
	 */
	public void updatePassword(String uid, String passwrod) throws SQLException {
		String sql = "update t_user set loginpass=? where uid=?";
		qr.update(sql, passwrod, uid);
	}
}
