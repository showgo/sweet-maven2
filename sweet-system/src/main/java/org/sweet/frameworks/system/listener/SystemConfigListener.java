package org.sweet.frameworks.system.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.sweet.frameworks.foundation.annotation.servlet.Servlet;
import org.sweet.frameworks.foundation.resource.ServletClassResources;
import org.sweet.frameworks.foundation.util.debug.Debug;
import org.sweet.frameworks.system.loader.DatabaseConfigLoader;
import org.sweet.frameworks.system.loader.MessagesLoader;
import org.sweet.frameworks.system.loader.ServletResourceLoader;
import org.sweet.frameworks.system.loader.SystemConfigLoader;
import org.sweet.frameworks.ui.components.UIComponentsLoader;

/**
 * 系统配置监听器(SystemConfigListener)
 * @filename:SystemConfigListener
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public final class SystemConfigListener extends HttpServlet implements ServletContextListener {
	private static final long serialVersionUID=-1099351217105907930L;
	private static ServletContext servletContext=null;

	public void contextDestroyed(ServletContextEvent sce){
	}

	public void contextInitialized(ServletContextEvent sce){
		try{
			// load database connection pool configurations
			DatabaseConfigLoader.loadConfigurations();
			// load system configurations
			SystemConfigLoader.loadConfigurations();
			// 加载系统messages
			MessagesLoader.loadMessages(sce);
			// 加载自定义标签配置
			UIComponentsLoader.loadConfigurations();
			// 加载servlet(s)
			loadServlets(sce);
			// 系统资源加载完成
			Debug.info(SystemConfigListener.class,"[Load system resource(s) successfully]");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 加载servlets
	 * @param sce
	 */
	private static void loadServlets(ServletContextEvent sce){
		servletContext=sce.getServletContext();
		Map<String,Class<?>> classMap=new HashMap<String,Class<?>>();
		List<String> setClasses=ServletResourceLoader.getServletClasses(servletContext);
		StringBuilder buffer=new StringBuilder();
		for(String clazz:setClasses){
			Class<?> cls=ServletClassResources.getResourceAsClass(clazz);
			Servlet annotationInstance=cls.getAnnotation(Servlet.class);
			String annotationAttrValue=annotationInstance.name();
			if(!"".equals(annotationAttrValue)){
				classMap.put(annotationAttrValue,cls);
				buffer.append(cls.getName());
				buffer.append(", ");
			}
		}
		servletContext.setAttribute("servletClassMap",classMap);
		Debug.info(SystemConfigListener.class,"Servlet(s): ["+buffer.substring(0,buffer.lastIndexOf(","))+"] ");
		Debug.info(SystemConfigListener.class,"[Load servlet(s) completed]");
	}
}
