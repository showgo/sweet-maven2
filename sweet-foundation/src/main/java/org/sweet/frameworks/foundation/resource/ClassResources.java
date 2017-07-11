package org.sweet.frameworks.foundation.resource;

import java.io.File;

/**
 * ClassResources
 * @filename:
 * @filedescription:ClassResources
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:
 * @modifyrecords:
 */
public class ClassResources {
	/**
	 * 返回类
	 * @param path
	 * @param resource
	 * @return
	 */
	public static Class<?> getResourceAsClass(String path,String resource){
		Class<?> clazz=null;
		try{
			String clazzName=(null!=path&&!"".equals(path)) ? ResourcePathUtil.getRelativePath(path,resource) : resource;
			clazzName=clazzName.substring(0,clazzName.lastIndexOf(".class"));
			clazzName=clazzName.replace(File.separatorChar,'.').replace('/','.');
			clazz=Class.forName(clazzName);
		}catch(ClassNotFoundException ex){
		}
		return clazz;
	}
}
