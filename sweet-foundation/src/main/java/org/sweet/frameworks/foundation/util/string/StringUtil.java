package org.sweet.frameworks.foundation.util.string;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.UUID;

/**
 * StringUtil
 * @filename:StringUtil
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016年12月2日
 * @modifyrecords:
 */
public final class StringUtil {
	/**
	 * 生成uuid
	 * @return
	 */
	public static String uuid(){
		UUID uuid=UUID.randomUUID();
		return uuid.toString().replaceAll("-","").replaceAll("_","");
	}

	/**
	 * 返回单引号字符
	 * @return
	 */
	public static String getSingleQuoteToken(){
		return "'";
	}

	/**
	 * 返回双引号字符
	 * @return
	 */
	public static String getDoubleQuoteToken(){
		return "\"";
	}

	/**
	 * 判断字符串是否为空
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(String string){
		return null==string||"".equals(string);
	}

	/**
	 * 判断字符串是否不为空
	 * @param string
	 * @return
	 */
	public static boolean isNotEmpty(String string){
		return null!=string&&!"".equals(string);
	}

	/**
	 * 美化字符串:去掉多余空格(字符常量除外)
	 * @param string 字符串
	 * @return
	 */
	public static String prettyString(String string){
		if(null!=string){
			StringBuilder buffer=new StringBuilder();
			StringBuilder temp=new StringBuilder();
			for(int e=0;e<string.length();e++){
				if('\''!=string.charAt(e)&&'"'!=string.charAt(e)){
					temp.append(string.charAt(e));
					if((e+1)==string.length()){
						/* 获取非字符(串)常量子串 */
						buffer.append(temp.toString().replaceAll("[\\s]+"," "));
						temp.delete(0,temp.length());
					}
				}else{
					/* 获取非字符(串)常量子串 */
					buffer.append(temp.toString().replaceAll("[\\s]+"," "));
					temp.delete(0,temp.length());
					/* 获取字符串中的字符(串)常量子串 */
					temp.append(string.charAt(e));
					char token=string.charAt(e);
					for(int i=e+1;i<string.length();i++){
						e=i;
						temp.append(string.charAt(i));
						if(string.charAt(i)==token){
							break;
						}
					}
					buffer.append(temp.toString());
					temp.delete(0,temp.length());
				}
			}
			return buffer.toString();
		}
		return string;
	}

	/**
	 * 字符串首字母大写
	 * @param string
	 * @return
	 */
	public static String capitalize(String string){
		int strLen;
		return string!=null&&(strLen=string.length())!=0 ? (new StringBuilder(strLen)).append(Character.toTitleCase(string.charAt(0))).append(string.substring(1)).toString() : string;
	}

	/**
	 * 对象转字符串
	 * @param object
	 * @return
	 */
	public static String toString(Object object){
		if(null!=object){
			StringBuilder builder=new StringBuilder();
			Class<?> clazz=object.getClass();
			builder.append("{");
			builder.append("class: ").append(clazz.getName()).append(", ");
			builder.append("fields: ").append("{");
			final Field[] fields=clazz.getDeclaredFields();
			for(int index=0;index<fields.length;index++){
				try{
					Field field=fields[index];
					Object value=(Modifier.isPublic(field.getModifiers())||Modifier.isProtected(field.getModifiers())||Modifier.isPrivate(field.getModifiers())) ? field.get(object) : "[PROTECTED]";
					if((index+1)<fields.length){
						builder.append(field.getName()).append(": ").append(value).append(", ");
					}else{
						builder.append(field.getName()).append(": ").append(value);
					}
				}catch(IllegalAccessException e){
				}
			}
			builder.append("}");
			builder.append("}");
			return builder.toString();
		}
		return "";
	}
}
