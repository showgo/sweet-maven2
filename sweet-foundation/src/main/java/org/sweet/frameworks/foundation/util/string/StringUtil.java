package org.sweet.frameworks.foundation.util.string;

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
		if(null!=string) {
			StringBuilder buffer=new StringBuilder();
			StringBuilder temp=new StringBuilder();
			for(int e=0;e<string.length();e++){
				if('\''!=string.charAt(e)&&'"'!=string.charAt(e)) {
					temp.append(string.charAt(e));
					if((e+1)==string.length()) {
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
						if(string.charAt(i)==token) {
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
}
