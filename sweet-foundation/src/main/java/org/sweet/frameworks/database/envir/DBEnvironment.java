/**
 * 
 */
package org.sweet.frameworks.database.envir;

import java.util.HashMap;
import java.util.Map;

/**
 * DBEnvironment
 * @filename:DBEnvironment
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:
 * @modifyrecords:
 */
public final class DBEnvironment {
	/**
	 * 数据库配置名
	 */
	private String id=null;
	/**
	 * 数据库类型
	 */
	private String type=null;
	private boolean isDefault=false;
	private String transactionType=null;
	private Map<String,String> transaction=new HashMap<String,String>();
	private String dataSourceType=null;
	private Map<String,String> dataSource=new HashMap<String,String>();
	private String version="3.4.2";

	/**
	 * 构造函数
	 */
	public DBEnvironment(){
	}

	public String getId(){
		return id;
	}

	public void setId(String id){
		this.id=id;
	}

	public String getType(){
		return type;
	}

	public void setType(String type){
		this.type=type;
	}

	public boolean isDefault(){
		return isDefault;
	}

	public void setDefault(boolean isDefault){
		this.isDefault=isDefault;
	}

	public String getTransactionType(){
		return transactionType;
	}

	public void setTransactionType(String transactionType){
		this.transactionType=transactionType;
	}

	public Map<String,String> getTransactionProperties(){
		return transaction;
	}

	public void setTransactionProperties(String key,String value){
		this.transaction.put(key,value);
	}

	public String getDataSourceType(){
		return dataSourceType;
	}

	public void setDataSourceType(String dataSourceType){
		this.dataSourceType=dataSourceType;
	}

	public Map<String,String> getDataSourceProperties(){
		return dataSource;
	}

	public void setDataSourceProperties(String key,String value){
		this.dataSource.put(key,value);
	}

	public String getVersion(){
		return version;
	}

	public String toString(){
		StringBuilder builder=new StringBuilder();
		builder.append("{");
		builder.append("id=");
		builder.append(this.id).append(", ");
		builder.append("type=");
		builder.append(this.type).append(", ");
		builder.append("transactionType=");
		builder.append(this.transactionType).append(", ");
		builder.append("transactionProperties=");
		builder.append(this.getTransactionProperties()).append(", ");
		builder.append("dataSourceType=");
		builder.append(this.dataSourceType).append(", ");
		builder.append("dataSourceProperties=");
		builder.append(this.getDataSourceProperties()).append(", ");
		builder.append("version=");
		builder.append(this.getVersion()).append(", ");
		builder.append("isDefault=");
		builder.append(this.isDefault);
		builder.append("}");
		return builder.toString();
	}
}
