package org.sweet.frameworks.security.authentication;

import java.util.ArrayList;
import java.util.List;

import org.sweet.frameworks.security.authentication.user.authority.UserAuthority;

/**
 * @Filename: UserPasswordAuthentication
 * @Company:
 * @Author: wugz
 * @Create: 2017年7月10日
 * @Version: 1.0.0
 * @ModifyRecords:
 */
public class UserPasswordAuthentication implements Authentication {
	private Object id=null;
	private Object account=null;
	private Object password=null;
	private Object details=null;
	private List<UserAuthority> authorities=new ArrayList<UserAuthority>();
	private boolean authenticated=false;
	private Object message=null;

	public UserPasswordAuthentication(){
	}

	public Object getId(){
		return id;
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

	public Object getAuthenticationMessage(){
		return message;
	}

	public void setId(Object id){
		this.id=id;
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

	public void setAuthenticationMessage(Object message){
		this.message=message;
	}
}
