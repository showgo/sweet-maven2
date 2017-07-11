package org.sweet.frameworks.system.login;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.sweet.frameworks.database.sql.SQL;
import org.sweet.frameworks.foundation.util.debug.Debug;
import org.sweet.frameworks.system.security.MD5;
import org.sweet.frameworks.system.service.Service;
import org.sweet.frameworks.ui.message.Messages;

/**
 * 用户登录验证实体类UserService
 * @filename:UserService
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public class UserService extends Service implements Serializable {
	private static final long serialVersionUID=-1055046666275749500L;

	public UserService(HttpServletRequest request){
		super(request);
	}

	/**
	 * 用户登录校验
	 * @param data 数据
	 * @throws SQLException
	 */
	public Map<String,Object> login(Map<String,Object> data) throws SQLException{
		String userId=(null!=data&&data.containsKey("userId")) ? data.get("userId").toString() : null;
		String password=(null!=data&&data.containsKey("password")) ? data.get("password").toString() : null;
		Map<String,Object> user=this.queryMap("org.sweet.frameworks.system.mappings.User.queryUser",userId);
		String enpwd=(null!=password&&!"".equals(password)) ? MD5.encode(password) : null;
		if(null!=user&&null!=enpwd&&enpwd.equals(user.get("password"))) {
			Map<String,Object> ret=new HashMap<String,Object>();
			Debug.info(UserService.class,"Congratulations, login authentication passed.");
			ret.putAll(user);
			ret.put(Messages.RETCODE,"1");
			ret.put(Messages.RETMESG,"Congratulations, login authentication passed.");
			return ret;
		}else{
			Map<String,Object> ret=new HashMap<String,Object>();
			Debug.info(UserService.class,"Login failed: invalid user name or password.");
			ret.put(Messages.RETCODE,"0");
			ret.put(Messages.RETMESG,"Login failed: invalid user name or password.");
			return ret;
		}
	}

	/**
	 * 用户退出
	 * @param data 数据
	 * @throws SQLException
	 */
	public Map<String,Object> logout(Map<String,Object> data) throws SQLException{
		Map<String,Object> ret=new HashMap<String,Object>();
		Debug.info(UserService.class,"Congratulations, logout authentication passed.");
		ret.put(Messages.RETCODE,"1");
		ret.put(Messages.RETMESG,"Congratulations, logout authentication passed.");
		return ret;
	}

	/**
	 * 用户注册方法
	 * @param data 数据
	 * @throws SQLException
	 */
	public Map<String,Object> register(final Map<String,Object> data) throws SQLException{
		SQL sql=new SQL() {
			protected boolean statements() throws SQLException{
				String userId=(null!=data&&data.containsKey("userId")) ? data.get("userId").toString() : null;
				String password=(null!=data&&data.containsKey("password")) ? data.get("password").toString() : null;
				Map<String,Object> user=this.queryMap("org.sweet.frameworks.system.mappings.User.queryUser",userId);
				if(null!=user) {
					return false;
				}
				Map<String,String> param=new HashMap<String,String>();
				param.put("userId",userId);
				param.put("password",MD5.encode(password));
				this.execute("org.sweet.frameworks.system.mappings.User.insertUser",param);
				return true;
			}
		};
		Map<String,Object> ret=new HashMap<String,Object>();
		if(this.execute(sql)) {
			/* 注册成功 */
			Debug.info(UserService.class,"Congratulations, user registration success.");
			ret.put(Messages.RETCODE,"1");
			ret.put(Messages.RETMESG,"Congratulations, user registration success.");
			return ret;
		}
		/* 注册失败 */
		Debug.info(UserService.class,"Registration failed: user already exists.");
		ret.put(Messages.RETCODE,"0");
		ret.put(Messages.RETMESG,"Registration failed: user already exists.");
		return ret;
	}
}
