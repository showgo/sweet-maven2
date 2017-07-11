package org.sweet.frameworks.system.security.authentication;

import java.util.List;
import org.sweet.frameworks.system.security.authentication.user.authority.UserAuthority;

/**
 * @Filename: Authentication
 * @Company:
 * @Author: wugz
 * @Create: 2017年7月10日
 * @Version: 1.0.0
 * @ModifyRecords:
 */
public interface Authentication {
	Object getAccount();

	Object getPassword();

	Object getDetails();

	List<UserAuthority> getAuthorities();

	boolean isAuthenticated();

	void setAuthenticated(boolean isAuthenticated);

	void setAuthenticationMessage(Object message);

	Object getAuthenticationMessage();
}