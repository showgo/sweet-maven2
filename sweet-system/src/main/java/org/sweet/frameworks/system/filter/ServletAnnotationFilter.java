package org.sweet.frameworks.system.filter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

import org.apache.commons.codec.binary.Base64;
import org.sweet.frameworks.foundation.annotation.servlet.Servlet;
import org.sweet.frameworks.foundation.resource.ResourcePathUtil;
import org.sweet.frameworks.foundation.util.debug.Debug;
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
		Class<?> servletClazz=classMap.get(uri);
		Object obj=null;
		try{
			obj=servletClazz.newInstance();
		}catch(InstantiationException e1){
			e1.printStackTrace();
		}catch(IllegalAccessException e1){
			e1.printStackTrace();
		}
		/* 调用服务方法:doGet or doPost */
		String reqMethod=req.getMethod();
		Method initMethod=null;
		Method destMethod=null;
		ControllerModel model=null;
		/* 如果是/servlet/controller.do则先执行初始化 */
		if(uri.indexOf("/servlet/controller.do")!=-1){
			try{
				Map<String,ControllerModel> controllerClassMap=(Map<String,ControllerModel>)this.servletContext.getAttribute("controllerClassMap");
				String encryptedUri=request.getParameter("uri");
				String requestUri=new String(Base64.decodeBase64(encryptedUri));
				String baseUri=this.getBaseURI(requestUri);
				model=controllerClassMap.get(baseUri);
				if(null!=model){
					model.setRequestUri(requestUri);
				}
				initMethod=servletClazz.getDeclaredMethod("initControllerModel",ControllerModel.class);
			}catch(NoSuchMethodException e){
				e.printStackTrace();
			}catch(SecurityException e){
				e.printStackTrace();
			}
		}
		if(reqMethod.equalsIgnoreCase("get")){
			try{
				destMethod=servletClazz.getDeclaredMethod("doGet",HttpServletRequest.class,HttpServletResponse.class);
			}catch(SecurityException e){
				e.printStackTrace();
			}catch(NoSuchMethodException e){
				e.printStackTrace();
			}
		}else{
			try{
				destMethod=servletClazz.getDeclaredMethod("doPost",HttpServletRequest.class,HttpServletResponse.class);
			}catch(SecurityException e){
				e.printStackTrace();
			}catch(NoSuchMethodException e){
				e.printStackTrace();
			}
		}
		try{
			/* 校验session */
			boolean allowValidated=servletClazz.getAnnotation(Servlet.class).allowValidated();
			if((allowValidated&&Session.validate(req))||!allowValidated){
				/* 调用doGet或doPost */
				resp.addHeader("session.status","normal");
				resp.addHeader("session.url",null);
				if(null!=initMethod){
					initMethod.invoke(obj,model);
				}
				if(null!=destMethod){
					destMethod.invoke(obj,req,resp);
				}
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
		String uri=ResourcePathUtil.getRelativePath(request.getContextPath(),request.getRequestURI());
		if(!uri.startsWith("/")){
			return "/"+uri;
		}
		return uri;
	}

	/**
	 * 获得访问的相对baseUri
	 * @param request
	 * @return
	 */
	public String getBaseURI(String uri){
		return uri.indexOf("?")!=-1 ? uri.substring(0,uri.indexOf("?")) : uri;
	}

	/**
	 * destroy
	 */
	public void destroy(){
	}
}
