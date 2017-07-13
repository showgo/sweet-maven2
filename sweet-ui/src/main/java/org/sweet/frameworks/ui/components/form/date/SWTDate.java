package org.sweet.frameworks.ui.components.form.date;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.sweet.frameworks.foundation.message.Messages;
import org.sweet.frameworks.ui.components.Component;
import org.sweet.frameworks.ui.components.abstraction.SWTControl;
import org.sweet.frameworks.ui.components.util.ComponentUtil;
import org.sweet.frameworks.ui.exception.RequiredAttributeException;

/**
 * 日期类SWTDate
 * @filename:SWTDate
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public class SWTDate extends SWTControl {
	public static final String COMPONENT_FAMILY="SWTDate";

	public String getFamily(){
		return COMPONENT_FAMILY;
	}

	public void encodeAll(FacesContext context) throws IOException{
		try{
			/* validate */
			Component compo=ComponentUtil.getComponent(context,this);
			compo.validate();
			/* start date */
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
			/* 生成date */
			String id=compo.getId();
			out.startElement("input",this);
			out.writeAttribute("id",id,null);
			out.writeAttribute("name",id,null);
			out.writeAttribute("type","text",null);
			out.writeAttribute("xtype",compo.getXtype(),null);
			out.writeAttribute("class",compo.getStyleClass(),null);
			out.writeAttribute("style",ComponentUtil.getStyle(attributesMap),null);
			/* extrasMap */
			Map<String,Object> extrasMap=new HashMap<String,Object>();
			extrasMap.put("currentText","'"+Messages.getDefault("current")+"'");
			extrasMap.put("closeText","'"+Messages.getDefault("close")+"'");
			extrasMap.put("okText","'"+Messages.getDefault("ok")+"'");
			/* formatter,parser */
			String format=attributesMap.get("format").toString();
			String token=format.indexOf("-")!=-1 ? "-" : "/";
			extrasMap.put("formatter","function(date){var y=date.getFullYear();var m=date.getMonth()+1;var d=date.getDate();return y+'"+token+"'+(m<10?('0'+m):m)+'"+token+"'+(d<10?('0'+d):d)}");
			extrasMap.put("parser","function(date){if(!date){return new Date()}var ds=(date.split('"+token+"'));var y=parseInt(ds[0],10);var m=parseInt(ds[1],10);var d=parseInt(ds[2],10);if(!isNaN(y)&&!isNaN(m)&&!isNaN(d)){return new Date(y,m-1,d)}else{return new Date()}}");
			out.writeAttribute("data-options",ComponentUtil.getDataOptions(attributesMap,extrasMap),null);
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
			/* end date */
			out.endElement("div");
		}catch(RequiredAttributeException e){
			e.printStackTrace();
		}
	}
}
