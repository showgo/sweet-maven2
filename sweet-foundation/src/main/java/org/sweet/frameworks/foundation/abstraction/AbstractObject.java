package org.sweet.frameworks.foundation.abstraction;

import java.io.Serializable;

/**
 * 抽象对象
 * @filename:AbstractObject
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public abstract class AbstractObject implements Serializable {
	private static final long serialVersionUID=-477799356104463791L;

	/**
	 * 判断对象是否为空
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj){
		/*
		 * (1)JAVA集合框架:Collection; (2)JAVA集合框架:Map; (3)JAVA的String类型; (4)其他JAVA对象; (5)obj为null;
		 */
		if(null!=obj) {
			if(obj instanceof java.util.Collection) {
				return ((java.util.Collection)obj).isEmpty();
			}else if(obj instanceof java.util.Map) {
				return ((java.util.Map)obj).isEmpty();
			}else if(obj instanceof java.lang.String) {
				return "".equals(obj) ? true : false;
			}else{
				return false;
			}
		}else{
			return true;
		}
	}

	/**
	 * 对象不为空
	 * @param obj
	 * @return
	 */
	public static boolean isNotEmpty(Object obj){
		return !isEmpty(obj);
	}

	/**
	 * 返回对象的(实例)类型字串
	 * @param obj
	 * @return
	 */
	public static String type(Object obj){
		return isEmpty(obj) ? "" : obj.getClass().getName();
	}

	/**
	 * 判断对象是否属于指定的类型
	 * @param obj
	 * @param clazz
	 * @param typeArray
	 * @return true/false
	 * @throws Exception
	 */
	protected static boolean typeOf(Object obj,Class<?> clazz,String[] typeArray) throws Exception{
		if(isEmpty(obj)) {
			return false;
		}
		String typeString=type(obj);
		if(null!=typeArray&&typeArray.length>0) {
			for(int i=0;i<typeArray.length;i++){
				if(typeString.equals(typeArray[i])) {
					return true;
				}
			}
			return false;
		}else{
			throw new Exception(clazz.getName()+"type array definition missing...");
		}
	}

	/**
	 * 程序版本
	 * @return
	 */
	public static String version(Class<?> clazz){
		StringBuilder type=new StringBuilder();
		try{
			return type.append(clazz.getName()).append("@").append("JDK").append(System.getProperty("java.version")).append("#").append(System.getProperty("java.class.version")).toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return type.toString();
	}
}
