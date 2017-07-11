package org.sweet.frameworks.ui.components.grid;

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
 * 表格列SWTGridColumn
 * @filename:SWTGridColumn
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public class SWTGridColumn extends SWTControl {
	public static final String COMPONENT_FAMILY="SWTGridColumn";

	@Override
	public String getFamily(){
		return COMPONENT_FAMILY;
	}

	/**
	 * 
	 */
	public void encodeAll(FacesContext context) throws IOException{
		try{
			/* validate */
			Component compo=ComponentUtil.getComponent(context,this);
			compo.validate();
			ResponseWriter out=context.getResponseWriter();
			out.startElement("th",this);
			/* 属性 */
			Map<String,Object> attributesMap=compo.getPublicAttributesMap();
			/* extrasMap */
			Map<String,Object> extrasMap=new HashMap<String,Object>();
			extrasMap.put("field","'"+attributesMap.get("name")+"'");
			extrasMap.put("align","'"+attributesMap.get("align")+"'");
			extrasMap.put("width",attributesMap.get("width"));
			/* 隐藏该列 */
			extrasMap.put("hidden",attributesMap.get("hidden"));
			out.writeAttribute("data-options",ComponentUtil.getDataOptions(null,extrasMap),null);
			out.writeText(attributesMap.get("title"),null);
			out.endElement("th");
		}catch(RequiredAttributeException e){
			e.printStackTrace();
		}
	}
}
