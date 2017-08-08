package org.sweet.frameworks.foundation.resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.ServletContext;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.FilenameUtils;
import org.sweet.frameworks.foundation.message.Messages;
import org.sweet.frameworks.foundation.resource.ResourcePathUtil;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

/**
 * ClassResources
 * @filename:
 * @filedescription:ClassResources
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:
 * @modifyrecords:
 */
public class ClassResources extends DirectoryWalker<String> {
	private static String defaultContext;
	private static ClassResources instance;
	static{
		defaultContext=Messages.getDefault("default-context").toString();
		instance=new ClassResources();
	}

	/**
	 * 文件处理
	 */
	protected void handleFile(File file,int depth,Collection<String> results){
		String resource=file.getAbsolutePath();
		if(resource.endsWith(".jar")&&FilenameUtils.getName(resource).contains(getDefaultContext())){
			results.add(resource);
		}
	}

	/**
	 * 返回默认上下文名称
	 * @return
	 */
	public static String getDefaultContext(){
		return defaultContext;
	}

	/**
	 * 获得自定义classpath
	 * @param servletContext
	 * @return
	 * @throws IOException
	 */
	public static List<String> getCustomClasspath(ServletContext servletContext) throws IOException{
		List<String> results=new ArrayList<String>();
		results.add(servletContext.getRealPath("/WEB-INF/classes/"));
		instance.walk(new File(servletContext.getRealPath("/WEB-INF/lib/")),results);
		return results;
	}

	/**
	 * 返回类
	 * @param pool
	 * @param path
	 * @param resource
	 * @return
	 */
	public static CtClass getResourceAsClass(ClassPool pool,String path,String resource){
		try{
			resource=(null!=path&&!"".equals(path)) ? ResourcePathUtil.getRelativePath(path,resource) : resource;
			String className=getClassName(resource);
			return pool.getCtClass(className);
		}catch(NotFoundException e){
			e.printStackTrace();
		}
		return null;
	}

	public static Class<?> getResourceAsClass(String resource){
		try{
			String className=getClassName(resource);
			return Class.forName(className);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		return null;
	}

	private static String getClassName(String resource){
		String className=resource.substring(0,resource.lastIndexOf(".class"));
		return className.replace(File.separatorChar,'.').replace('/','.');
	}
}
