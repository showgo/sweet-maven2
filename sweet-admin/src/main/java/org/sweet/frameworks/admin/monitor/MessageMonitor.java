package org.sweet.frameworks.admin.monitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sweet.frameworks.foundation.annotation.servlet.Servlet;
import org.sweet.frameworks.foundation.resource.PropertiesReader;

@Servlet(name="/servlet/message.do",allowValidated=false)
public class MessageMonitor extends HttpServlet {
	private static final long serialVersionUID=-4640860678047138875L;

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		try{
			StringBuilder builder=new StringBuilder();
			Map<String,Object> map=MessageReader.getPropertiesMap();
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

	private static class MessageReader extends PropertiesReader {
		public static Map<String,Object> getPropertiesMap(){
			return propertiesMap;
		}
	}
}