/**
 * 
 */
package org.sweet.frameworks.foundation.expression;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表达式Element
 * @filename:Element
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-28
 * @modifyrecords:
 */
public final class ExpressionElement {
	public static final String TOKEN="token";
	public static final String KEYWORD="keyword";
	public static final String VAR="var";
	public static final String CONSTANT="constant";
	private String type=null;
	private String value=null;
	private static Map<String,String> keywords=new HashMap<String,String>();
	static{
		keywords.put("null","null");
		keywords.put("and","&&");
		keywords.put("or","||");
		keywords.put("not","!");
	}

	/**
	 * 构造函数
	 * @param type
	 * @param value
	 */
	public ExpressionElement(String type,String value){
		this.type=type;
		this.value=value;
	}

	/**
	 * 是否关键字
	 * @param value
	 * @return
	 */
	public static boolean isKeyword(String value){
		return keywords.containsKey(value);
	}

	/**
	 * 是否变量标识符
	 * @param value
	 * @return
	 */
	public static boolean isVar(String value){
		if(keywords.containsKey(value)){
			return false;
		}
		Pattern pattern=Pattern.compile("^[A-Za-z_$]+[A-Za-z_$\\d]+$");
		Matcher matcher=pattern.matcher(value);
		return matcher.matches();
	}

	public String getType(){
		return type;
	}

	public void setType(String type){
		this.type=type;
	}

	public Object getKeywordValue(){
		return keywords.get(this.value);
	}

	public String getValue(){
		return value;
	}

	public void setValue(String value){
		this.value=value;
	}

	public String toString(){
		return this.getClass()+": [type: "+this.getType()+", value: "+this.getValue()+"]";
	}
}
