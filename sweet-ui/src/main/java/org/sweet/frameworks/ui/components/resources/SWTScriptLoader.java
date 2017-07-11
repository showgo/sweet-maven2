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
 * 脚本资源加载SWTScriptLoader
 * @filename:SWTScriptLoader
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public class SWTScriptLoader extends UIComponentBase {
	public static final String COMPONENT_FAMILY="SWTScriptLoader";
	private static final String LIBRARY="library";
	private static final String HREF="href";

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
			out.startElement("script",null);
			out.writeAttribute("type","text/javascript",null);
			Map<String,Object> attributesMap=compo.getPublicAttributesMap();
			for(Map.Entry<String,Object> entry:attributesMap.entrySet()){
				String attr=entry.getKey();
				if(HREF.equals(attr)) {
					Object library=attributesMap.get(LIBRARY);
					if(null!=library) {
						out.writeAttribute("src",relationPath+PageResourceResolver.getResourceBasePath()+library.toString()+PageResourceResolver.SEPARATOR+attributesMap.get(attr),null);
					}else{
						out.writeAttribute("src",relationPath+attributesMap.get(attr),null);
					}
				}else{
					out.writeAttribute(attr.toLowerCase(),attributesMap.get(attr),null);
				}
			}
			/* 不包含href属性,则解析为脚本定义 */
			if(!attributesMap.containsKey("href")) {
				this.encodeChildren(context);
			}
			out.endElement("script");
		}catch(RequiredAttributeException e){
			e.printStackTrace();
		}
	}

	public String getFamily(){
		return COMPONENT_FAMILY;
	}
}
