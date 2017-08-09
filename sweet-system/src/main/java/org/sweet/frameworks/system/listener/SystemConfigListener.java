package org.sweet.frameworks.system.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.sweet.frameworks.foundation.annotation.controller.RestController;
import org.sweet.frameworks.foundation.annotation.servlet.Servlet;
import org.sweet.frameworks.foundation.resource.ClassResources;
import org.sweet.frameworks.foundation.util.debug.Debug;
import org.sweet.frameworks.foundation.util.list.ListWrapper;
import org.sweet.frameworks.system.filter.ControllerModel;
import org.sweet.frameworks.system.filter.ControllerModelParser;
import org.sweet.frameworks.system.loader.AnnotationClassesLoader;
import org.sweet.frameworks.system.loader.DatabaseConfigLoader;
import org.sweet.frameworks.system.loader.MessagesLoader;
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
			// 加载controller(s)
			loadControllers(sce);
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
		List<String> classes=AnnotationClassesLoader.getClasses(servletContext,Servlet.class);
		ListWrapper<String> buffer=new ListWrapper<String>();
		for(String clazz:classes){
			Class<?> cls=ClassResources.getResourceAsClass(clazz);
			Servlet annotationInstance=cls.getAnnotation(Servlet.class);
			String annotationAttrValue=annotationInstance.value();
			if(!"".equals(annotationAttrValue)){
				classMap.put(annotationAttrValue,cls);
				buffer.push(cls.getName());
			}
		}
		servletContext.setAttribute("servletClassMap",classMap);
		Debug.info(SystemConfigListener.class,"Servlet(s): ["+buffer.join(", ")+"] ");
		Debug.info(SystemConfigListener.class,"[Load servlet(s) completed]");
	}

	/**
	 * 加载controllers
	 * @param sce
	 */
	private static void loadControllers(ServletContextEvent sce){
		servletContext=sce.getServletContext();
		Map<String,ControllerModel> classMap=new HashMap<String,ControllerModel>();
		List<String> classes=AnnotationClassesLoader.getClasses(servletContext,RestController.class);
		ListWrapper<String> buffer=new ListWrapper<String>();
		for(String clazz:classes){
			Map<String,ControllerModel> map=ControllerModelParser.newParser(clazz).parse();
			if(!map.isEmpty()){
				classMap.putAll(map);
				for(Map.Entry<String,ControllerModel> entry:map.entrySet()){
					buffer.push(entry.getKey());
				}
			}
		}
		servletContext.setAttribute("controllerClassMap",classMap);
		Debug.info(SystemConfigListener.class,"Controller(s): ["+buffer.join(", ")+"] ");
		Debug.info(SystemConfigListener.class,"[Load controller(s) completed]");
	}
}
