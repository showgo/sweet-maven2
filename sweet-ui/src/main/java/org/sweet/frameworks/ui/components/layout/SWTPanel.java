package org.sweet.frameworks.ui.components.layout;

import java.io.IOException;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.sweet.frameworks.foundation.util.json.JSONUtil;
import org.sweet.frameworks.ui.components.Component;
import org.sweet.frameworks.ui.components.abstraction.SWTContainer;
import org.sweet.frameworks.ui.components.util.ComponentUtil;
import org.sweet.frameworks.ui.exception.RequiredAttributeException;

/**
 * 页面Panel
 * @filename:SWTPanel
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public class SWTPanel extends SWTContainer {
	public static final String COMPONENT_FAMILY="SWTPanel";

	@Override
	public String getFamily(){
		return COMPONENT_FAMILY;
	}

	public void encodeAll(FacesContext context) throws IOException{
		try{
			/* validate */
			Component compo=ComponentUtil.getComponent(context,this);
			compo.validate();
			this.setAttribute("id",compo.getId());
			this.setAttribute("xtype",compo.getXtype());
			/* generate me */
			Map<String,Object> publicAttributesMap=compo.getPublicAttributesMap();
			ResponseWriter out=context.getResponseWriter();
			/* 如果panel是嵌套布局(layout不为空),则渲染为panel布局 */
			if(null!=publicAttributesMap.get("layout")){
				/* define panel layout */
				out.startElement("div",this);
				for(Map.Entry<String,Object> entry:compo.getProtectedAttributesMap().entrySet()){
					out.writeAttribute(entry.getKey(),entry.getValue(),null);
				}
				out.writeAttribute("style",ComponentUtil.getStyle(publicAttributesMap),null);
				out.writeAttribute("fit","true",null);
				out.startElement("div",this);
				out.writeAttribute("class","easyui-layout",null);
				out.writeAttribute("data-options",ComponentUtil.getDataOptions(publicAttributesMap),null);
				/* generate children */
				this.encodeChildren(context);
				out.endElement("div");
				out.endElement("div");
			}else{
				/* define panel */
				out.startElement("div",this);
				for(Map.Entry<String,Object> entry:compo.getProtectedAttributesMap().entrySet()){
					out.writeAttribute(entry.getKey(),entry.getValue(),null);
				}
				out.writeAttribute("style",ComponentUtil.getStyle(publicAttributesMap),null);
				out.writeAttribute("data-options",ComponentUtil.getDataOptions(publicAttributesMap),null);
				/* generate children */
				if(null!=publicAttributesMap.get("content")){
					Map<String,Object> contentMap=JSONUtil.toMap(publicAttributesMap.get("content").toString());
					out.startElement("div",this);
					out.writeAttribute("border","false",null);
					if(contentMap.containsKey("align")){
						out.writeAttribute("class",contentMap.get("align")+" easyui-panel",null);
					}else{
						out.writeAttribute("class","easyui-panel",null);
					}
					out.writeText(contentMap.get("content"),null);
					out.endElement("div");
				}else{
					this.encodeChildren(context);
				}
				out.endElement("div");
			}
		}catch(RequiredAttributeException e){
			e.printStackTrace();
		}
	}
}
