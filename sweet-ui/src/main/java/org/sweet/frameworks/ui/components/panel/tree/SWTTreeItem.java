package org.sweet.frameworks.ui.components.panel.tree;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.sweet.frameworks.ui.components.Component;
import org.sweet.frameworks.ui.components.abstraction.SWTControl;
import org.sweet.frameworks.ui.components.util.ComponentUtil;
import org.sweet.frameworks.ui.components.util.TreeUtil;
import org.sweet.frameworks.ui.exception.RequiredAttributeException;

/**
 * 树节点项(SWTTreeItem)
 * @filename:SWTTreeItem
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-16
 * @modifyrecords:
 */
public class SWTTreeItem extends SWTControl {
	public static final String COMPONENT_FAMILY="SWTTreeItem";

	@Override
	public String getFamily(){
		return COMPONENT_FAMILY;
	}

	public void encodeAll(FacesContext context) throws IOException{
		try{
			/* validate */
			Component compo=ComponentUtil.getComponent(context,this);
			compo.validate();
			/* generate me */
			Map<String,Object> attributesMap=compo.getPublicAttributesMap();
			ResponseWriter out=context.getResponseWriter();
			List<UIComponent> items=TreeUtil.getItems(this);
			/* 有子菜单 */
			if(items.size()>0) {
				out.startElement("li",this);
				out.startElement("span",this);
				out.writeText(attributesMap.get("text"),null);
				out.endElement("span");
				out.startElement("ul",this);
				out.writeAttribute("data-options","state:'closed'",null);
				for(UIComponent item:items){
					item.encodeAll(context);
				}
				out.endElement("ul");
				out.endElement("li");
			}else{
				out.startElement("li",this);
				out.startElement("span",this);
				out.writeText(attributesMap.get("text"),null);
				out.endElement("span");
				out.endElement("li");
			}
		}catch(RequiredAttributeException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
