package org.sweet.frameworks.admin.monitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.sweet.frameworks.foundation.annotation.servlet.Servlet;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

@Servlet(name="/servlet/info.do",allowValidated=false)
public class ServletMonitor extends HttpServlet {
	private static final long serialVersionUID=-4640860678047138875L;

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		try{
			Map<String,Object> map=new HashMap<String,Object>();
			StringBuilder builder=new StringBuilder();
			builder.append("<html>");
			builder.append("<body>");
			builder.append("<table border=\"1\" style=\"border-collapse: collapse;\">");
			for(Map.Entry<String,Object> entry:map.entrySet()){
				builder.append("<tr>");
				builder.append("<td>");
				builder.append(entry.getKey());
				builder.append("</td>");
				builder.append("<td>");
				builder.append(entry.getValue());
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

	public static void main(String[] args) throws NotFoundException, ClassNotFoundException{
		ClassPool pool=ClassPool.getDefault();
//		pool.insertClassPath("/WEB-INF/lib/");
//		pool.insertClassPath("/WEB-INF/classes/");
		CtClass clazz=pool.getCtClass("org.sweet.frameworks.admin.monitor.ServletMonitor");
		System.out.println(clazz.getAnnotation(Document.class));
		System.out.println(clazz);
	}
}