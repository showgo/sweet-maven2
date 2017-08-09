package org.sweet.frameworks.system.filter;

import java.io.IOException;
import java.io.PrintWriter;
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

import org.sweet.frameworks.foundation.util.debug.Debug;
import org.sweet.frameworks.foundation.util.json.JSONUtil;
import org.sweet.frameworks.foundation.util.map.MapUtil;
import org.sweet.frameworks.system.session.Session;
import org.sweet.frameworks.system.session.SessionException;

/**
 * RestController注解过滤器
 * @filename:RestControllerAnnotationFilter
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public class RestControllerAnnotationFilter implements Filter {
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
		Map<String,ControllerModel> classMap=(Map<String,ControllerModel>)this.servletContext.getAttribute("controllerClassMap");
		/* 1.过滤获得所有controller */
		String uri=request.getParameter("uri");
		String baseUri=this.getBaseURI(uri);
		Map<String,Object> params=MapUtil.fromURL(uri);
		ControllerModel model=classMap.get(baseUri);
		Object obj=null;
		try{
			obj=model.getController().newInstance();
		}catch(InstantiationException e1){
			e1.printStackTrace();
		}catch(IllegalAccessException e1){
			e1.printStackTrace();
		}
		/* 调用服务方法 */
		try{
			Method targetMethod=model.getMethod();
			/* 校验session */
			boolean allowValidated=model.allowValidated();
			if((allowValidated&&Session.validate(req))||!allowValidated){
				PrintWriter out=resp.getWriter();
				resp.setContentType("text/html; charset=UTF-8");
				resp.setCharacterEncoding("UTF-8");
				resp.addHeader("session.status","normal");
				resp.addHeader("session.url",null);
				Object result=targetMethod.invoke(obj,params);
				out.print(JSONUtil.fromObject(ResponseMap.ok(result)));
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
			Debug.info(RestControllerAnnotationFilter.class,e.getMessage());
			/* 重定向到登录页面 */
			resp.addHeader("session.status","timeout");
			resp.addHeader("session.url",req.getContextPath());
			chain.doFilter(req,resp);
		}
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
