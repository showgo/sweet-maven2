/**
 * 
 */
package org.sweet.frameworks.system.session;

import java.util.ArrayList;
import java.util.List;

import org.sweet.frameworks.system.security.authentication.Authentication;
import org.sweet.frameworks.system.security.authentication.user.User;
import org.sweet.frameworks.system.security.authentication.user.authority.UserAuthority;

/**
 * 会话用户(SessionUser)
 * @filename:
 * @filedescription:
 * @copyright:版权所有(C)2009-2050
 * @company:成都淞幸科技有限责任公司
 * @summary:
 * @othersummary:
 * @finisheddate:
 * @modifyrecords:
 * @version:1.0.0
 * @date:wugz/2017年3月21日
 */
public final class SessionUser implements User {
	private static final long serialVersionUID=-4615943330385472176L;
	private String sessionId=null;
	private String account=null;
	private Object details=null;
	private boolean accountExpired=false;
	private boolean accountLocked=false;
	private boolean passwordExpired=false;
	private boolean enabled=true;
	private List<UserAuthority> authorities=new ArrayList<UserAuthority>();

	/**
	 * 构造函数
	 * @param sessionId
	 * @param authentication
	 */
	public SessionUser(String sessionId,Authentication authentication){
		this.sessionId=sessionId;
		this.account=authentication.getAccount().toString();
		this.details=authentication.getDetails();
		this.authorities=authentication.getAuthorities();
	}

	public String getSessionId(){
		return sessionId;
	}

	public String getAccount(){
		return account;
	}

	public String getPassword(){
		return "";
	}

	public boolean isAccountExpired(){
		return accountExpired;
	}

	public boolean isPasswordExpired(){
		return passwordExpired;
	}

	public boolean isLocked(){
		return accountLocked;
	}

	public boolean isEnabled(){
		return enabled;
	}

	public Object getDetails(){
		return details;
	}

	public List<UserAuthority> getAuthorities(){
		return authorities;
	}
}
