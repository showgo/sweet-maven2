package org.sweet.frameworks.system.filter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sweet.frameworks.foundation.annotation.servlet.Servlet;
import org.sweet.frameworks.foundation.resource.ClassResources;
import org.sweet.frameworks.foundation.resource.ResourcePathUtil;
import org.sweet.frameworks.foundation.util.debug.Debug;
import org.sweet.frameworks.system.loader.ServletResourceLoader;
import org.sweet.frameworks.system.session.Session;
import org.sweet.frameworks.system.session.SessionException;

/**
 * Servlet注解过滤器
 * @filename:ServletAnnotationFilter
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public class ServletAnnotationFilter implements Filter {
	private ServletContext servletContext=null;

	/**
	 * init
	 */
	public void init(FilterConfig filterConfig) throws ServletException{
		this.servletContext=filterConfig.getServletContext();
		Map<String,Class<?>> classMap=new HashMap<String,Class<?>>();
		List<String> setClasses=ServletResourceLoader.getServletClasses(this.servletContext);
		StringBuilder buffer=new StringBuilder();
		for(String clazz:setClasses){
			Class<?> cls=ClassResources.getResourceAsClass(null,clazz);
			Servlet annotationInstance=cls.getAnnotation(Servlet.class);
			String annotationAttrValue=annotationInstance.name();
			if(!"".equals(annotationAttrValue)){
				classMap.put(annotationAttrValue,cls);
				buffer.append(cls.getName());
				buffer.append(", ");
			}
		}
		this.servletContext.setAttribute("servletClassMap",classMap);
		Debug.info(ServletAnnotationFilter.class,"Servlet(s): ["+buffer.substring(0,buffer.lastIndexOf(","))+"] ");
		Debug.info(ServletAnnotationFilter.class,"[Load servlet(s) completed]");
	}

	/**
	 * doFilter
	 */
	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain) throws IOException,ServletException{
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse resp=(HttpServletResponse)response;
		Map<String,Class<?>> classMap=(Map<String,Class<?>>)this.servletContext.getAttribute("servletClassMap");
		String uri=this.getURI(req);
		Class<?> clazz=classMap.get(uri);
		Object obj=null;
		try{
			obj=clazz.newInstance();
		}catch(InstantiationException e1){
			e1.printStackTrace();
		}catch(IllegalAccessException e1){
			e1.printStackTrace();
		}
		/* 调用服务方法:doGet or doPost */
		String reqMethod=req.getMethod();
		Method targetMethod=null;
		if(reqMethod.equalsIgnoreCase("get")){
			try{
				targetMethod=clazz.getDeclaredMethod("doGet",HttpServletRequest.class,HttpServletResponse.class);
			}catch(SecurityException e){
				e.printStackTrace();
			}catch(NoSuchMethodException e){
				e.printStackTrace();
			}
		}else{
			try{
				targetMethod=clazz.getDeclaredMethod("doPost",HttpServletRequest.class,HttpServletResponse.class);
			}catch(SecurityException e){
				e.printStackTrace();
			}catch(NoSuchMethodException e){
				e.printStackTrace();
			}
		}
		try{
			/* 校验session */
			boolean allowValidated=clazz.getAnnotation(Servlet.class).allowValidated();
			if(allowValidated&&Session.validate(req)){
				/* 调用doGet或doPost */
				resp.addHeader("session.status","normal");
				resp.addHeader("session.url",null);
				targetMethod.invoke(obj,req,resp);
			}else if(!allowValidated){
				/* 调用doGet或doPost */
				resp.addHeader("session.status","normal");
				resp.addHeader("session.url",null);
				targetMethod.invoke(obj,req,resp);
			}else{
				/* 会话错误 */
				throw new SessionException("System session failure...");
			}
			/* 执行下一个filter */
			// chain.doFilter(request,response);
		}catch(IllegalArgumentException e){
			e.printStackTrace();
		}catch(IllegalAccessException e){
			e.printStackTrace();
		}catch(InvocationTargetException e){
			e.printStackTrace();
		}catch(SessionException e){
			Debug.info(ServletAnnotationFilter.class,e.getMessage());
			/* 重定向到登录页面 */
			resp.addHeader("session.status","timeout");
			resp.addHeader("session.url",req.getContextPath());
			chain.doFilter(req,resp);
		}
	}

	/**
	 * 获得访问的相对uri
	 * @param request
	 * @return
	 */
	public String getURI(HttpServletRequest request){
		return ResourcePathUtil.getRelativePath(request.getContextPath(),request.getRequestURI());
	}

	/**
	 * destroy
	 */
	public void destroy(){
	}
}
