package org.sweet.frameworks.ui.components.form.combobox;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.sweet.frameworks.foundation.util.json.JSONUtil;
import org.sweet.frameworks.ui.components.Component;
import org.sweet.frameworks.ui.components.abstraction.SWTControl;
import org.sweet.frameworks.ui.components.util.ComponentUtil;
import org.sweet.frameworks.ui.exception.RequiredAttributeException;
import org.sweet.frameworks.ui.resources.URLParameter;

/**
 * combo框类SWTCombobox
 * @filename:SWTCombobox
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public class SWTCombobox extends SWTControl {
	public static final String COMPONENT_FAMILY="SWTCombobox";

	public String getFamily(){
		return COMPONENT_FAMILY;
	}

	public void encodeAll(FacesContext context) throws IOException{
		try{
			/* validate */
			Component compo=ComponentUtil.getComponent(context,this);
			compo.validate();
			/* start Combo */
			Map<String,Object> attributesMap=compo.getPublicAttributesMap();
			ResponseWriter out=context.getResponseWriter();
			out.startElement("div",this);
			/* 生成前置标签 */
			if(null!=attributesMap.get("label")){
				out.startElement("span",this);
				out.writeAttribute("class","sweet-form-control-label",null);
				out.writeAttribute("style",ComponentUtil.getDefaultLabelWidth(this),null);
				out.writeText(attributesMap.get("label")+":",null);
				out.endElement("span");
			}
			/* 生成Combo */
			String id=compo.getId();
			Map<String,Object> sourceMap=null!=attributesMap.get("source") ? JSONUtil.toMap(attributesMap.get("source").toString()) : new HashMap<String,Object>();
			if(null!=sourceMap&&sourceMap.size()>0){
				out.startElement("input",this);
				out.writeAttribute("id",id,null);
				out.writeAttribute("name",id,null);
				out.writeAttribute("xtype",compo.getXtype(),null);
				out.writeAttribute("class",compo.getStyleClass(),null);
				out.writeAttribute("style",ComponentUtil.getStyle(attributesMap),null);
				/* extrasMap */
				Map<String,Object> extrasMap=new HashMap<String,Object>();
				extrasMap.put("valueField","'"+attributesMap.get("valueField")+"'");
				extrasMap.put("textField","'"+attributesMap.get("textField")+"'");
				int count=0;
				StringBuilder urlBuffer=new StringBuilder();
				urlBuffer.append("./servlet/combo.do?");
				sourceMap.put(URLParameter.MODEL_,"id,"+attributesMap.get("valueField")+",text,"+attributesMap.get("textField"));
				for(Map.Entry<String,Object> entry:sourceMap.entrySet()){
					if(count>0){
						urlBuffer.append("&");
					}
					urlBuffer.append(entry.getKey()).append("=").append(entry.getValue());
					count++;
				}
				extrasMap.put("url","'"+urlBuffer.toString()+"'");
				out.writeAttribute("data-options",ComponentUtil.getDataOptions(attributesMap,extrasMap),null);
				out.writeAttribute("value",attributesMap.get("value"),null);
				out.endElement("input");
			}else{
				out.startElement("select",this);
				out.writeAttribute("id",id,null);
				out.writeAttribute("name",id,null);
				out.writeAttribute("xtype",compo.getXtype(),null);
				out.writeAttribute("class",compo.getStyleClass(),null);
				out.writeAttribute("style",ComponentUtil.getStyle(attributesMap),null);
				out.writeAttribute("data-options",ComponentUtil.getDataOptions(attributesMap),null);
				out.writeAttribute("value",attributesMap.get("value"),null);
				this.encodeChildren(context);
				out.endElement("select");
			}
			/* 生成后置标签 */
			if(null!=attributesMap.get("endLabel")){
				out.startElement("span",this);
				out.writeAttribute("class","sweet-form-control-right-label",null);
				out.writeText(attributesMap.get("endLabel"),null);
				out.endElement("span");
			}
			/* end Combo */
			out.endElement("div");
		}catch(RequiredAttributeException e){
			e.printStackTrace();
		}
	}
}
