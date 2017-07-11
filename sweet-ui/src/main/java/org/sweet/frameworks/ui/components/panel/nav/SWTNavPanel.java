package org.sweet.frameworks.ui.components.panel.nav;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.sweet.frameworks.ui.components.Component;
import org.sweet.frameworks.ui.components.abstraction.SWTContainer;
import org.sweet.frameworks.ui.components.util.ComponentUtil;
import org.sweet.frameworks.ui.exception.RequiredAttributeException;

/**
 * 导航(SWTNavPanel)
 * @filename:SWTNavPanel
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-5-5
 * @modifyrecords:
 */
public class SWTNavPanel extends SWTContainer {
	public static final String COMPONENT_FAMILY="SWTNavPanel";

	@Override
	public String getFamily(){
		return COMPONENT_FAMILY;
	}

	public void encodeAll(FacesContext context) throws IOException{
		try{
			/* validate */
			Component compo=ComponentUtil.getComponent(context,this);
			compo.validate();
			this.setAttribute("xtype",compo.getXtype());
			/* generate me */
			Map<String,Object> attributesMap=compo.getPublicAttributesMap();
			ResponseWriter out=context.getResponseWriter();
			out.startElement("div",this);
			// out.writeAttribute("border",attributesMap.get("border"),null);
			out.writeAttribute("class","easyui-panel",null);
			out.writeAttribute("style",ComponentUtil.getStyle(attributesMap),null);
			out.writeAttribute("data-options",ComponentUtil.getDataOptions(attributesMap),null);
			out.startElement("div",this);
			out.writeAttribute("id",compo.getId(),null);
			out.writeAttribute("xtype",compo.getXtype(),null);
			out.writeAttribute("class",compo.getStyleClass(),null);
			out.writeAttribute("border","false",null);
			out.writeAttribute("justified","false",null);
			out.writeAttribute("pill","true",null);
			if("center".equals(attributesMap.get("location"))) {
				out.writeAttribute("fit","true",null);
			}
			List<UIComponent> childs=this.getChildren();
			for(UIComponent child:childs){
				if(child instanceof SWTNavPage) {
					((SWTNavPage)child).encodeAll(context);
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
