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
 * 隐藏文本框类SWTHidden
 * @filename:SWTHidden
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public class SWTHidden extends SWTControl {
	public static final String COMPONENT_FAMILY="SWTHidden";

	public String getFamily(){
		return COMPONENT_FAMILY;
	}

	public void encodeAll(FacesContext context) throws IOException{
		try{
			/* validate */
			Component compo=ComponentUtil.getComponent(context,this);
			compo.validate();
			/* start text */
			Map<String,Object> attributesMap=compo.getPublicAttributesMap();
			ResponseWriter out=context.getResponseWriter();
			out.startElement("div",this);
			out.writeAttribute("style","display: none;",null);
			/* 生成hidden */
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
			/* end hidden */
			out.endElement("div");
		}catch(RequiredAttributeException e){
			e.printStackTrace();
		}
	}
}
