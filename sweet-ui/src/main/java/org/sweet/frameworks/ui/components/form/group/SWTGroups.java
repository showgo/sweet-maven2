package org.sweet.frameworks.ui.components.form.group;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.sweet.frameworks.ui.components.Component;
import org.sweet.frameworks.ui.components.abstraction.SWTControl;
import org.sweet.frameworks.ui.components.util.ComponentUtil;
import org.sweet.frameworks.ui.exception.RequiredAttributeException;

/**
 * 控件组SWTGroups
 * @filename:SWTGroups
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public class SWTGroups extends SWTControl {
	public static final String COMPONENT_FAMILY="SWTGroups";

	public String getFamily(){
		return COMPONENT_FAMILY;
	}

	public void encodeAll(FacesContext context) throws IOException{
		try{
			/* validate */
			Component compo=ComponentUtil.getComponent(context,this);
			compo.validate();
			/* start groups */
			Map<String,Object> attributesMap=compo.getPublicAttributesMap();
			ResponseWriter out=context.getResponseWriter();
			out.startElement("div",this);
			out.writeAttribute("id",compo.getId(),null);
			out.writeAttribute("xtype",compo.getXtype(),null);
			out.writeAttribute("class",compo.getStyleClass(),null);
			out.writeAttribute("style","text-align: "+attributesMap.get("align")+";",null);
			/* generate groups items */
			for(UIComponent component:this.getChildren()){
				if(component instanceof SWTControl) {
					component.encodeAll(context);
				}
			}
			/* end groups */
			out.endElement("div");
		}catch(RequiredAttributeException e){
			e.printStackTrace();
		}
	}
}
