package org.sweet.frameworks.ui.components.resources;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * PageResourceResolver
 * @filename:PageResourceResolver
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public final class PageResourceResolver {
	public static final String SEPARATOR="/";
	private static final String CURR_PATH="./";
	private static final String LAST_PATH="../";
	private static final String RESOURCE_BASE_PATH="WEB-UI/frameworks/sweet-ui-v2.0/";

	/**
	 * 获得项目上下文路径(基础路径)
	 * @param request
	 * @return
	 */
	public static String getContextPath(HttpServletRequest request){
		return request.getContextPath();
	}

	/**
	 * 获得资源基础路径
	 * @return
	 */
	public static String getResourceBasePath(){
		return RESOURCE_BASE_PATH;
	}

	/**
	 * 获取相对路径
	 * @param request
	 * @return
	 */
	public static String getRelationPath0(HttpServletRequest request){
		String retPath="";
		String realPath=request.getRequestURI();
		String[] realPaths=realPath.split("/");
		boolean isContextPath=true;
		for(int i=1;i<realPaths.length-1;i++){
			if(isContextPath){
				retPath+=CURR_PATH;
				isContextPath=false;
			}else{
				retPath+=LAST_PATH;
			}
		}
		return retPath;
	}

	/**
	 * 获取相对路径
	 * @param request
	 * @return
	 */
	public static String getRelationPath(HttpServletRequest request){
		return CURR_PATH;
	}

	/**
	 * 获得绝对路径
	 * @param context
	 * @param url
	 * @return
	 */
	public static String getAbsolutePath(FacesContext context,Object url){
		HttpServletRequest request=(HttpServletRequest)context.getExternalContext().getRequest();
		String relationPath=getRelationPath(request);
		return relationPath+url;
	}
}
