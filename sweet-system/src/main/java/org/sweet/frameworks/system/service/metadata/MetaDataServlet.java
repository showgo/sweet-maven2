package org.sweet.frameworks.system.service.metadata;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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

/**
 * 基础DAOServlet
 * @filename:DAOServlet
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
@Servlet(value="/servlet/metadata.do")
public final class MetaDataServlet extends HttpServlet {
	private static final long serialVersionUID=3835210393642733385L;

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out=response.getWriter();
		try{
			Map<String,Object> data=MapUtil.getParameterMap(request);
			IDataSource service=new MetaDataSource(request);
			Map<String,Object> param=new HashMap<String,Object>();
			if(null!=data){
				param.putAll(data);
			}
			/* 翻页数据 */
			param.put(Pagination.PAGE_NUMB,data.get("page"));
			param.put(Pagination.PAGE_SIZE,data.get("rows"));
			out.write(JSONUtil.fromObject(service.getData(null,param)));
		}catch(SQLException e){
			out.write("{}");
			e.printStackTrace();
		}finally{
			if(null!=out){
				out.flush();
				out.close();
			}
		}
	}
}
