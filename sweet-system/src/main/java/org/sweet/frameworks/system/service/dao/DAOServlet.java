package org.sweet.frameworks.system.service.dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sweet.frameworks.database.dao.service.Service;
import org.sweet.frameworks.foundation.annotation.servlet.Servlet;
import org.sweet.frameworks.foundation.util.json.JSONUtil;
import org.sweet.frameworks.foundation.util.map.MapUtil;
import org.sweet.frameworks.system.generator.GeneratorFactory;
import org.sweet.frameworks.ui.message.Messages;
import org.sweet.frameworks.ui.resources.URLParameter;

/**
 * 基础DAOServlet
 * @filename:DAOServlet
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
@Servlet(name="/servlet/baseDAO.do")
public final class DAOServlet extends HttpServlet {
	private static final long serialVersionUID=-4640860678047138875L;

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out=response.getWriter();
		Map<String,Object> ret=new HashMap<String,Object>();
		try{
			Map<String,Object> data=MapUtil.getParameterMap(request);
			Map<String,Object> generatorMap=MapUtil.getParamsMap(data.get(URLParameter.GENERATOR_).toString());
			GeneratorFactory.generate(data,generatorMap);
			Service service=new Service();
			int res=0;
			if(Boolean.parseBoolean(data.get(URLParameter.COMPILED_).toString())){
				res=service.execute(data.get(URLParameter.SQLID_LIT_).toString(),data);
			}else{
				res=service.execute(data.get(URLParameter.SQLID_LIT_).toString(),data);
			}
			if(res>0){
				ret.put(Messages.RETCODE,Messages.SUCCESS);
				ret.put(Messages.RETMESG,Messages.getDefault("success"));
			}else{
				ret.put(Messages.RETCODE,Messages.FAILURE);
				ret.put(Messages.RETMESG,Messages.getDefault("failure"));
			}
			out.write(JSONUtil.fromObject(ret));
		}catch(Exception e){
			ret.put(Messages.RETCODE,Messages.FAILURE);
			ret.put(Messages.RETMESG,Messages.getDefault("failure")+"<br>"+e.getMessage());
			out.write(JSONUtil.fromObject(ret));
			e.printStackTrace();
		}finally{
			if(null!=out){
				out.flush();
				out.close();
			}
		}
	}
}
