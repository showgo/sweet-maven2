/**
 * 
 */
package org.sweet.frameworks.database.dao.service;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.sweet.frameworks.database.sql.SQLHelper;

/**
 * 元数据服务(ServiceForMetaData)
 * @filename:
 * @filedescription:
 * @copyright:版权所有(C)2009-2050
 * @company:成都淞幸科技有限责任公司
 * @summary:
 * @othersummary:
 * @finisheddate:
 * @modifyrecords:
 * @version:1.0.0
 * @date:wugz/2017年3月21日
 */
public class ServiceForMetaData extends Service {
	public ServiceForMetaData(HttpServletRequest request){
		super(request);
	}

	private List<Map<String,Object>> getObjects(String schema,String tableName,String types) throws SQLException{
		final List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		DatabaseMetaData meta=this.dao.queryMetaData();
		ResultSet result=meta.getTables(null,schema,tableName,new String[]{types});
		if(null!=result){
			List<String> resultFields=SQLHelper.getResultMap(result);
			while(result.next()){
				Map<String,Object> map=new HashMap<String,Object>();
				for(String field:resultFields){
					map.put(field,result.getObject(field));
				}
				list.add(map);
			}
			result.close();
		}
		return list;
	}

	private List<Map<String,Object>> getColumns(String schema,String tableName,String columnPattern) throws SQLException{
		final List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		DatabaseMetaData meta=this.dao.queryMetaData();
		ResultSet result=meta.getColumns(null,schema,tableName,columnPattern);
		if(null!=result){
			List<String> resultFields=SQLHelper.getResultMap(result);
			while(result.next()){
				Map<String,Object> map=new HashMap<String,Object>();
				for(String field:resultFields){
					map.put(field,result.getObject(field));
				}
				list.add(map);
			}
			result.close();
		}
		return list;
	}

	/**
	 * 获得表的元信息
	 * @param schema
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String,Object>> getTable(String schema,String tableName) throws SQLException{
		return this.getObjects(schema,tableName,"TABLE");
	}

	/**
	 * 获得视图的元信息
	 * @param schema
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String,Object>> getView(String schema,String tableName) throws SQLException{
		return this.getObjects(schema,tableName,"VIEW");
	}

	/**
	 * 获得列的元信息
	 * @param schema
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String,Object>> getColumns(String schema,String tableName) throws SQLException{
		return this.getColumns(schema,tableName,null);
	}
}
