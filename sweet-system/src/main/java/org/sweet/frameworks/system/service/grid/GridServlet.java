/**
 * 
 */
package org.sweet.frameworks.system.service.grid;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sweet.frameworks.foundation.annotation.servlet.Servlet;
import org.sweet.frameworks.foundation.util.json.JSONUtil;
import org.sweet.frameworks.foundation.util.map.MapUtil;
import org.sweet.frameworks.system.service.datasource.IDataSource;
import org.sweet.frameworks.system.service.grid.pagination.Pagination;
import org.sweet.frameworks.ui.resources.URLParameter;

/**
 * 基础GridServlet
 * @filename:GridServlet
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
@Servlet(value="/servlet/grid.do")
public final class GridServlet extends HttpServlet {
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
			Map<String,Object> param=new HashMap<String,Object>();
			if(null!=data){
				param.putAll(data);
			}
			/* 翻页数据 */
			param.put(Pagination.PAGE_NUMB,data.get("page"));
			param.put(Pagination.PAGE_SIZE,data.get("rows"));
			IDataSource source=new GridDataSource(request);
			out.println(JSONUtil.fromObject(source.getData(id,param)));
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
