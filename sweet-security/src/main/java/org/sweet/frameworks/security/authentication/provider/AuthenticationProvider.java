package org.sweet.frameworks.security.authentication.provider;

import org.sweet.frameworks.security.authentication.Authentication;
import org.sweet.frameworks.security.exception.AuthenticationException;

/**
 * @Filename: AuthenticationProvider
 * @Company:
 * @Author: wugz
 * @Create: 2017年7月10日
 * @Version: 1.0.0
 * @ModifyRecords:
 */
public interface AuthenticationProvider {
	/**
	 * 身份鉴定
	 * @param account 账号
	 * @param password 密码
	 * @return
	 * @throws AuthenticationException
	 */
	Authentication authenticate(String account,String password) throws AuthenticationException;
}
