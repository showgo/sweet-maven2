package org.sweet.frameworks.system.filter;

import java.lang.reflect.Method;
import java.util.Map;

public class ControllerModel {
	private Class<?> controller=null;
	private Method method=null;
	private String requestUri=null;

	/**
	 * 构造函数
	 * @param controller
	 * @param method
	 * @param requestUri
	 */
	public ControllerModel(Class<?> controller,String method,String requestUri){
		this.controller=controller;
		try{
			this.method=controller.getDeclaredMethod(method,Map.class);
		}catch(Exception e){
			e.printStackTrace();
			/* 如果异常,则做无参处理 */
			try{
				this.method=controller.getMethod(method);
			}catch(NoSuchMethodException e1){
				e1.printStackTrace();
			}catch(SecurityException e1){
				e1.printStackTrace();
			}
		}
		this.requestUri=requestUri;
	}

	public Class<?> getController(){
		return controller;
	}

	public Method getMethod(){
		return method;
	}

	public String getRequestUri(){
		return requestUri;
	}

	public void setRequestUri(String requestUri){
		this.requestUri=requestUri;
	}
}
