/**
 * 
 */
package org.sweet.frameworks.system.session;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.sweet.frameworks.foundation.util.debug.Debug;
import org.sweet.frameworks.security.authentication.Authentication;
import org.sweet.frameworks.system.loader.Config;

/**
 * 系统session(Session)
 * @filename:Session
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-5-10
 * @modifyrecords:
 */
public final class Session {
	/* 重入锁 */
	private static final ReentrantLock lock=new ReentrantLock();
	private HttpSession session=null;
	private SessionUser sessionUser=null;
	private static Map<String,Session> sessionMap=new ConcurrentHashMap<String,Session>();

	/**
	 * 构造函数
	 * @param request
	 * @param authentication
	 */
	private Session(HttpServletRequest request,Authentication authentication){
		lock.lock();
		try{
			int sec=null!=Config.getConfig("session-timeout") ? Integer.parseInt(Config.getConfig("session-timeout").toString()) : 24*3600;
			this.session=request.getSession(true);
			this.session.setMaxInactiveInterval(sec);
			/* session(覆盖)添加 */
			this.sessionUser=new SessionUser(this.session.getId(),authentication);
			sessionMap.put(this.sessionUser.getId(),this);
		}finally{
			lock.unlock();
		}
	}

	/**
	 * 打印Cookies
	 * @param request
	 */
	public static void listCookies(HttpServletRequest request){
		Cookie cookies[]=((HttpServletRequest)request).getCookies();
		if(null!=cookies){
			StringBuilder buffer=new StringBuilder();
			buffer.append("{");
			for(int i=0;i<cookies.length;i++){
				if(i>0){
					buffer.append(", ").append(cookies[i].getName()).append(": ").append(cookies[i].getValue());
				}else{
					buffer.append(cookies[i].getName()).append(": ").append(cookies[i].getValue());
				}
			}
			buffer.append("}");
			Debug.info(Session.class,buffer.toString());
		}
	}

	/**
	 * Session创建和初始化
	 * @param request
	 * @param authentication
	 * @return
	 */
	public static Session create(HttpServletRequest request,Authentication authentication){
		return new Session(request,authentication);
	}

	/**
	 * 返回当前Session
	 * @return
	 */
	public static Session current(HttpServletRequest request){
		return getSession(request.getSession().getId());
	}

	/**
	 * 获得当前会话user
	 * @param request
	 * @return
	 */
	public static SessionUser currentUser(HttpServletRequest request){
		return current(request).getUser();
	}

	/**
	 * 校验当前Session
	 * @param request
	 * @return
	 */
	public static boolean validate(HttpServletRequest request){
		Session current=null;
		try{
			current=getSession(request.getSession().getId());
			if(null!=current){
				return true;
			}
		}catch(IllegalStateException e){
			e.printStackTrace();
		}finally{
		}
		destroy(current);
		return false;
	}

	/**
	 * 使当前session失效
	 * @return
	 */
	public static void destroy(HttpServletRequest request){
		destroy(getSession(request.getSession().getId()));
	}

	private static void destroy(Session session){
		lock.lock();
		try{
			if(null!=session){
				sessionMap.remove(session.getUser().getId());
				session.session.invalidate();
			}
		}finally{
			lock.unlock();
		}
	}

	/**
	 * 根据会话id查询并返回会话
	 * @param id
	 * @return
	 */
	private static Session getSession(String id){
		lock.lock();
		try{
			for(Entry<String,Session> ent:sessionMap.entrySet()){
				if(ent.getValue().getId().equals(id)){
					return ent.getValue();
				}
			}
		}finally{
			lock.unlock();
		}
		return null;
	}

	/**
	 * 获得会话id
	 * @return
	 */
	public String getId(){
		return null!=session ? this.session.getId() : null;
	}

	/**
	 * 获得会话用户
	 * @return
	 */
	public SessionUser getUser(){
		return sessionUser;
	}
}
