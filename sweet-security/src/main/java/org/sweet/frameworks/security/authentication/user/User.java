package org.sweet.frameworks.security.authentication.user;

import java.io.Serializable;
import java.util.List;

import org.sweet.frameworks.security.authentication.user.authority.UserAuthority;

/**
 * @Filename: User
 * @Company:
 * @Author: wugz
 * @Create: 2017年7月10日
 * @Version: 1.0.0
 * @ModifyRecords:
 */
public interface User extends Serializable {
	public static final String ID="id";
	public static final String ACCOUNT="account";
	public static final String PASSWORD="password";
	public static final String IS_ACCOUNT_EXPIRED="is_account_expired";
	public static final String IS_PASSWORD_EXPIRED="is_password_expired";
	public static final String IS_LOCKED="is_locked";
	public static final String IS_ENABLED="is_enabled";

	/**
	 * 返回用户Id
	 * @return
	 */
	String getId();

	/**
	 * 返回帐号
	 * @return
	 */
	String getAccount();

	/**
	 * 返回密码
	 * @return
	 */
	String getPassword();

	/**
	 * 账号是否过期
	 * @return
	 */
	boolean isAccountExpired();

	/**
	 * 密码是否过期
	 * @return
	 */
	boolean isPasswordExpired();

	/**
	 * 账号是否锁定
	 * @return
	 */
	boolean isLocked();

	/**
	 * 用户是否启用
	 * @return
	 */
	boolean isEnabled();

	/**
	 * 获得用户详细信息
	 * @return
	 */
	Object getDetails();

	/**
	 * 返回用户权限
	 * @return
	 */
	List<UserAuthority> getAuthorities();
}
