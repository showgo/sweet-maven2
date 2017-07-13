package org.sweet.frameworks.security.authentication.provider;

import org.sweet.frameworks.security.authentication.Authentication;

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
	 * @param account
	 * @param password
	 * @return
	 */
	Authentication authenticate(String account,String password);
}
