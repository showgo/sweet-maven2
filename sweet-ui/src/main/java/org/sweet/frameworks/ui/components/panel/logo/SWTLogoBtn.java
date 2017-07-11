package org.sweet.frameworks.ui.components.panel.logo;

import java.io.IOException;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.sweet.frameworks.ui.components.Component;
import org.sweet.frameworks.ui.components.abstraction.SWTControl;
import org.sweet.frameworks.ui.components.util.ComponentUtil;
import org.sweet.frameworks.ui.exception.RequiredAttributeException;

/**
 * logo按钮(SWTLogoBtn)
 * @filename:SWTLogoBtn
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-5-5
 * @modifyrecords:
 */
public class SWTLogoBtn extends SWTControl {
	public static final String COMPONENT_FAMILY="SWTLogoBtn";

	public String getFamily(){
		return COMPONENT_FAMILY;
	}

	public void encodeAll(FacesContext context) throws IOException{
		try{
			/* validate */
			Component compo=ComponentUtil.getComponent(context,this);
			compo.validate();
			/* start button */
			Map<String,Object> attributesMap=compo.getPublicAttributesMap();
			ResponseWriter out=context.getResponseWriter();
			out.startElement("a",this);
			out.writeAttribute("id",compo.getId(),null);
			out.writeAttribute("xtype",compo.getXtype(),null);
			out.writeAttribute("class",compo.getStyleClass(),null);
			out.writeAttribute("style",ComponentUtil.getStyle(attributesMap),null);
			out.writeAttribute("data-options",ComponentUtil.getDataOptions(attributesMap),null);
			out.writeAttribute("href","#",null);
			out.writeAttribute("title",attributesMap.get("title"),null);
			out.writeAttribute("onclick",attributesMap.get("handler"),null);
			out.writeAttribute("plain","true",null);
			out.writeText(attributesMap.get("title"),null);
			/* end button */
			out.endElement("a");
		}catch(RequiredAttributeException e){
			e.printStackTrace();
		}
	}
}
