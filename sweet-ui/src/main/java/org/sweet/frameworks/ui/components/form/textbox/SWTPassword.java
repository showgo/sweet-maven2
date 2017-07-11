package org.sweet.frameworks.ui.components.form.textbox;

import java.io.IOException;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.sweet.frameworks.ui.components.Component;
import org.sweet.frameworks.ui.components.abstraction.SWTControl;
import org.sweet.frameworks.ui.components.util.ComponentUtil;
import org.sweet.frameworks.ui.exception.RequiredAttributeException;

/**
 * 密码框类SWTPassword
 * @filename:SWTPassword
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public class SWTPassword extends SWTControl {
	public static final String COMPONENT_FAMILY="SWTPassword";

	public String getFamily(){
		return COMPONENT_FAMILY;
	}

	public void encodeAll(FacesContext context) throws IOException{
		try{
			/* validate */
			Component compo=ComponentUtil.getComponent(context,this);
			compo.validate();
			/* start password */
			Map<String,Object> attributesMap=compo.getPublicAttributesMap();
			ResponseWriter out=context.getResponseWriter();
			out.startElement("div",this);
			/* 生成前置标签 */
			if(null!=attributesMap.get("label")) {
				out.startElement("span",this);
				out.writeAttribute("class","sweet-form-control-label",null);
				out.writeAttribute("style",ComponentUtil.getDefaultLabelWidth(this),null);
				out.writeText(attributesMap.get("label")+":",null);
				out.endElement("span");
			}
			/* 生成password */
			String id=compo.getId();
			out.startElement("input",this);
			out.writeAttribute("id",id,null);
			out.writeAttribute("name",id,null);
			out.writeAttribute("type","text",null);
			out.writeAttribute("xtype",compo.getXtype(),null);
			out.writeAttribute("class",compo.getStyleClass(),null);
			out.writeAttribute("style",ComponentUtil.getStyle(attributesMap),null);
			out.writeAttribute("data-options",ComponentUtil.getDataOptions(attributesMap),null);
			out.writeAttribute("value",attributesMap.get("value"),null);
			out.writeAttribute("onclick",attributesMap.get("handler"),null);
			out.endElement("input");
			/* 生成后置标签 */
			if(null!=attributesMap.get("endLabel")) {
				out.startElement("span",this);
				out.writeAttribute("class","sweet-form-control-right-label",null);
				out.writeText(attributesMap.get("endLabel"),null);
				out.endElement("span");
			}
			/* end password */
			out.endElement("div");
		}catch(RequiredAttributeException e){
			e.printStackTrace();
		}
	}
}
