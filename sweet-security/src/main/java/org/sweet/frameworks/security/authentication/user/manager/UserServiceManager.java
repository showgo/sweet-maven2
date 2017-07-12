package org.sweet.frameworks.security.authentication.user.manager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.sweet.frameworks.database.dao.service.Service;
import org.sweet.frameworks.security.authentication.user.User;
import org.sweet.frameworks.security.authentication.user.UserImpl;
import org.sweet.frameworks.security.authentication.user.UserService;
import org.sweet.frameworks.security.authentication.user.authority.UserAuthority;

/**
 * @Filename: UserServiceManager
 * @Company:
 * @Author: wugz
 * @Create: 2017年7月10日
 * @Version: 1.0.0
 * @ModifyRecords:
 */
public class UserServiceManager extends Service implements UserService {
	public UserServiceManager(HttpServletRequest request){
		super(request);
	}

	public int createUser(User user){
		return 0;
	}

	public int updateUser(User user){
		return 0;
	}

	public int updateUserPassword(User user,String newPassword){
		return 0;
	}

	public int deleteUser(String account){
		return 0;
	}

	public boolean userExists(String account){
		return false;
	}

	public User queryUserByAccount(String account){
		try{
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("account",account);
			Map<String,Object> map=this.queryMap("org.sweet.frameworks.security.mappings.User.queryUser",param);
			if(null!=map&&map.size()>0){
				/* 用户信息 */
				UserBuilder builder=UserBuilder.withAccount(account);
				builder.id(null!=map.get(User.ID) ? map.get(User.ID).toString() : "");
				builder.password(null!=map.get(User.PASSWORD) ? map.get(User.PASSWORD).toString() : "");
				builder.accountExpired(null!=map.get(User.IS_ACCOUNT_EXPIRED)&&"Y".equalsIgnoreCase(map.get(User.IS_ACCOUNT_EXPIRED).toString()) ? true : false);
				builder.passwordExpired(null!=map.get(User.IS_PASSWORD_EXPIRED)&&"Y".equalsIgnoreCase(map.get(User.IS_PASSWORD_EXPIRED).toString()) ? true : false);
				builder.accountLocked(null!=map.get(User.IS_LOCKED)&&"Y".equalsIgnoreCase(map.get(User.IS_LOCKED).toString()) ? true : false);
				builder.enabled(null!=map.get(User.IS_ENABLED)&&"Y".equalsIgnoreCase(map.get(User.IS_ENABLED).toString()) ? true : false);
				/* 用户详细信息 */
				/* 用户权限信息 */
				return builder.build();
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return null;
	}

	public List<User> queryAllUser(){
		return null;
	}

	public List<UserAuthority> queryUserAuthorities(String account){
		return null;
	}

	/**
	 * (UserBuilder)
	 */
	public static class UserBuilder {
		private static UserBuilder builder=null;
		private UserImpl userImpl=null;

		static UserBuilder withId(String id){
			builder=new UserBuilder();
			builder.userImpl=new UserImpl();
			builder.userImpl.setId(id);
			return builder;
		}

		static UserBuilder withAccount(String account){
			builder=new UserBuilder();
			builder.userImpl=new UserImpl();
			builder.userImpl.setAccount(account);
			return builder;
		}

		UserBuilder id(String id){
			builder.userImpl.setId(id);
			return builder;
		}

		UserBuilder account(String account){
			builder.userImpl.setAccount(account);
			return builder;
		}

		UserBuilder password(String password){
			builder.userImpl.setPassword(password);
			return builder;
		}

		UserBuilder accountExpired(boolean accountExpired){
			builder.userImpl.setAccountExpired(accountExpired);
			return builder;
		}

		UserBuilder passwordExpired(boolean passwordExpired){
			builder.userImpl.setPasswordExpired(passwordExpired);
			return builder;
		}

		UserBuilder accountLocked(boolean accountLocked){
			builder.userImpl.setAccountLocked(accountLocked);
			return builder;
		}

		UserBuilder enabled(boolean enabled){
			builder.userImpl.setEnabled(enabled);
			return builder;
		}

		UserImpl build(){
			return builder.userImpl;
		}
	}
}
