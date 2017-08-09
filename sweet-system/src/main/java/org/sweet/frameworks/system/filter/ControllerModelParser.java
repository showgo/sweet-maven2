package org.sweet.frameworks.system.filter;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.sweet.frameworks.foundation.annotation.controller.RestController;
import org.sweet.frameworks.foundation.annotation.controller.RestMapping;
import org.sweet.frameworks.foundation.resource.ClassResources;

public class ControllerModelParser {
	private String controller;

	/**
	 * 构造函数
	 * @param controller
	 */
	private ControllerModelParser(String controller){
		this.controller=controller;
	}

	public static ControllerModelParser newParser(String controller){
		return new ControllerModelParser(controller);
	}

	/**
	 * 解析
	 */
	public Map<String,ControllerModel> parse(){
		Map<String,ControllerModel> map=new HashMap<String,ControllerModel>();
		Class<?> cls=ClassResources.getResourceAsClass(this.controller);
		RestController controller=cls.getAnnotation(RestController.class);
		String controllerValue=(null!=controller.value()&&!"".equals(controller.value())) ? this.prettyUri(controller.value()) : "";
		for(Method method:cls.getDeclaredMethods()){
			/* 仅允许public唯一修饰的方法通过 */
			if(Modifier.PUBLIC==method.getModifiers()){
				RestMapping mapping=method.getAnnotation(RestMapping.class);
				if(null!=mapping&&null!=mapping.value()&&!"".equals(mapping.value())){
					String uri=controllerValue+this.prettyUri(mapping.value());
					map.put(uri,new ControllerModel(cls,method.getName(),controller.allowValidated()));
				}
			}
		}
		return map;
	}

	private String prettyUri(String uri){
		if(null!=uri&&!"".equals(uri)){
			uri=uri.startsWith("/") ? uri : ("/"+uri);
			uri=!uri.endsWith("/") ? uri : uri.substring(0,uri.lastIndexOf("/"));
		}
		return uri;
	}
}
