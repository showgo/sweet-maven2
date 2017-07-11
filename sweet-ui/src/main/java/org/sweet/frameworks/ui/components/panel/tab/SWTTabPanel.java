package org.sweet.frameworks.ui.components.panel.tab;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.sweet.frameworks.ui.components.Component;
import org.sweet.frameworks.ui.components.abstraction.SWTContainer;
import org.sweet.frameworks.ui.components.util.ComponentUtil;
import org.sweet.frameworks.ui.exception.RequiredAttributeException;

/**
 * 页面TabPanel
 * @filename:SWTTabPanel
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public class SWTTabPanel extends SWTContainer {
	public static final String COMPONENT_FAMILY="SWTTabPanel";

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
			out.startElement("div",this);
			out.writeAttribute("border",attributesMap.get("border"),null);
			out.writeAttribute("class","easyui-panel",null);
			out.writeAttribute("style",ComponentUtil.getStyle(attributesMap),null);
			out.writeAttribute("data-options",ComponentUtil.getDataOptions(attributesMap),null);
			if("center".equals(attributesMap.get("location"))) {
				out.writeAttribute("fit","true",null);
			}
			out.startElement("div",this);
			out.writeAttribute("id",compo.getId(),null);
			out.writeAttribute("xtype",compo.getXtype(),null);
			out.writeAttribute("class",compo.getStyleClass(),null);
			out.writeAttribute("border","false",null);
			out.writeAttribute("tabPosition",attributesMap.get("position"),null);
			/* generate panels item */
			for(UIComponent item:this.getChildren()){
				if(item instanceof SWTTabItem) {
					if(null!=attributesMap.get("activeItem")&&attributesMap.get("activeItem").equals(item.getAttributes().get("id"))) {
						item.getAttributes().put("selected","true");
					}
					item.encodeAll(context);
				}
			}
			out.endElement("div");
			out.endElement("div");
		}catch(RequiredAttributeException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
