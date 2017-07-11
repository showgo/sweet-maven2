/**
 * 
 */
package org.sweet.frameworks.ui.components.form.combobox;

import java.io.IOException;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.sweet.frameworks.ui.components.Component;
import org.sweet.frameworks.ui.components.abstraction.SWTControl;
import org.sweet.frameworks.ui.components.util.ComponentUtil;
import org.sweet.frameworks.ui.exception.RequiredAttributeException;

/**
 * (SWTComboboxOption)
 * @filename:SWTComboboxOption
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2017年3月13日
 * @modifyrecords:
 */
public class SWTComboboxOption extends SWTControl {
	public static final String COMPONENT_FAMILY="SWTComboboxOption";

	public String getFamily(){
		return COMPONENT_FAMILY;
	}

	public void encodeAll(FacesContext context) throws IOException{
		try{
			/* validate */
			Component compo=ComponentUtil.getComponent(context,this);
			compo.validate();
			/* start option */
			Map<String,Object> attributesMap=compo.getPublicAttributesMap();
			ResponseWriter out=context.getResponseWriter();
			out.startElement("option",this);
			if(null!=attributesMap.get("value")) {
				out.writeAttribute("value",attributesMap.get("value"),null);
			}
			if(null!=attributesMap.get("text")) {
				out.writeText(attributesMap.get("text"),null);
			}
			/* end option */
			out.endElement("option");
		}catch(RequiredAttributeException e){
			e.printStackTrace();
		}
	}
}
