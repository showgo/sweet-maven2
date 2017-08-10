package org.sweet.frameworks.system.service.dao;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.sweet.frameworks.foundation.annotation.servlet.Servlet;
import org.sweet.frameworks.foundation.util.json.JSONUtil;
import org.sweet.frameworks.foundation.util.map.MapUtil;
import org.sweet.frameworks.system.filter.ControllerModel;
import org.sweet.frameworks.system.filter.ResponseMap;

/**
 * 基础ControllerServlet
 * @filename:ControllerServlet
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
@Servlet(value="/servlet/controller.do",allowValidated=true)
public final class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID=-4640860678047138875L;
	private ControllerModel model=null;

	public void initControllerModel(ControllerModel model){
		this.model=model;
	}

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out=response.getWriter();
		try{
			if(null!=this.model){
				Object object=this.model.getController().newInstance();
				Object result=this.model.getMethod().invoke(object,MapUtil.fromURL(this.model.getRequestUri()));
				out.write(JSONUtil.fromObject(ResponseMap.ok(result)));
			}else{
				throw new Exception("Bad Request: "+new String(Base64.decodeBase64(request.getParameter("uri"))));
			}
		}catch(Exception e){
			out.write(JSONUtil.fromObject(ResponseMap.fail(e.getMessage())));
			e.printStackTrace();
		}finally{
			if(null!=out){
				out.flush();
				out.close();
			}
		}
	}
}
