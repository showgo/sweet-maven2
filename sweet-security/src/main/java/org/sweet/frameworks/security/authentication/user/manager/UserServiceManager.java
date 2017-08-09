package org.sweet.frameworks.security.authentication.user.manager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import org.sweet.frameworks.database.dao.service.Service;
import org.sweet.frameworks.database.sql.SQL;
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
	protected HttpServletRequest request;

	public UserServiceManager(){
	}

	public UserServiceManager(HttpServletRequest request){
		this.request=request;
	}

	public int createUser(String account,String password){
		try{
			UserBuilder userBuilder=UserBuilder.getBuilder();
			String id=UserBuilder.uuid();
			userBuilder.id(id);
			userBuilder.account(account);
			userBuilder.password(password);
			final Map<String,Object> parameter=userBuilder.buildMap();
			SQL sql=new SQL() {
				protected boolean statements() throws SQLException{
					try{
						this.execute("org.sweet.frameworks.security.mappings.User.insertUser",parameter);
						this.execute("org.sweet.frameworks.security.mappings.User.insertUserDetails",parameter);
						return true;
					}catch(SQLException ex){
						throw ex;
					}
				}
			};
			return this.execute(sql) ? 1 : 0;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 0;
	}

	public int updateUser(User user){
		return 0;
	}

	public int updateUserPassword(String id,String newPassword){
		try{
			Map<String,Object> parameter=new HashMap<String,Object>();
			parameter.put("id",id);
			parameter.put("password",newPassword);
			return this.execute("org.sweet.frameworks.security.mappings.User.updateUserPassword",parameter);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 0;
	}

	public int deleteUser(String id){
		try{
			final Map<String,Object> parameter=new HashMap<String,Object>();
			parameter.put("id",id);
			SQL sql=new SQL() {
				protected boolean statements() throws SQLException{
					try{
						this.execute("org.sweet.frameworks.security.mappings.User.deleteUserDetails",parameter);
						this.execute("org.sweet.frameworks.security.mappings.User.deleteUser",parameter);
						return true;
					}catch(SQLException ex){
						throw ex;
					}
				}
			};
			return this.execute(sql) ? 1 : 0;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
		}
		return 0;
	}

	public boolean userExists(String id){
		return null!=this.queryUserById(id) ? true : false;
	}

	public User queryUserById(String id){
		Map<String,Object> parameter=new HashMap<String,Object>();
		parameter.put("id",id);
		return this.queryUser("org.sweet.frameworks.security.mappings.User.queryUser",parameter);
	}

	public User queryUserByAccount(String account){
		Map<String,Object> parameter=new HashMap<String,Object>();
		parameter.put("account",account);
		return this.queryUser("org.sweet.frameworks.security.mappings.User.queryUser",parameter);
	}

	private User queryUser(String sql,Map<String,Object> parameter){
		try{
			Map<String,Object> map=this.queryMap(sql,parameter);
			if(null!=map&&map.size()>0){
				/* 用户信息 */
				UserBuilder builder=UserBuilder.getBuilder();
				builder.id(null!=map.get(User.ID) ? map.get(User.ID).toString() : "");
				builder.account(null!=map.get(User.ACCOUNT) ? map.get(User.ACCOUNT).toString() : "");
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
		private UserImpl userImpl=null;

		private UserBuilder(){
		}

		static UserBuilder getBuilder(){
			UserBuilder builder=new UserBuilder();
			builder.userImpl=new UserImpl();
			return builder;
		}

		static String uuid(){
			return String.valueOf(UUID.randomUUID()).replaceAll("-","");
		}

		UserBuilder id(String id){
			userImpl.setId(id);
			return this;
		}

		UserBuilder account(String account){
			userImpl.setAccount(account);
			return this;
		}

		UserBuilder password(String password){
			userImpl.setPassword(password);
			return this;
		}

		UserBuilder accountExpired(boolean accountExpired){
			userImpl.setAccountExpired(accountExpired);
			return this;
		}

		UserBuilder passwordExpired(boolean passwordExpired){
			userImpl.setPasswordExpired(passwordExpired);
			return this;
		}

		UserBuilder accountLocked(boolean accountLocked){
			userImpl.setAccountLocked(accountLocked);
			return this;
		}

		UserBuilder enabled(boolean enabled){
			userImpl.setEnabled(enabled);
			return this;
		}

		UserImpl build(){
			return userImpl;
		}

		Map<String,Object> buildMap(){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put(User.ID,userImpl.getId());
			map.put(User.ACCOUNT,userImpl.getAccount());
			map.put(User.PASSWORD,userImpl.getPassword());
			map.put(User.IS_ACCOUNT_EXPIRED,userImpl.isAccountExpired());
			map.put(User.IS_PASSWORD_EXPIRED,userImpl.isPasswordExpired());
			map.put(User.IS_LOCKED,userImpl.isLocked());
			map.put(User.IS_ENABLED,userImpl.isEnabled());
			return map;
		}
	}
}
