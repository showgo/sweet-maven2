package org.sweet.frameworks.foundation.util.json;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.sweet.frameworks.foundation.expression.ExpressionElement;
import org.sweet.frameworks.foundation.expression.ExpressionTokenizer;

/**
 * JSONUtil
 * @filename:JSONUtil
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public final class JSONUtil {
	private static ObjectMapper mapper=null;
	static{
		mapper=new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS"));
	}

	/**
	 * 将对象转换成json文本
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public static String fromObject(Object object) throws IOException{
		String string=null;
		JsonGenerator generator=null;
		StringWriter writer=null;
		try{
			writer=new StringWriter();
			generator=new JsonFactory().createJsonGenerator(writer);
			mapper.writeValue(generator,object);
			string=null!=writer ? writer.toString() : "{}";
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(null!=writer){
				writer.close();
			}
			if(null!=generator){
				generator.close();
			}
		}
		return string;
	}

	/**
	 * JSON字符串转Map
	 * @param string
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> toMap(String string) throws IOException{
		JavaType javaType=getCollectionType(Map.class,String.class,Object.class);
		string=prettyJsonString(string);
		return (Map<String,Object>)mapper.readValue(string,javaType);
	}

	/**
	 * JSON字符串转List
	 * @param string
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String,Object>> toList(String string) throws IOException{
		JavaType javaType=getCollectionType(List.class,Map.class);
		string=prettyJsonString(string);
		return (List<Map<String,Object>>)mapper.readValue(string,javaType);
	}

	/**
	 * 美化JSON字符串
	 * @param string JSON字符串
	 * @return
	 */
	public static String prettyJsonString(String string){
		StringBuilder buffer=new StringBuilder();
		ExpressionTokenizer tokenizer=new ExpressionTokenizer();
		for(ExpressionElement element:tokenizer.split(string,"[{:,}]")){
			if(ExpressionElement.VAR.equals(element.getType())){
				buffer.append("\"").append(element.getValue()).append("\"");
			}else if(ExpressionElement.CONSTANT.equals(element.getType())){
				buffer.append(element.getValue().replaceAll("'","\""));
			}else{
				buffer.append(element.getValue());
			}
		}
		return buffer.toString();
	}

	/**
	 * 获取数据类型
	 * @param collectionClass 集合类型(主类型)
	 * @param elementClasses 元素类型(子类型)
	 * @return
	 */
	private static JavaType getCollectionType(Class<?> collectionClass,Class<?>...elementClasses){
		return mapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);
	}
}
