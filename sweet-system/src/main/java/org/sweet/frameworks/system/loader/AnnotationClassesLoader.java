package org.sweet.frameworks.system.loader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;
import org.sweet.frameworks.foundation.resource.ClassResources;
import org.sweet.frameworks.foundation.resource.ResourcesFilter;
import org.sweet.frameworks.foundation.resource.ResourcesWalker;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

/**
 * ervlet资源类加载
 * @Company:
 * @Author: wugz
 * @Create: 2016年6月14日
 * @Version: 1.0.0
 * @ModifyRecords:
 */
public final class AnnotationClassesLoader {
	private static AnnotationClassesLoader loader=null;
	static{
		loader=new AnnotationClassesLoader();
	}

	/**
	 * 返回包含指定注解的类的集合
	 * @param servletContext servlet上下文
	 * @param annotationClazz 注解类
	 * @return
	 */
	protected List<String> getClassesByAnnotation(ServletContext servletContext,final Class<?> annotationClazz){
		List<String> results=new ArrayList<String>();
		try{
			final ClassPool pool=ClassPool.getDefault();
			List<String> paths=ClassResources.getCustomClasspath(servletContext);
			for(String path:paths){
				pool.appendClassPath(path);
			}
			ResourcesWalker walker=new ResourcesWalker(new ResourcesFilter() {
				public boolean accept(String path,String resource){
					try{
						if(resource.endsWith(".jar")){
							return FilenameUtils.getName(resource).contains(ClassResources.getDefaultContext());
						}else if(resource.endsWith(".class")){
							CtClass clazz=ClassResources.getResourceAsClass(pool,path,resource);
							if(null!=clazz&&null!=clazz.getAnnotation(annotationClazz)){
								return true;
							}
						}
					}catch(Exception ex){
					}
					return false;
				}
			});
			walker.walks(servletContext.getRealPath("/WEB-INF/lib/"),results);
			walker.walks(servletContext.getRealPath("/WEB-INF/classes/"),results);
		}catch(IOException e){
			e.printStackTrace();
		}catch(NotFoundException e){
			e.printStackTrace();
		}
		return results;
	}

	/**
	 * 返回包含指定注解的类的集合
	 * @param servletContext servlet上下文
	 * @param annotationClazz 注解类
	 * @return
	 */
	public static List<String> getClasses(ServletContext servletContext,Class<?> annotationClazz){
		return loader.getClassesByAnnotation(servletContext,annotationClazz);
	}
}
