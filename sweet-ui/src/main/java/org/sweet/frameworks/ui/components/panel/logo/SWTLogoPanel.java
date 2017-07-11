package org.sweet.frameworks.ui.components.panel.logo;

import java.io.IOException;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.sweet.frameworks.ui.components.Component;
import org.sweet.frameworks.ui.components.abstraction.SWTContainer;
import org.sweet.frameworks.ui.components.util.ComponentUtil;
import org.sweet.frameworks.ui.exception.RequiredAttributeException;

/**
 * logo(SWTLogoPanel)
 * @filename:SWTLogoPanel
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-5-5
 * @modifyrecords:
 */
public class SWTLogoPanel extends SWTContainer {
	public static final String COMPONENT_FAMILY="SWTLogoPanel";

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
			out.writeAttribute("id",compo.getId(),null);
			out.writeAttribute("xtype",compo.getXtype(),null);
			out.writeAttribute("border",attributesMap.get("border"),null);
			out.writeAttribute("class",compo.getStyleClass(),null);
			out.writeAttribute("style",ComponentUtil.getStyle(attributesMap),null);
			out.writeAttribute("data-options","region: '"+attributesMap.get("location")+"'",null);
			/* generate logo */
			String styleString=ComponentUtil.getAlignStyle(attributesMap);
			out.startElement("div",this);
			out.writeAttribute("class","sweet-logo-title-container",null);
			out.writeAttribute("style",styleString,null);
			out.startElement("span",this);
			out.writeAttribute("class","sweet-logo-title-icons "+attributesMap.get("icons"),null);
			out.writeAttribute("style",styleString,null);
			out.endElement("span");
			out.startElement("span",this);
			out.writeAttribute("class","sweet-logo-title-content",null);
			out.writeText(attributesMap.get("title"),null);
			out.endElement("span");
			out.endElement("div");
			/* generate btn */
			out.startElement("div",this);
			out.writeAttribute("class","sweet-logo-btn-container",null);
			out.writeAttribute("style",styleString,null);
			out.startElement("form",this);
			out.writeAttribute("id",this.getId()+"_form",null);
			out.writeAttribute("action",attributesMap.get("action"),null);
			out.writeAttribute("enctype","multipart/form-data",null);
			out.writeAttribute("method","post",null);
			this.encodeChildren(context);
			out.endElement("form");
			out.endElement("div");
			out.endElement("div");
		}catch(RequiredAttributeException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
