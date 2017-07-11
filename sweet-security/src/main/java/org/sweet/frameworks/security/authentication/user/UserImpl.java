package org.sweet.frameworks.security.authentication.user;

import java.util.ArrayList;
import java.util.List;

import org.sweet.frameworks.security.authentication.user.authority.UserAuthority;

/**
 * @Filename: UserImpl
 * @Company:
 * @Author: wugz
 * @Create: 2017年7月10日
 * @Version: 1.0.0
 * @ModifyRecords:
 */
public class UserImpl implements User {
	private static final long serialVersionUID=-973846806711714319L;
	private String id=null;
	private String account=null;
	private String password=null;
	private boolean accountExpired=false;
	private boolean accountLocked=false;
	private boolean passwordExpired=false;
	private boolean enabled=true;
	private Object details=null;
	private List<UserAuthority> authorities=new ArrayList<UserAuthority>();

	public UserImpl(){
	}

	public String getId(){
		return id;
	}

	public String getAccount(){
		return account;
	}

	public String getPassword(){
		return password;
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

	public void setId(String id){
		this.id=id;
	}

	public void setAccount(String account){
		this.account=account;
	}

	public void setPassword(String password){
		this.password=password;
	}

	public void setAccountExpired(boolean accountExpired){
		this.accountExpired=accountExpired;
	}

	public void setAccountLocked(boolean accountLocked){
		this.accountLocked=accountLocked;
	}

	public void setPasswordExpired(boolean passwordExpired){
		this.passwordExpired=passwordExpired;
	}

	public void setEnabled(boolean enabled){
		this.enabled=enabled;
	}

	public void setDetails(Object details){
		this.details=details;
	}

	public void setAuthorities(List<UserAuthority> authorities){
		this.authorities=authorities;
	}
}
