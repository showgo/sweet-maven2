package org.sweet.frameworks.security.authentication.provider;

import org.sweet.frameworks.security.authentication.Authentication;
import org.sweet.frameworks.security.authentication.UserPasswordAuthentication;
import org.sweet.frameworks.security.authentication.user.User;
import org.sweet.frameworks.security.authentication.user.UserService;
import org.sweet.frameworks.security.exception.AuthenticationException;

/**
 * @Filename: UserAuthenticationProvider
 * @Company:
 * @Author: wugz
 * @Create: 2017年7月10日
 * @Version: 1.0.0
 * @ModifyRecords:
 */
public class UserAuthenticationProvider implements AuthenticationProvider {
	private UserService userService;

	/**
	 * 构造函数
	 * @param userService
	 */
	public UserAuthenticationProvider(UserService userService){
		this.userService=userService;
	}

	public Authentication authenticate(String account,String password) throws AuthenticationException{
		UserPasswordAuthentication auth=new UserPasswordAuthentication();
		User user=userService.queryUserByAccount(account);
		try{
			if(null!=user){
				/* 校验用户本身 */
				if(user.isAccountExpired()){
					throw new AuthenticationException("The target user's account is expired: "+account);
				}else if(user.isPasswordExpired()){
					throw new AuthenticationException("The target user's password is expired: "+account);
				}else if(user.isLocked()){
					throw new AuthenticationException("The target user is locked: "+account);
				}else if(!user.isEnabled()){
					throw new AuthenticationException("The target user is disabled: "+account);
				}
				/* 校验用户密码 */
				if(!user.getPassword().equals(password)){
					throw new AuthenticationException("The login password of user is error: "+account);
				}
				/* 校验用户权限 */
				/* 验证通过 */
				auth.setId(user.getId());
				auth.setAccount(user.getAccount());
				auth.setDetails(user.getDetails());
				auth.setAuthorities(user.getAuthorities());
				auth.setAuthenticated(true);
				auth.setAuthenticationMessage("Authentication successfully.");
			}else{
				throw new AuthenticationException("The target user does not exist: "+account);
			}
		}catch(AuthenticationException ex){
			/* 验证未通过 */
			auth.setAuthenticated(false);
			auth.setAuthenticationMessage(ex.getMessage());
			throw ex;
		}
		return auth;
	}
}
