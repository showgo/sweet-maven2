package org.sweet.frameworks.foundation.expression;

/**
 * 数据类型(DataType)
 * @filename:DataType
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:
 * @modifyrecords:
 */
public enum DataType{
	string("string"),number("number"),date("date");
	private String value=null;

	/**
	 * 构造函数
	 * @param value
	 */
	DataType(String value){
		this.value=value;
	}

	public String getValue(){
		return value;
	}

	/**
	 * 根据字符面类型值获得数据类型
	 * @param value
	 * @return
	 */
	public static DataType getValue(String value){
		for(DataType data:DataType.values()){
			if(data.getValue().equalsIgnoreCase(value)) {
				return data;
			}
		}
		return null;
	}
}
