package org.sweet.frameworks.system.security.authentication;

import java.util.ArrayList;
import java.util.List;

import org.sweet.frameworks.system.security.authentication.user.authority.UserAuthority;

/**
 * @Filename: UserPasswordAuthentication
 * @Company:
 * @Author: wugz
 * @Create: 2017年7月10日
 * @Version: 1.0.0
 * @ModifyRecords:
 */
public class UserPasswordAuthentication implements Authentication {
	private Object account=null;
	private Object password=null;
	private Object details=null;
	private List<UserAuthority> authorities=new ArrayList<UserAuthority>();
	private boolean authenticated=false;

	public UserPasswordAuthentication(){
	}

	public Object getAccount(){
		return account;
	}

	public Object getPassword(){
		return password;
	}

	public Object getDetails(){
		return details;
	}

	public List<UserAuthority> getAuthorities(){
		return authorities;
	}

	public boolean isAuthenticated(){
		return authenticated;
	}

	public void setAccount(Object account){
		this.account=account;
	}

	public void setDetails(Object details){
		this.details=details;
	}

	public void setAuthorities(List<UserAuthority> authorities){
		this.authorities=authorities;
	}

	public void setAuthenticated(boolean isAuthenticated){
		this.authenticated=isAuthenticated;
	}
}
