package org.sweet.frameworks.foundation.expression;

import org.sweet.frameworks.foundation.util.string.StringUtil;

/**
 * 变量表达式
 * @filename:
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:
 * @modifyrecords:
 */
public final class VarExpression {
	private DataType type=null;
	private String name=null;
	private String separator=null;

	/**
	 * 分隔符
	 * @param expression 变量表达式
	 * @param separator 分隔符号
	 */
	private VarExpression(String expression,String separator){
		if(null!=expression&&null!=separator) {
			String[] expressions=expression.split(separator);
			if(expressions.length>=2) {
				this.name=null!=expressions[0] ? expressions[0].trim() : expressions[0];
				this.type=DataType.getValue(null!=expressions[1] ? expressions[1].trim() : expressions[1]);
			}else if(expressions.length<2) {
				this.name=null!=expressions[0] ? expressions[0].trim() : expressions[0];
				this.type=DataType.string;
			}
		}
	}

	/**
	 * 表达式分析
	 * @param expression
	 * @param separator
	 * @return
	 */
	public static VarExpression parse(String expression,String separator){
		return new VarExpression(expression,separator);
	}

	public DataType getType(){
		return null!=type ? type : DataType.string;
	}

	public String getName(){
		return name;
	}

	public String convertedValue(Object obj){
		if(null!=obj) {
			if(DataType.string.equals(this.getType())) {
				return StringUtil.getSingleQuoteToken()+obj.toString()+StringUtil.getSingleQuoteToken();
			}else if(DataType.number.equals(this.getType())) {
				return obj.toString();
			}else{
				// FIXME:handle type date etc.
				return obj.toString();
			}
		}
		return "null";
	}

	public String getSeparator(){
		return separator;
	}
}
