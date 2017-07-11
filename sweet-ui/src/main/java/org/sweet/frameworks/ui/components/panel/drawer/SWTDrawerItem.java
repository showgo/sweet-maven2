package org.sweet.frameworks.ui.components.panel.drawer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.sweet.frameworks.ui.components.Component;
import org.sweet.frameworks.ui.components.abstraction.SWTControl;
import org.sweet.frameworks.ui.components.util.ComponentUtil;
import org.sweet.frameworks.ui.exception.RequiredAttributeException;

/**
 * 抽屉式panel的item
 * @filename:SWTDrawerItem
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public class SWTDrawerItem extends SWTControl {
	public static final String COMPONENT_FAMILY="SWTDrawerItem";

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
			Map<String,Object> attributesMap=compo.getPublicAttributesMap();
			ResponseWriter out=context.getResponseWriter();
			out.startElement("div",this);
			out.writeAttribute("id",compo.getId(),null);
			out.writeAttribute("xtype",compo.getXtype(),null);
			out.writeAttribute("border",attributesMap.get("border"),null);
			/* extrasMap */
			Map<String,Object> extrasMap=new HashMap<String,Object>();
			extrasMap.put("selected",this.getAttributes().get("selected"));
			out.writeAttribute("data-options",ComponentUtil.getDataOptions(attributesMap,extrasMap),null);
			/* generate children */
			this.encodeChildren(context);
			out.endElement("div");
		}catch(RequiredAttributeException e){
			e.printStackTrace();
		}
	}
}
