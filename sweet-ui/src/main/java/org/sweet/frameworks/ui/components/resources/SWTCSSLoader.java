package org.sweet.frameworks.ui.components.resources;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.http.HttpServletRequest;

import org.sweet.frameworks.ui.components.Component;
import org.sweet.frameworks.ui.components.util.ComponentUtil;
import org.sweet.frameworks.ui.exception.RequiredAttributeException;

/**
 * CSS加载SWTCSSLoader
 * @filename:SWTCSSLoader
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public class SWTCSSLoader extends UIComponentBase {
	public static final String COMPONENT_FAMILY="SWTCSSLoader";
	private static final String LIBRARY="library";
	private static final String HREF="href";

	@Override
	public String getFamily(){
		return COMPONENT_FAMILY;
	}

	/**
	 * 编码
	 */
	public void encodeAll(FacesContext context) throws IOException{
		try{
			/* validate */
			Component compo=ComponentUtil.getComponent(context,this);
			compo.validate();
			/* start encode */
			HttpServletRequest request=(HttpServletRequest)context.getExternalContext().getRequest();
			String relationPath=PageResourceResolver.getRelationPath(request);
			ResponseWriter out=context.getResponseWriter();
			out.startElement("link",null);
			out.writeAttribute("rel","stylesheet",null);
			out.writeAttribute("type","text/css",null);
			Map<String,Object> attributesMap=compo.getPublicAttributesMap();
			for(Map.Entry<String,Object> entry:attributesMap.entrySet()){
				String attr=entry.getKey();
				if(HREF.equals(attr)) {
					Object library=attributesMap.get(LIBRARY);
					if(null!=library) {
						out.writeAttribute(attr.toLowerCase(),relationPath+PageResourceResolver.getResourceBasePath()+library.toString()+PageResourceResolver.SEPARATOR+attributesMap.get(attr),null);
					}else{
						out.writeAttribute(attr.toLowerCase(),relationPath+attributesMap.get(attr),null);
					}
				}else{
					out.writeAttribute(attr.toLowerCase(),attributesMap.get(attr),null);
				}
			}
			out.endElement("link");
		}catch(RequiredAttributeException e){
			e.printStackTrace();
		}
	}
}
