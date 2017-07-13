package org.sweet.frameworks.system.login;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sweet.frameworks.foundation.annotation.servlet.Servlet;
import org.sweet.frameworks.foundation.util.json.JSONUtil;
import org.sweet.frameworks.foundation.util.map.MapUtil;
import org.sweet.frameworks.security.authentication.Authentication;
import org.sweet.frameworks.security.authentication.provider.UserAuthenticationProvider;
import org.sweet.frameworks.security.authentication.user.UserService;
import org.sweet.frameworks.security.authentication.user.manager.UserServiceManager;
import org.sweet.frameworks.security.exception.AuthenticationException;
import org.sweet.frameworks.system.session.Session;
import org.sweet.frameworks.ui.message.Messages;

/**
 * 用户登录UserLoginServlet
 * @filename:UserLoginServlet
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
@Servlet(name="/servlet/login.do",allowValidated=false)
public final class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID=-4640860678047138875L;

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		try{
			Map<String,Object> data=MapUtil.getParameterMap(request);
			if("login".equals(data.get("method"))){
				doLogin(request,response,data);
			}else if("regedit".equals(data.get("method"))){
				doRegist(request,response,data);
			}else{
				doLogout(request,response,data);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	/**
	 * 登录
	 * @param request
	 * @param response
	 * @param data
	 * @throws IOException
	 * @throws SQLException
	 */
	private void doLogin(HttpServletRequest request,HttpServletResponse response,Map<String,Object> data) throws IOException,SQLException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		UserService service=new UserServiceManager();
		UserAuthenticationProvider provider=new UserAuthenticationProvider(service);
		try{
			Authentication auth=provider.authenticate(data.get("account").toString(),data.get("password").toString());
			if(auth.isAuthenticated()){
				Session session=Session.create(request,auth);
				response.sendRedirect(request.getContextPath()+"/main.xhtml?sessionId="+session.getId());
				return;
			}else{
				/* _swtui_page_messages_:用于返回登录信息的特殊参数 */
				response.sendRedirect(request.getContextPath()+"/index.xhtml?_swtui_page_messages_="+Messages.RETCODE+",-1,"+Messages.RETMESG+","+String.valueOf(auth.getAuthenticationMessage())+"&sessionId=-1");
				return;
			}
		}catch(AuthenticationException e){
			e.printStackTrace();
		}
	}

	/**
	 * 登出
	 * @param request
	 * @param response
	 * @param data
	 * @throws IOException
	 * @throws SQLException
	 */
	private void doLogout(HttpServletRequest request,HttpServletResponse response,Map<String,Object> data) throws IOException,SQLException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		Session.destroy(request);
		response.sendRedirect(request.getContextPath()+"/index.xhtml?sessionId=-1");
		return;
	}

	/**
	 * 注册
	 * @param request
	 * @param response
	 * @param data
	 * @throws IOException
	 * @throws SQLException
	 */
	private void doRegist(HttpServletRequest request,HttpServletResponse response,Map<String,Object> data) throws IOException,SQLException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		ServletOutputStream out=response.getOutputStream();
		UserService service=new UserServiceManager(request);
		Map<String,Object> map=new HashMap<String,Object>();
		if(service.createUser(data.get("account").toString(),data.get("password").toString())>0){
			map.put(Messages.RETCODE,"1");
			map.put(Messages.RETMESG,"Congratulations, user registration success.");
		}else{
			map.put(Messages.RETCODE,"0");
			map.put(Messages.RETMESG,"Registration failed: user already exists.");
		}
		out.println(JSONUtil.fromObject(map));
		out.flush();
		out.close();
	}
}
