/**
 * 
 */
package org.sweet.frameworks.system.startmenu;

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
import org.sweet.frameworks.system.service.datasource.ITreeDataSource;
import org.sweet.frameworks.ui.components.panel.tree.TreeModel;
import org.sweet.frameworks.ui.resources.URLParameter;

/**
 * 基础TreeServlet
 * @filename:TreeServlet
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
@Servlet(name="/servlet/startmenu.do")
public final class StartMenuServlet extends HttpServlet {
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
			Map<String,Object> param=new HashMap<String,Object>();
			if(null!=data){
				param.putAll(data);
			}
			/* 异步树:父节点id自动传入 */
			if(data.containsKey("id")){
				param.put(TreeModel.TreeFields.TREE_ID,data.get("id"));
			}
			/* 是否是同步树 */
			if(data.containsKey(TreeModel.TreeFields.TREE_SYNC)){
				param.put(TreeModel.TreeFields.TREE_SYNC,data.get(TreeModel.TreeFields.TREE_SYNC));
			}
			/* checkbox树 */
			if(data.containsKey(TreeModel.TreeFields.TREE_CHECKED)){
				param.put(TreeModel.TreeFields.TREE_CHECKED,data.get(TreeModel.TreeFields.TREE_CHECKED));
			}
			if(data.containsKey(TreeModel.TreeFields.TREE_CASCADE_CHECKED)){
				param.put(TreeModel.TreeFields.TREE_CASCADE_CHECKED,data.get(TreeModel.TreeFields.TREE_CASCADE_CHECKED));
			}
			if(data.containsKey(TreeModel.TreeFields.TREE_ONLYLEAF_CHECKED)){
				param.put(TreeModel.TreeFields.TREE_ONLYLEAF_CHECKED,data.get(TreeModel.TreeFields.TREE_ONLYLEAF_CHECKED));
			}
			Map<String,Object> model=new HashMap<String,Object>();
			if(data.containsKey(URLParameter.MODEL_LIT_)){
				model.putAll(MapUtil.getParamsMap(data.get(URLParameter.MODEL_LIT_).toString()));
			}
			ITreeDataSource source=new StartMenuDataSource(request);
			source.setModel(new TreeModel(model));
			out.println(JSONUtil.fromObject(source.getData("org.sweet.frameworks.system.mappings.Function.queryAll",param)));
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
