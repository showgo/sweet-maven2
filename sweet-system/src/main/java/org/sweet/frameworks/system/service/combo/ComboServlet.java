/**
 * 
 */
package org.sweet.frameworks.system.service.combo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sweet.frameworks.database.dao.service.Service;
import org.sweet.frameworks.foundation.annotation.servlet.Servlet;
import org.sweet.frameworks.foundation.util.json.JSONUtil;
import org.sweet.frameworks.foundation.util.map.MapUtil;
import org.sweet.frameworks.ui.resources.URLParameter;

/**
 * 基础ComboServlet
 * @filename:ComboServlet
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
@Servlet(name="/servlet/combo.do")
public final class ComboServlet extends HttpServlet {
	private static final long serialVersionUID=-4640860678047138875L;

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out=response.getWriter();
		try{
			Map<String,Object> data=MapUtil.getParameterMap(request);
			String id=null;
			if(data.containsKey(URLParameter.SQLID_LIT_)){
				id=data.get(URLParameter.SQLID_LIT_).toString();
			}
			Map<String,Object> model=new HashMap<String,Object>();
			if(data.containsKey(URLParameter.MODEL_)){
				model.putAll(MapUtil.getParamsMap(data.get(URLParameter.MODEL_).toString()));
			}
			/* 获取数据 */
			Service service=new Service(request);
			List<Map<String,Object>> list=service.query(id,data);
			for(Map<String,Object> map:list){
				map.put("id",map.get(model.get("id")));
				map.put("text",map.get(model.get("text")));
			}
			out.println(JSONUtil.fromObject(list));
		}catch(Exception e){
			out.println("{}");
			e.printStackTrace();
		}finally{
			if(null!=out){
				out.flush();
				out.close();
			}
		}
	}
}
