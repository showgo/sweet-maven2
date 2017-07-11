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
 * 按钮类SWTSwitchButton
 * @filename:SWTSwitchButton
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public class SWTSwitchButton extends SWTControl {
	public static final String COMPONENT_FAMILY="SWTSwitchButton";

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
			for(Map.Entry<String,Object> entry:compo.getPublicAttributesMap().entrySet()){
				out.writeAttribute(entry.getKey(),entry.getValue(),null);
			}
			out.writeAttribute("data-options",ComponentUtil.getDataOptions(attributesMap),null);
			out.writeAttribute("href","#",null);
			out.writeAttribute("title",attributesMap.get("title"),null);
			/* end button */
			out.endElement("a");
		}catch(RequiredAttributeException e){
			e.printStackTrace();
		}
	}
}
