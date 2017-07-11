package org.sweet.frameworks.foundation.util.map;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * MapUtil
 * @filename:MapUtil
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public final class MapUtil {
	/**
	 * 从request中获得参数Map，并返回可读的Map
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String,Object> getParameterMap(HttpServletRequest request){
		Map properties=request.getParameterMap();
		Map<String,Object> returnMap=new HashMap<String,Object>();
		Iterator entries=properties.entrySet().iterator();
		String name="";
		String value="";
		while(entries.hasNext()){
			Map.Entry entry=(Map.Entry)entries.next();
			name=(String)entry.getKey();
			Object valueObj=entry.getValue();
			if(null==valueObj){
				value="";
			}else if(valueObj instanceof String[]){
				String[] values=(String[])valueObj;
				for(int i=0;i<values.length;i++){
					value=values[i]+",";
				}
				value=value.substring(0,value.length()-1);
			}else{
				value=valueObj.toString();
			}
			returnMap.put(name,value);
		}
		return returnMap;
	}

	/**
	 * 将(逗号分割的)字符串转Map
	 * @param params
	 * @return
	 */
	public static Map<String,Object> getParamsMap(String params){
		Map<String,Object> returnMap=new HashMap<String,Object>();
		if(null!=params&&params.contains(",")){
			String paramsString[]=params.split(",");
			for(int e=0;e<paramsString.length;e+=2){
				if((e+1)<paramsString.length){
					returnMap.put(paramsString[e],paramsString[e+1]);
				}else{
					returnMap.put(paramsString[e],null);
				}
			}
		}
		return returnMap;
	}

	/**
	 * 将url键值对转换成Map
	 * @param url
	 * @return
	 */
	public static Map<String,Object> fromURL(String url){
		Map<String,Object> returnMap=new HashMap<String,Object>();
		if(null!=url){
			String[] urls=url.split("[\\{\\?&\\}]");
			for(int e=0;e<urls.length;e++){
				if(null!=urls[e]&&!"".equals(urls[e])){
					String segments[]=urls[e].split("=");
					if(segments.length==2){
						returnMap.put(segments[0],segments[1]);
					}else{
						returnMap.put(segments[0],null);
					}
				}
			}
		}
		return returnMap;
	}

	/**
	 * 将bean包装成map
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> wrap(Object bean) throws Exception{
		if(null==bean){
			return null;
		}
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			BeanInfo beanInfo=Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] propertyDescriptors=beanInfo.getPropertyDescriptors();
			for(PropertyDescriptor property:propertyDescriptors){
				String key=property.getName();
				/* 过滤class属性 */
				if(!key.equals("class")){
					/* 得到property对应的getter方法 */
					Method getter=property.getReadMethod();
					Object value=getter.invoke(bean);
					map.put(key,value);
				}
			}
		}catch(Exception e){
			throw new Exception(e.getCause());
		}
		return map;
	}
}
