package org.sweet.frameworks.ui.components.form;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.sweet.frameworks.ui.components.Component;
import org.sweet.frameworks.ui.components.abstraction.SWTContainer;
import org.sweet.frameworks.ui.components.util.ComponentUtil;
import org.sweet.frameworks.ui.components.util.FormLayoutUtil;
import org.sweet.frameworks.ui.exception.LayoutException;
import org.sweet.frameworks.ui.exception.RequiredAttributeException;

/**
 * 页面Form(SWTForm)
 * @filename:SWTForm
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public class SWTForm extends SWTContainer {
	public static final String COMPONENT_FAMILY="SWTForm";

	@Override
	public String getFamily(){
		return COMPONENT_FAMILY;
	}

	public void encodeAll(FacesContext context) throws IOException{
		try{
			/* validate */
			Component compo=ComponentUtil.getComponent(context,this);
			compo.validate();
			this.setAttribute("id",compo.getId());
			this.setAttribute("xtype",compo.getXtype());
			/* generate me */
			Map<String,Object> attributesMap=compo.getPublicAttributesMap();
			ResponseWriter out=context.getResponseWriter();
			/* define panel */
			String id=compo.getId();
			out.startElement("div",this);
			out.writeAttribute("id",id,null);
			out.writeAttribute("xtype",compo.getXtype(),null);
			out.writeAttribute("border",attributesMap.get("border"),null);
			out.writeAttribute("class",compo.getStyleClass(),null);
			out.writeAttribute("style",ComponentUtil.getStyle(attributesMap),null);
			out.writeAttribute("data-options",ComponentUtil.getDataOptions(attributesMap),null);
			if("center".equals(attributesMap.get("location"))) {
				out.writeAttribute("fit","true",null);
			}
			/* generate tbar */
			UIComponent tbar=this.getTopBar();
			if(null!=tbar) {
				tbar.encodeAll(context);
			}
			/* generate form body */
			out.startElement("form",this);
			out.writeAttribute("id",id+"_form",null);
			out.writeAttribute("action","",null);
			out.writeAttribute("enctype","multipart/form-data",null);
			out.writeAttribute("method","post",null);
			FormLayoutUtil.encodeChildren(context,this,attributesMap);
			out.endElement("form");
			/* generate bbar */
			UIComponent bbar=this.getBottomBar();
			if(null!=bbar) {
				bbar.encodeAll(context);
			}
			out.endElement("div");
		}catch(RequiredAttributeException e){
			e.printStackTrace();
		}catch(LayoutException e){
			e.printStackTrace();
		}
	}
}
