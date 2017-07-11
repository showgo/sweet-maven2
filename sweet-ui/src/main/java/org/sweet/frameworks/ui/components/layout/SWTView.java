package org.sweet.frameworks.ui.components.layout;

import java.io.IOException;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.sweet.frameworks.foundation.util.map.MapUtil;
import org.sweet.frameworks.ui.components.Component;
import org.sweet.frameworks.ui.components.abstraction.SWTContainer;
import org.sweet.frameworks.ui.components.resources.PageResourceResolver;
import org.sweet.frameworks.ui.components.util.ComponentUtil;
import org.sweet.frameworks.ui.components.util.DocumentUtil;
import org.sweet.frameworks.ui.exception.RequiredAttributeException;
import org.sweet.frameworks.ui.message.Messages;

/**
 * 页面布局类SWTView
 * @filename:SWTView
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public class SWTView extends SWTContainer {
	public static final String COMPONENT_FAMILY="SWTView";

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
			/* initialize view map */
			DocumentUtil.initViewMap(this);
			/* generate me */
			ResponseWriter out=context.getResponseWriter();
			/* generate mask */
			this.encodeMask(context);
			/* end mask */
			/* encode view */
			out.startElement("div",this);
			for(Map.Entry<String,Object> entry:compo.getProtectedAttributesMap().entrySet()){
				out.writeAttribute(entry.getKey(),entry.getValue(),null);
			}
			out.writeAttribute("data-options",ComponentUtil.getDataOptions(compo.getPublicAttributesMap()),null);
			/* generate children */
			this.encodeChildren(context);
			out.endElement("div");
			/* get parameters */
			Map<String,Object> params=ComponentUtil.getParameters(context);
			if(params.containsKey("_swtui_page_messages_")) {
				Object msg=params.get("_swtui_page_messages_");
				Map<String,Object> map=MapUtil.getParamsMap(null!=msg ? msg.toString() : null);
				DocumentUtil.addScript(this,"MessageBox.alert(\""+map.get(Messages.RETCODE)+"\",\""+map.get(Messages.RETMESG)+"\");");
			}
			/* write pages ready script */
			DocumentUtil.writeScriptOnReady(context,this);
		}catch(RequiredAttributeException e){
			e.printStackTrace();
		}
	}

	private void encodeMask(FacesContext context) throws IOException{
		ResponseWriter out=context.getResponseWriter();
		/* generate mask */
		out.startElement("div",this);
		out.writeAttribute("id","org_sweet_mask",null);
		out.writeAttribute("xtype","mask",null);
		out.writeAttribute("style","position:absolute;z-index:10000;top:0px;left:0px;width:100%;height:100%;background:#fcffff;text-align:center;padding-top:20%;",null);
		out.writeAttribute("fit","true",null);
		out.startElement("span",this);
		out.writeAttribute("style","height:16px;line-height:16px;",null);
		out.startElement("img",this);
		out.writeAttribute("src",PageResourceResolver.getAbsolutePath(context,"WEB-UI/frameworks/sweet-ui-v2.0/images/default/loading.gif"),null);
		out.endElement("img");
		out.endElement("span");
		out.startElement("span",this);
		out.writeAttribute("style","height:16px;line-height:16px;color:#15428B;",null);
		out.writeText(Messages.getDefault("pageLoading"),null);
		out.endElement("span");
		out.endElement("div");
		/* end mask */
	}
}
