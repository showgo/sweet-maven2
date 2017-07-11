package org.sweet.frameworks.ui.components.form.label;

import java.io.IOException;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.sweet.frameworks.ui.components.Component;
import org.sweet.frameworks.ui.components.abstraction.SWTControl;
import org.sweet.frameworks.ui.components.util.ComponentUtil;
import org.sweet.frameworks.ui.exception.RequiredAttributeException;

/**
 * 文本标签类SWTLabel
 * @filename:SWTLabel
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public class SWTLabel extends SWTControl {
	public static final String COMPONENT_FAMILY="SWTLabel";

	public String getFamily(){
		return COMPONENT_FAMILY;
	}

	public void encodeAll(FacesContext context) throws IOException{
		try{
			/* validate */
			Component compo=ComponentUtil.getComponent(context,this);
			compo.validate();
			/* start label */
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
			/* 生成label */
			out.startElement("span",this);
			out.writeAttribute("xtype",compo.getXtype(),null);
			out.writeAttribute("class",compo.getStyleClass(),null);
			out.writeAttribute("title",attributesMap.get("text"),null);
			out.writeText(null!=attributesMap.get("text") ? attributesMap.get("text") : "",null);
			out.endElement("span");
			/* 生成后置标签 */
			if(null!=attributesMap.get("endLabel")) {
				out.startElement("span",this);
				out.writeAttribute("class","sweet-form-control-right-label",null);
				out.writeText(attributesMap.get("endLabel"),null);
				out.endElement("span");
			}
			/* end label */
			out.endElement("div");
		}catch(RequiredAttributeException e){
			e.printStackTrace();
		}
	}
}
