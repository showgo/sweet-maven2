/**
 * 
 */
package org.sweet.frameworks.ui.components.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import org.sweet.frameworks.ui.components.layout.SWTView;

/**
 * 文档事件 (Document)
 * @filename:Document
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-22
 * @modifyrecords:
 */
public final class DocumentUtil {
	private static Map<String,List<Object>> scriptMapping=null;

	static{
		scriptMapping=new HashMap<String,List<Object>>();
	}

	/**
	 * 初始化viewMap
	 * @param component
	 */
	public static void initViewMap(UIComponent component){
		scriptMapping.put(component.getId(),new ArrayList<Object>());
	}

	/**
	 * 获得页面根组件view
	 * @param component
	 * @return
	 */
	public static UIComponent getViewComponent(UIComponent component){
		while(null!=component.getParent()){
			component=component.getParent();
			if(component instanceof SWTView) {
				return component;
			}
		}
		return null;
	}

	/**
	 * 添加页面脚本事件
	 * @param component
	 * @param processing
	 */
	public static void addScript(UIComponent component,Object processing){
		if(null!=component) {
			scriptMapping.get(component.getId()).add(processing);
		}
	}

	/**
	 * 在页面就绪事件中写脚本
	 * @param context
	 * @param component
	 * @throws IOException
	 */
	public static void writeScriptOnReady(FacesContext context,UIComponent component) throws IOException{
		ResponseWriter out=context.getResponseWriter();
		out.startElement("script",component);
		out.writeAttribute("type","text/javascript",null);
		out.writeAttribute("charset","utf-8",null);
		StringBuilder scripts=new StringBuilder();
		/* 必须反向生成列表中的内容 */
		scripts.append("$(function(){\n");
		List<Object> scriptList=scriptMapping.get(component.getId());
		for(int e=scriptList.size()-1;e>=0;e--){
			scripts.append("\t").append(scriptList.get(e)).append("\n");
		}
		scripts.append("});");
		out.writeText(scripts.toString(),null);
		out.endElement("script");
	}
}
