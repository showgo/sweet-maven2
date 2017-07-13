package org.sweet.frameworks.system.loader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.commons.io.FilenameUtils;
import org.sweet.frameworks.foundation.resource.PropertiesReader;
import org.sweet.frameworks.foundation.resource.ResourcesFilter;
import org.sweet.frameworks.foundation.resource.ResourcesWalker;
import org.sweet.frameworks.foundation.util.debug.Debug;

/**
 * 国际化资源(MessagesLoader)
 * @filename:MessagesLoader
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public class MessagesLoader extends PropertiesReader {
	/**
	 * 加载配置文件
	 */
	public static void loadMessages(ServletContextEvent sce){
		ServletContext servletContext=sce.getServletContext();
		List<String> results=new ArrayList<String>();
		try{
			ResourcesWalker walker=new ResourcesWalker(new ResourcesFilter() {
				public boolean accept(String path,String resource){
					try{
						if(resource.endsWith(".jar")){
							return FilenameUtils.getName(resource).contains("sweet");
						}else if(resource.endsWith(".properties")){
							return true;
						}
					}catch(Exception ex){
					}
					return false;
				}
			});
			walker.walks(servletContext.getRealPath("/META-INF/"),results);
			walker.walks(servletContext.getRealPath("/WEB-INF/META-INF/"),results);
			walker.walks(servletContext.getRealPath("/WEB-INF/lib/"),results);
		}catch(IOException e){
			e.printStackTrace();
		}
		for(String resource:results){
			String configurationFile=resource.substring(0,resource.indexOf(".properties"));
			loadProperties(configurationFile);
		}
		Debug.info(MessagesLoader.class,"[Load property(s) configuration(s) completed]");
	}
}
