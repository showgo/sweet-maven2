/**
 * 
 */
package org.sweet.frameworks.system.loader;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.sweet.frameworks.foundation.resource.ResourceReader;
import org.sweet.frameworks.foundation.util.debug.Debug;

/**
 * 系统配置loader(SystemConfigLoader)
 * @filename:SystemConfigLoader
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016年5月13日
 * @modifyrecords:
 */
public abstract class SystemConfigLoader {
	protected static Map<String,String> mapper=new ConcurrentHashMap<String,String>();

	/**
	 * 加载配置
	 */
	@SuppressWarnings("unchecked")
	public static void loadConfigurations(){
		Document docu=ResourceReader.readXML(SystemResourceConfig.DEFAULT_SYSTEM_CONFIG_XML);
		if(null!=docu) {
			Element root=docu.getRootElement();
			List<Element> confs=root.elements();
			for(Element conf:confs){
				List<Attribute> attrs=conf.attributes();
				for(Attribute attr:attrs){
					mapper.put(conf.getName()+"-"+attr.getName(),attr.getStringValue());
				}
			}
			Debug.info(SystemConfigLoader.class,"[Load system configuration(s) completed]");
		}
	}
}
