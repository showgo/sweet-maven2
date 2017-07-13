package org.sweet.frameworks.security.authentication.user;

import java.util.List;

import org.sweet.frameworks.security.authentication.user.authority.UserAuthority;

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
	int createUser(String account,String password);

	/**
	 * Update the specified user.
	 */
	int updateUser(User user);

	/**
	 * update current user's password with newPassword
	 * @param id
	 * @param newPassword
	 */
	int updateUserPassword(String id,String newPassword);

	/**
	 * Remove the user with the given login name from the system.
	 */
	int deleteUser(String id);

	/**
	 * Check if a user with the supplied login name exists in the system.
	 */
	boolean userExists(String id);

	/**
	 * 根据id查找用户
	 * @param id
	 * @return
	 */
	User queryUserById(String id);

	/**
	 * 根据账号查找用户
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
	 * @param id 用户id
	 * @return
	 */
	List<UserAuthority> queryUserAuthorities(String id);
}
