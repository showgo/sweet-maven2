package org.sweet.frameworks.foundation.resource;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sweet.frameworks.foundation.util.string.StringUtil;

/**
 * PropertiesReader
 * @filename:PropertiesReader
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:
 * @modifyrecords:
 */
public class PropertiesReader {
	protected static final String BINDING_PARAMS_REGEX="\\{\\d\\}";
	protected static final Pattern BINDING_PATTERN=Pattern.compile(BINDING_PARAMS_REGEX);
	protected static Map<String,Object> resourceBundles=new ConcurrentHashMap<String,Object>();

	/**
	 * 加载配置文件
	 * @param configurationFile 配置文件
	 */
	protected static void loadProperties(String configurationFile){
		ResourceBundle resourceBundle=ResourceBundle.getBundle(configurationFile,new Locale("zh","CN"));
		for(String key:resourceBundle.keySet()){
			resourceBundles.put(key,resourceBundle.getObject(key));
		}
	}

	/**
	 * 获得配置值
	 * @param key 配置key
	 * @return 配置值
	 */
	private static Object get(String key){
		if(resourceBundles.containsKey(key)){
			return resourceBundles.get(key);
		}
		return null;
	}

	/**
	 * 获得消息配置字串
	 * @param key 配置键名(全名)
	 * @param parameters 绑定参数数组
	 * @return
	 */
	public static Object get(String key,Object...parameters){
		if(StringUtil.isNotEmpty(key)){
			Object value=get(key);
			if(null==parameters){
				return value;
			}
			String string=null!=value ? value.toString() : "";
			Matcher mat=BINDING_PATTERN.matcher(string);
			while(mat.find()){
				String group=mat.group();
				String index=group.substring(1,group.length()-1);
				Object param=null;
				try{
					param=parameters[Integer.valueOf(index).intValue()];
				}catch(Exception e){
				}
				if(null!=param){
					string=string.replaceAll("\\{"+index+"\\}",param.toString());
				}
			}
			return string;
		}
		return null;
	}

	/**
	 * 获得消息配置字串
	 * @param clazz
	 * @param key 配置键名(短键名称,通过和clazz的全类名拼接获得完整的配置名称后再获取配置信息)
	 * @param parameters 绑定参数数组
	 * @return
	 */
	public static Object get(Class<?> clazz,String key,Object...parameters){
		String k=null!=clazz ? (clazz.getName()+"."+key) : key;
		return get(k,parameters);
	}
}
