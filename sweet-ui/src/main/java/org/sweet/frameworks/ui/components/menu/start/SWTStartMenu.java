package org.sweet.frameworks.ui.components.menu.start;

import java.io.IOException;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.sweet.frameworks.ui.components.Component;
import org.sweet.frameworks.ui.components.abstraction.SWTContainer;
import org.sweet.frameworks.ui.components.util.ComponentUtil;
import org.sweet.frameworks.ui.exception.RequiredAttributeException;

/**
 * SWTStartMenu
 * @filename:SWTStartMenu
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2017年6月11日
 * @modifyrecords:
 */
public class SWTStartMenu extends SWTContainer {
	public static final String COMPONENT_FAMILY="SWTStartMenu";

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
			// FIXME
			Map<String,Object> attributesMap=compo.getPublicAttributesMap();
			ResponseWriter out=context.getResponseWriter();
			out.startElement("div",this);
			out.writeAttribute("id",compo.getId(),null);
			out.writeAttribute("xtype",compo.getXtype(),null);
			out.writeAttribute("class",compo.getStyleClass(),null);
			out.writeAttribute("style","text-align: "+attributesMap.get("align")+";",null);
			/* generate children */
			this.encodeChildren(context);
			out.endElement("div");
			/* end generate me */
		}catch(RequiredAttributeException e){
			e.printStackTrace();
		}
	}
}
