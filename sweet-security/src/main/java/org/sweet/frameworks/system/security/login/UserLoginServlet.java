package org.sweet.frameworks.system.security.login;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sweet.frameworks.foundation.annotation.servlet.Servlet;
import org.sweet.frameworks.foundation.util.json.JSONUtil;
import org.sweet.frameworks.foundation.util.map.MapUtil;
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
	private void doLogin(HttpServletRequest request,HttpServletResponse response,Map<String,Object> data) throws IOException,
		SQLException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		UserService service=new UserService(request);
		Map<String,Object> ret=service.login(data);
		if("1".equals(ret.get(Messages.RETCODE))){
			Session session=Session.create(request,ret);
			response.sendRedirect(request.getContextPath()+"/main.xhtml?sessionId="+session.getId());
			return;
		}else{
			/* _swtui_page_messages_:用于返回登录信息的特殊参数 */
			response.sendRedirect(request.getContextPath()+"/index.xhtml?_swtui_page_messages_="+Messages.RETCODE+","+ret.get(
				Messages.RETCODE)+","+Messages.RETMESG+","+String.valueOf(ret.get(Messages.RETMESG))+"&sessionId=-1");
			return;
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
	private void doLogout(HttpServletRequest request,HttpServletResponse response,Map<String,Object> data) throws IOException,
		SQLException{
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
	private void doRegist(HttpServletRequest request,HttpServletResponse response,Map<String,Object> data) throws IOException,
		SQLException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		ServletOutputStream out=response.getOutputStream();
		UserService service=new UserService(request);
		Map<String,Object> ret=service.register(data);
		out.println(JSONUtil.fromObject(ret));
		out.flush();
		out.close();
	}
}
