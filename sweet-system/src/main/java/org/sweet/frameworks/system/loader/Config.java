package org.sweet.frameworks.system.loader;

/**
 * Config
 * @filename:Config
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2017年3月9日
 * @modifyrecords:
 */
public final class Config extends SystemConfigLoader {
	/**
	 * 获得配置
	 * @param conf
	 * @return
	 */
	public static String getConfig(String conf){
		return mapper.get(conf);
	}
}
