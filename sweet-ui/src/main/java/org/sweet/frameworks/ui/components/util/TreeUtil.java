package org.sweet.frameworks.ui.components.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.sweet.frameworks.foundation.util.json.JSONUtil;
import org.sweet.frameworks.ui.components.panel.tree.SWTTreeItem;
import org.sweet.frameworks.ui.components.panel.tree.TreeModel;

/**
 * TreeUtil
 * @filename:TreeUtil
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-16
 * @modifyrecords:
 */
public final class TreeUtil {
	/**
	 * 生成节点xml
	 * @param context
	 * @param component
	 * @param model
	 * @param node
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static void encodeNode(FacesContext context,UIComponent component,TreeModel model,Map<String,Object> node) throws IOException{
		ResponseWriter out=context.getResponseWriter();
		out.startElement("li",component);
		/* id */
		out.writeAttribute("id",node.get(model.getId()),null);
		/* attributes, state */
		if(node.containsKey("item")) {
			String status=null!=node.get(model.isExpand()) ? node.get(model.isExpand()).toString() : "0";
			out.writeAttribute("data-options","attributes: "+JSONUtil.fromObject(node)+", state: "+("1".equals(status) ? "'open'" : "'closed'")+"",null);
		}else{
			out.writeAttribute("data-options","attributes: "+JSONUtil.fromObject(node),null);
		}
		out.startElement("span",component);
		/* text */
		out.writeText(node.get(model.getText()),null);
		out.endElement("span");
		if(node.containsKey("item")) {
			out.startElement("ul",component);
			List<Map<String,Object>> items=(List<Map<String,Object>>)node.get("item");
			for(Map<String,Object> item:items){
				encodeNode(context,component,model,item);
			}
			out.endElement("ul");
		}
		out.endElement("li");
	}

	/**
	 * 获得子菜单
	 * @param component
	 * @return
	 */
	public static List<UIComponent> getItems(UIComponent component){
		List<UIComponent> components=new ArrayList<UIComponent>();
		for(UIComponent compo:component.getChildren()){
			if(compo instanceof SWTTreeItem) {
				components.add(compo);
			}
		}
		return components;
	}
}
