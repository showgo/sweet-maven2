package org.sweet.frameworks.admin.monitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sweet.frameworks.foundation.annotation.controller.RestController;
import org.sweet.frameworks.foundation.annotation.servlet.Servlet;
import org.sweet.frameworks.system.loader.AnnotationClassesLoader;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

@Servlet(value="/servlet/info.do",allowValidated=false)
public class ServletMonitor extends HttpServlet implements ServletContextListener {
	private static final long serialVersionUID=-4640860678047138875L;
	private ServletContext servletContext=null;

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		try{
			List<String> classes=AnnotationClassesLoader.getClasses(this.servletContext,RestController.class);
			StringBuilder builder=new StringBuilder();
			builder.append("<html>");
			builder.append("<body>");
			builder.append("<table border=\"1\" style=\"border-collapse: collapse;\">");
			for(int e=0;e<classes.size();e++){
				builder.append("<tr>");
				builder.append("<td>");
				builder.append(e);
				builder.append("</td>");
				builder.append("<td>");
				builder.append(classes.get(e));
				builder.append("</td>");
				builder.append("</tr>");
			}
			builder.append("</table>");
			builder.append("</body>");
			builder.append("</html>");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out=response.getWriter();
			out.println(builder.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws NotFoundException,ClassNotFoundException{
		ClassPool pool=ClassPool.getDefault();
		// pool.insertClassPath("/WEB-INF/lib/");
		// pool.insertClassPath("/WEB-INF/classes/");
		CtClass clazz=pool.getCtClass("org.sweet.frameworks.admin.monitor.Test");
		System.out.println(clazz.getAnnotation(RestController.class));
		System.out.println(clazz);
	}

	public void contextInitialized(ServletContextEvent sce){
		this.servletContext=sce.getServletContext();
	}

	public void contextDestroyed(ServletContextEvent sce){
	}
}