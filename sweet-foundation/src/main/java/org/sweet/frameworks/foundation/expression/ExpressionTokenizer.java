/**
 * 
 */
package org.sweet.frameworks.foundation.expression;

import java.util.ArrayList;
import java.util.List;

import org.sweet.frameworks.foundation.util.string.StringUtil;

/**
 * (ExpressionTokenizer)
 * @filename:ExpressionTokenizer
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-29
 * @modifyrecords:
 */
public final class ExpressionTokenizer {
	private final String openToken;
	private final String closeToken;

	/**
	 * 构造函数
	 */
	public ExpressionTokenizer(){
		this(null,null);
	}

	/**
	 * 构造函数
	 * @param openToken
	 * @param closeToken
	 */
	public ExpressionTokenizer(String openToken,String closeToken){
		this.openToken=openToken;
		this.closeToken=closeToken;
	}

	/**
	 * 匹配目标文本
	 * @param text
	 * @return
	 */
	public List<String> match(String text){
		List<String> list=new ArrayList<String>();
		if(text!=null){
			String after=text;
			int start=after.indexOf(this.openToken);
			int end=after.indexOf(this.closeToken);
			while(start!=-1&&end!=-1){
				if(end>start){
					String content=after.substring(start,end+1);
					list.add(content);
					after=after.substring(end+this.closeToken.length());
				}
				start=after.indexOf(this.openToken);
				end=after.indexOf(this.closeToken);
			}
		}
		return list;
	}

	/**
	 * 去除文本(去除掉开始结束标记)
	 * @param text
	 * @return
	 */
	public String trim(String text){
		StringBuilder builder=new StringBuilder();
		if(text!=null){
			String after=text;
			int start=after.indexOf(this.openToken);
			int end=after.indexOf(this.closeToken);
			while(start>-1){
				if(end>start){
					String before=after.substring(0,start);
					String content=after.substring(start+this.openToken.length(),end);
					builder.append(before);
					builder.append(content);
					after=after.substring(end+this.closeToken.length());
				}else{
					if(end<=-1){
						break;
					}
					String before=after.substring(0,end);
					builder.append(before);
					builder.append(this.closeToken);
					after=after.substring(end+this.closeToken.length());
				}
				start=after.indexOf(this.openToken);
				end=after.indexOf(this.closeToken);
			}
			builder.append(after);
		}
		return builder.toString();
	}

	/**
	 * 表达式分割
	 * @param string
	 * @param token
	 * @return
	 */
	public List<ExpressionElement> split(String string,String token){
		List<ExpressionElement> list=new ArrayList<ExpressionElement>();
		for(String element:this.split(string,token,true)){
			String elementTrim=StringUtil.isNotEmpty(element) ? element.trim() : null;
			if(token.contains(elementTrim)){
				list.add(new ExpressionElement(ExpressionElement.TOKEN,elementTrim));
			}else if(ExpressionElement.isKeyword(elementTrim)){
				list.add(new ExpressionElement(ExpressionElement.KEYWORD,elementTrim));
			}else if(ExpressionElement.isVar(elementTrim)){
				list.add(new ExpressionElement(ExpressionElement.VAR,elementTrim));
			}else{
				list.add(new ExpressionElement(ExpressionElement.CONSTANT,elementTrim));
			}
		}
		return list;
	}

	/**
	 * 字符串分割
	 * @param string 待分割字符串
	 * @param token 分割符集
	 * @param protectStringConst 是否保护字符串常量
	 * @return
	 */
	public String[] split(String string,String token,boolean protectStringConst){
		/* 不需要保护字符串常量 */
		if(!protectStringConst){
			return string.split(token);
		}
		/* 需要保护字符串常量 */
		List<String> expressions=new ArrayList<String>();
		StringBuilder builder=new StringBuilder();
		char[] tokens=token.toCharArray();
		boolean startConst=false;
		boolean endConst=false;
		for(int e=0;e<string.length();e++){
			char tmp=string.charAt(e);
			/* 设置匹配字符常量开始，结束标志 */
			if(tmp=='\''||tmp=='"'){
				if(startConst==false){
					startConst=true;
					endConst=false;
				}else{
					startConst=false;
					endConst=true;
				}
			}
			/* 处理字符串常量部分 */
			if(startConst==true&&endConst==false){
				builder.append(tmp);
				continue;
			}else if(startConst==false&&endConst==true){
				builder.append(tmp);
				if(builder.length()>0){
					expressions.add(String.valueOf(builder));
				}
				builder.delete(0,builder.length());
				startConst=false;
				endConst=false;
				continue;
			}
			/* 处理非字符串常量部分 */
			if(matched(tmp,tokens)){
				if(builder.length()>0){
					expressions.add(String.valueOf(builder));
				}
				expressions.add(String.valueOf(tmp));
				builder.delete(0,builder.length());
			}else{
				builder.append(tmp);
			}
		}
		return expressions.toArray(new String[expressions.size()]);
	}

	private static boolean matched(char chr,char[] delimiters){
		for(char delimiter:delimiters){
			if(chr==delimiter){
				return true;
			}
		}
		return false;
	}
}