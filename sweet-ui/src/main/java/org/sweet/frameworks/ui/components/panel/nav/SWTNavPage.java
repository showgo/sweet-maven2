package org.sweet.frameworks.ui.components.panel.nav;

import java.io.IOException;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.sweet.frameworks.ui.components.Component;
import org.sweet.frameworks.ui.components.abstraction.SWTContainer;
import org.sweet.frameworks.ui.components.util.ComponentUtil;
import org.sweet.frameworks.ui.exception.RequiredAttributeException;

/**
 * 导航页(SWTNavPage)
 * @filename:SWTNavPage
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-5-5
 * @modifyrecords:
 */
public class SWTNavPage extends SWTContainer {
	public static final String COMPONENT_FAMILY="SWTNavPage";

	public String getFamily(){
		return COMPONENT_FAMILY;
	}

	public void encodeAll(FacesContext context) throws IOException{
		try{
			Component compo=ComponentUtil.getComponent(context,this);
			compo.validate();
			ResponseWriter out=context.getResponseWriter();
			Map<String,Object> attributesMap=compo.getPublicAttributesMap();
			out.startElement("div",this);
			out.writeAttribute("id",compo.getId(),null);
			out.writeAttribute("border","false",null);
			out.writeAttribute("title",attributesMap.get("title"),null);
			out.writeAttribute("closable",attributesMap.get("closable"),null);
			out.writeAttribute("style","border-width: 0px; padding: 5px; background: #fcffff;",null);
			this.encodeChildren(context);
			out.endElement("div");
		}catch(RequiredAttributeException e){
			e.printStackTrace();
		}
	}
}
