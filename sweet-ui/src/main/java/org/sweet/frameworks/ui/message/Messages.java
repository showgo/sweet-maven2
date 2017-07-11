package org.sweet.frameworks.ui.message;

import org.sweet.frameworks.foundation.resource.PropertiesReader;

/**
 * Messages
 * @filename:Messages
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2017年4月8日
 * @modifyrecords:
 */
public final class Messages extends PropertiesReader {
	/**
	 * 获得系统默认消息配置字串
	 * @param key 配置键名(短键名称,通过和Messages类的全类名拼接获得完整的配置名称后再获取配置信息)
	 * @param parameters 绑定参数数组
	 * @return
	 */
	public static Object getDefault(String key,Object...parameters){
		return get(Messages.class,key,parameters);
	}

	public static final String RETCODE="retcode";
	public static final String RETMESG="retmesg";
	public static final String SUCCESS="1";
	public static final String FAILURE="0";
}
