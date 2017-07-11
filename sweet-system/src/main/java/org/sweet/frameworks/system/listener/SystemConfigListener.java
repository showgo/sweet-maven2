package org.sweet.frameworks.system.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.sweet.frameworks.foundation.util.debug.Debug;
import org.sweet.frameworks.system.loader.DatabaseConfigLoader;
import org.sweet.frameworks.system.loader.SystemConfigLoader;
import org.sweet.frameworks.ui.components.UIComponentsLoader;
import org.sweet.frameworks.ui.message.MessagesLoader;

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

	public void contextDestroyed(ServletContextEvent sce){
	}

	public void contextInitialized(ServletContextEvent sce){
		try{
			// load database connection pool configurations
			DatabaseConfigLoader.loadConfigurations();
			// load system configurations
			SystemConfigLoader.loadConfigurations();
			// 加载系统messages
			MessagesLoader.loadMessages();
			// 加载自定义标签配置
			UIComponentsLoader.loadConfigurations();
			Debug.info(SystemConfigListener.class,"[Load system resource(s) successfully]");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
