package org.sweet.frameworks.ui.message;

import org.sweet.frameworks.foundation.resource.PropertiesReader;
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
	private static final String configurationFile="META-INF/messages";

	/**
	 * 加载配置文件
	 */
	public static void loadMessages(){
		loadProperties(configurationFile);
		Debug.info(MessagesLoader.class,"[Load property(s) configuration(s) completed]");
	}
}
