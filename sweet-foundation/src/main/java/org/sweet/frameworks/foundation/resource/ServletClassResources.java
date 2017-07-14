package org.sweet.frameworks.foundation.resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.ServletContext;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.FilenameUtils;

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
public class ServletClassResources extends DirectoryWalker<String> {
	private static ServletClassResources instance;
	static{
		instance=new ServletClassResources();
	}

	/**
	 * 文件处理
	 */
	protected void handleFile(File file,int depth,Collection<String> results){
		String resource=file.getAbsolutePath();
		if(resource.endsWith(".jar")&&FilenameUtils.getName(resource).contains("sweet")){
			results.add(resource);
		}
	}

	/**
	 * 获得自定义classpath
	 * @param servletContext
	 * @return
	 * @throws IOException
	 */
	public static List<String> getCustomClasspath(ServletContext servletContext) throws IOException{
		List<String> results=new ArrayList<String>();
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
