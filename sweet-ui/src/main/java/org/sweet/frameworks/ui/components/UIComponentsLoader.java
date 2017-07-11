package org.sweet.frameworks.ui.components;

import org.sweet.frameworks.foundation.util.debug.Debug;
import org.sweet.frameworks.ui.components.UIComponentsReader;

/**
 * UIComponentsLoader
 * @filename:
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:
 * @modifyrecords:
 */
public class UIComponentsLoader extends UIComponentsReader {
	private static final String configurationFile="META-INF/sweet-ui.faces-config.xml";

	/**
	 * 加载配置
	 */
	public static void loadConfigurations(){
		loadConfigurations(configurationFile);
		Debug.info(UIComponentsLoader.class,"[Load ui components configuration(s) completed]");
	}
}