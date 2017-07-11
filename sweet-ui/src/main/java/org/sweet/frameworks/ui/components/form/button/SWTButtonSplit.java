package org.sweet.frameworks.ui.components.form.button;

import java.io.IOException;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.sweet.frameworks.ui.components.Component;
import org.sweet.frameworks.ui.components.abstraction.SWTControl;
import org.sweet.frameworks.ui.components.util.ComponentUtil;
import org.sweet.frameworks.ui.exception.RequiredAttributeException;

/**
 * 按钮分隔类SWTButtonSplit
 * @filename:SWTButtonSplit
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public class SWTButtonSplit extends SWTControl {
	public static final String COMPONENT_FAMILY="SWTButtonSplit";

	public String getFamily(){
		return COMPONENT_FAMILY;
	}

	public void encodeAll(FacesContext context) throws IOException{
		try{
			/* validate */
			Component compo=ComponentUtil.getComponent(context,this);
			compo.validate();
			/* start separator */
			Map<String,Object> attributesMap=compo.getPublicAttributesMap();
			ResponseWriter out=context.getResponseWriter();
			out.startElement("span",this);
			out.writeAttribute("xtype",compo.getXtype(),null);
			out.writeAttribute("class",compo.getStyleClass(),null);
			out.writeAttribute("style","width: "+Integer.parseInt(attributesMap.get("width").toString())+"px;",null);
			out.writeText("",null);
			/* end separator */
			out.endElement("span");
		}catch(RequiredAttributeException e){
			e.printStackTrace();
		}
	}
}
