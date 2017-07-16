package org.sweet.frameworks.admin.model.testing;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sweet.frameworks.foundation.annotation.servlet.Servlet;
import org.sweet.frameworks.foundation.util.map.MapUtil;
import org.sweet.frameworks.security.authentication.user.manager.UserServiceManager;

/**
 * @Filename: UserTest
 * @Company:
 * @Author: wugz
 * @Create: 2017年7月13日
 * @Version: 1.0.0
 * @ModifyRecords:
 */
@Servlet(name="/servlet/user.do",allowValidated=false)
public final class UserTest extends HttpServlet {
	private static final long serialVersionUID=-4640860678047138875L;

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		try{
			Map<String,Object> data=MapUtil.getParameterMap(request);
			String method=data.get("method").toString();
			UserServiceManager user=new UserServiceManager(request);
			if("create".equals(method)){
				user.createUser(data.get("account").toString(),data.get("password").toString());
			}else if("update".equals(method)){
				user.updateUser(null);
			}else if("password".equals(method)){
				user.updateUserPassword(data.get("id").toString(),data.get("password").toString());
			}else if("delete".equals(method)){
				user.deleteUser(data.get("id").toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
