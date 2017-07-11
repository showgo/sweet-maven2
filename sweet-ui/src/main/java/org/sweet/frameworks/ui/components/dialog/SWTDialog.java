package org.sweet.frameworks.ui.components.dialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.sweet.frameworks.ui.components.Component;
import org.sweet.frameworks.ui.components.abstraction.SWTContainer;
import org.sweet.frameworks.ui.components.resources.PageResourceResolver;
import org.sweet.frameworks.ui.components.util.ComponentUtil;
import org.sweet.frameworks.ui.exception.RequiredAttributeException;

/**
 * 对话框类SWTDialog
 * @filename:SWTDialog
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public class SWTDialog extends SWTContainer {
	public static final String COMPONENT_FAMILY="SWTDialog";

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
			String dialogId=compo.getId();
			out.startElement("div",this);
			out.writeAttribute("id",dialogId,null);
			out.writeAttribute("xtype",compo.getXtype(),null);
			out.writeAttribute("class",compo.getStyleClass(),null);
			out.writeAttribute("style",ComponentUtil.getStyle(attributesMap),null);
			/* generate url */
			Map<String,Object> extrasMap=new HashMap<String,Object>();
			if(null!=attributesMap.get("url")) {
				extrasMap.put("href","'"+PageResourceResolver.getAbsolutePath(context,attributesMap.get("url"))+"'");
			}
			List<UIComponent> buttons=this.getButtons();
			if(null!=buttons&&buttons.size()>0) {
				for(UIComponent button:buttons){
					Object position="top".equals(button.getAttributes().get("position")) ? "top" : "bottom";
					Object buttonsId=dialogId+"_"+position+"_buttons";
					button.getAttributes().put("id",buttonsId);
					if("top".equals(position)) {
						attributesMap.put("tbar",buttonsId);
					}else{
						attributesMap.put("bbar",buttonsId);
					}
				}
			}
			out.writeAttribute("data-options",ComponentUtil.getDataOptions(attributesMap,extrasMap),null);
			/* generate children */
			this.encodeChildren(context);
			out.endElement("div");
			/* end generate me */
		}catch(RequiredAttributeException e){
			e.printStackTrace();
		}
	}

	/**
	 * 获得对话框的footer buttons
	 * @return
	 */
	protected List<UIComponent> getButtons(){
		List<UIComponent> list=new ArrayList<UIComponent>();
		List<UIComponent> childrens=this.getChildren();
		for(UIComponent children:childrens){
			if(children instanceof SWTDialogButtons) {
				list.add(children);
			}
		}
		return list;
	}
}
