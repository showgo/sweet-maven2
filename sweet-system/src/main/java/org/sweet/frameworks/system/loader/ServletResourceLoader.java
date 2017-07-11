package org.sweet.frameworks.system.loader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;
import org.sweet.frameworks.foundation.annotation.servlet.Servlet;
import org.sweet.frameworks.foundation.resource.ClassResources;
import org.sweet.frameworks.foundation.resource.ResourcesFilter;
import org.sweet.frameworks.foundation.resource.ResourcesWalker;

/**
 * servlet资源类加载
 * @filename:ServletResourceLoader
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public final class ServletResourceLoader {
	private static ServletResourceLoader loader=null;

	static{
		loader=new ServletResourceLoader();
	}

	/**
	 * 返回servlet类集
	 * @return
	 */
	protected List<String> listClasses(ServletContext servletContext){
		List<String> results=new ArrayList<String>();
		try{
			ResourcesWalker walker=new ResourcesWalker(new ResourcesFilter() {
				public boolean accept(String path,String resource){
					try{
						if(resource.endsWith(".jar")) {
							return FilenameUtils.getName(resource).contains("sweet");
						}else if(resource.endsWith(".class")) {
							Class<?> clazz=ClassResources.getResourceAsClass(path,resource);
							if(null!=clazz&&clazz.isAnnotationPresent(Servlet.class)) {
								return true;
							}
						}
					}catch(Exception ex){
					}
					return false;
				}
			});
			walker.walks(servletContext.getRealPath("/WEB-INF/lib/"),results);
			walker.walks(servletContext.getRealPath("/WEB-INF/classes/"),results);
		}catch(IOException e){
			e.printStackTrace();
		}
		return results;
	}

	/**
	 * 返回servlet类集
	 * @return
	 */
	public static List<String> getServletClasses(ServletContext servletContext){
		return loader.listClasses(servletContext);
	}
}
