package org.sweet.frameworks.system.security.authentication.user;

import java.util.List;

import org.sweet.frameworks.system.security.authentication.user.authority.UserAuthority;

/**
 * @Filename: UserService
 * @Company:
 * @Author: wugz
 * @Create: 2017年7月10日
 * @Version: 1.0.0
 * @ModifyRecords:
 */
public interface UserService {
	/**
	 * Create a new user with the supplied details.
	 */
	int createUser(User user);

	/**
	 * Update the specified user.
	 */
	int updateUser(User user);

	/**
	 * update current user's password with newPassword
	 * @param user
	 * @param newPassword
	 */
	int updateUserPassword(User user,String newPassword);

	/**
	 * Remove the user with the given login name from the system.
	 */
	int deleteUser(String account);

	/**
	 * Check if a user with the supplied login name exists in the system.
	 */
	boolean userExists(String account);

	/**
	 * 根据账号查看用户
	 * @param account
	 * @return
	 */
	User queryUserByAccount(String account);

	/**
	 * 获得所有用户
	 * @return
	 */
	List<User> queryAllUser();

	/**
	 * 返回用户权限
	 * @param account
	 * @return
	 */
	List<UserAuthority> queryUserAuthorities(String account);
}
