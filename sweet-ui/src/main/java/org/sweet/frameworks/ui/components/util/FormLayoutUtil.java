package org.sweet.frameworks.ui.components.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.sweet.frameworks.ui.components.abstraction.SWTControl;
import org.sweet.frameworks.ui.components.form.textbox.SWTHidden;
import org.sweet.frameworks.ui.exception.LayoutException;

/**
 * 表单布局处理类FormLayoutUtil
 * @filename:FormLayoutUtil
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public final class FormLayoutUtil {
	/**
	 * 渲染子元素
	 * @param context
	 * @param component
	 * @param attributesMap
	 * @throws IOException
	 * @throws LayoutException
	 */
	public static void encodeChildren(FacesContext context,UIComponent component,Map<String,Object> attributesMap) throws IOException,LayoutException{
		if(component.getChildCount()>0) {
			ResponseWriter out=context.getResponseWriter();
			out.startElement("table",component);
			out.writeAttribute("class","sweet-form-formtable",null);
			if("true".equals(attributesMap.get("lines"))) {
				out.writeAttribute("border","1",null);
				out.writeAttribute("style","margin-top: 2px; border-color: #c1dbff; border-collapse: collapse;",null);
			}
			out.startElement("tbody",component);
			int count=0;
			int columns=(null!=attributesMap.get("columns")) ? Integer.parseInt(attributesMap.get("columns").toString()) : 3;
			Map<String,List<UIComponent>> children=getChildrenGroups(component);
			List<UIComponent> visible=children.get("visible");
			if(visible.size()>0) {
				for(int index=0;index<visible.size();index+=count){
					count=encodeTR(context,component,visible,index,columns,attributesMap);
				}
			}
			List<UIComponent> hidden=children.get("hidden");
			if(hidden.size()>0) {
				out.startElement("tr",component);
				out.writeAttribute("style","display: none;",null);
				out.startElement("td",component);
				for(int index=0;index<hidden.size();index++){
					hidden.get(index).encodeAll(context);
				}
				out.endElement("td");
				out.endElement("tr");
			}
			out.endElement("tbody");
			out.endElement("table");
		}else{
			component.encodeChildren(context);
		}
	}

	/**
	 * 生成表格(table)的行(tr)
	 * @param context
	 * @param component
	 * @param visible
	 * @param start
	 * @param columns
	 * @param attributesMap
	 * @return
	 * @throws IOException
	 */
	private static int encodeTR(FacesContext context,UIComponent component,List<UIComponent> visible,int start,int columns,Map<String,Object> attributesMap) throws IOException{
		ResponseWriter out=context.getResponseWriter();
		out.startElement("tr",component);
		int index=0,generatedIndex=0;
		for(;index<columns&&(start+generatedIndex)<visible.size();){
			UIComponent compo=visible.get(start+generatedIndex);
			if(compo instanceof SWTControl) {
				Map<String,Object> attrsMap=compo.getAttributes();
				int colSpan=(null!=attrsMap.get("colSpan")) ? Integer.parseInt(attrsMap.get("colSpan").toString()) : 1;
				colSpan=colSpan>columns ? columns : colSpan;
				String align=(null!=attrsMap.get("align")) ? attrsMap.get("align").toString() : "left";
				if(colSpan>1&&(columns>=(index+colSpan))) {
					out.startElement("td",component);
					out.writeAttribute("colspan",colSpan,null);
					out.writeAttribute("style","width: "+(100/columns*colSpan)+"%; text-align: "+align+"; vertical-align: middle;",null);
					compo.getAttributes().put("labelWidth",attributesMap.get("labelWidth"));
					compo.encodeAll(context);
					out.endElement("td");
					index+=colSpan;
					generatedIndex++;
				}else if(colSpan==1) {
					out.startElement("td",component);
					out.writeAttribute("style","width: "+(100/columns*colSpan)+"%; text-align: "+align+"; vertical-align: middle;",null);
					compo.getAttributes().put("labelWidth",attributesMap.get("labelWidth"));
					compo.encodeAll(context);
					out.endElement("td");
					index+=colSpan;
					generatedIndex++;
				}else{
					out.startElement("td",component);
					out.writeAttribute("style","width: "+(100/columns*colSpan)+"%; text-align: "+align+"; vertical-align: middle;",null);
					out.writeText("",null);
					out.endElement("td");
					break;
				}
			}
		}
		out.endElement("tr");
		return generatedIndex;
	}

	/**
	 * 获得分组后的孩子节点
	 * @param component
	 * @return
	 */
	private static Map<String,List<UIComponent>> getChildrenGroups(UIComponent component){
		Map<String,List<UIComponent>> children=new HashMap<String,List<UIComponent>>();
		List<UIComponent> hidden=new ArrayList<UIComponent>();
		List<UIComponent> visible=new ArrayList<UIComponent>();
		for(UIComponent compo:component.getChildren()){
			if(compo instanceof SWTControl) {
				if(compo instanceof SWTHidden) {
					hidden.add(compo);
				}else{
					visible.add(compo);
				}
			}
		}
		children.put("hidden",hidden);
		children.put("visible",visible);
		return children;
	}
}
