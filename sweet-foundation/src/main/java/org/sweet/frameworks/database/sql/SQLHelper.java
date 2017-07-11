package org.sweet.frameworks.database.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.session.SqlSession;
import org.sweet.frameworks.foundation.util.debug.Debug;

/**
 * Sql配置文件分析器:查找并解析配置文件
 * @filename:SqlHelper
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public class SQLHelper {
	/**
	 * 获得结果集的字段组
	 * @param rs 结果集
	 * @return 字段组
	 * @throws SQLException
	 */
	public static List<String> getResultMap(ResultSet rs) throws SQLException{
		if(null!=rs){
			List<String> list=new ArrayList<String>();
			ResultSetMetaData rm=rs.getMetaData();
			for(int i=0;i<rm.getColumnCount();i++){
				/* modify:使用getColumnLabel()替代getColumnName()解决字段重命名问题/2016/09/22 */
				list.add(rm.getColumnLabel(i+1).toLowerCase());
			}
			return list;
		}
		return null;
	}

	/**
	 * 返回预定义SQL包装类
	 * @param session
	 * @param sql
	 * @param parameter
	 * @return
	 */
	public static PreparedSQL getPreparedSQL(SqlSession session,String sql,Object parameter){
		return new PreparedSQL(session,sql,parameter);
	}

	/**
	 * PreparedSQL
	 */
	public static class PreparedSQL {
		private SqlSession session=null;
		private StringBuilder originalSQL=null;
		private StringBuilder preparedSQL=null;
		private Map<String,String> parameterMappings=new TreeMap<String,String>(new Comparator<String>() {
			public int compare(String key1,String key2){
				return Integer.parseInt(key1)-Integer.parseInt(key2);
			}
		});
		private Map<String,Object> parameters=new TreeMap<String,Object>(new Comparator<String>() {
			public int compare(String key1,String key2){
				return Integer.parseInt(key1)-Integer.parseInt(key2);
			}
		});

		/**
		 * 构造函数
		 * @param session
		 * @param sql
		 * @param parameter
		 */
		private PreparedSQL(SqlSession session,String sql,Object parameter){
			this.session=session;
			this.originalSQL=new StringBuilder(sql);
			this.preparedSQL=new StringBuilder(sql);
			this.parse(parameter);
		}

		private void parse(Object parameter){
			Pattern pat=Pattern.compile("#\\{[\\w]+\\}");
			Matcher mat=pat.matcher(this.originalSQL);
			int index=1;
			while(mat.find()){
				String target=mat.group();
				String key=target.substring(2,target.length()-1);
				Object val=this.getParameterValue(parameter,key);
				this.preparedSQL.replace(this.preparedSQL.indexOf(target),this.preparedSQL.indexOf(target)+target.length(),"?");
				this.parameterMappings.put(String.valueOf(index),null!=val ? (val+"("+val.getClass().getSimpleName()+")") : null);
				this.parameters.put(String.valueOf(index),val);
				index++;
			}
		}

		@SuppressWarnings("rawtypes")
		private Object getParameterValue(Object parameter,String key){
			if(null!=parameter){
				if(parameter instanceof Map){
					return ((Map)parameter).get(key);
				}else{
					return parameter;
				}
			}
			return null;
		}

		public String getSQL(){
			return this.preparedSQL.toString();
		}

		public String getParameterMappings(){
			StringBuilder builder=new StringBuilder();
			for(Iterator<String> it=parameterMappings.keySet().iterator();it.hasNext();){
				String key=it.next();
				Object val=parameterMappings.get(key);
				builder.append(val);
				builder.append(", ");
			}
			return builder.substring(0,builder.lastIndexOf(","));
		}

		public Map<String,Object> getParameters(){
			return this.parameters;
		}

		/**
		 * 执行sql语句
		 * @return
		 * @throws SQLException
		 */
		public int update() throws SQLException{
			/* 创建预编译语句包装器 */
			Debug.debug(PreparedSQL.class,"==>  Preparing: "+this.getSQL());
			Debug.debug(PreparedSQL.class,"==> Parameters: "+this.getParameterMappings());
			/* 创建预编译语句,并设置参数 */
			PreparedStatement pstmt=null;
			try{
				pstmt=session.getConnection().prepareStatement(this.getSQL());
				Map<String,Object> parameters=this.getParameters();
				for(Iterator<String> it=parameters.keySet().iterator();it.hasNext();){
					String key=it.next();
					pstmt.setObject(Integer.parseInt(key),parameters.get(key));
				}
				/* 执行语句 */
				return pstmt.executeUpdate();
			}catch(SQLException e){
				throw new SQLException(e.getCause());
			}finally{
				if(null!=pstmt){
					pstmt.close();
				}
			}
		}

		/**
		 * 返回数据集
		 * @return
		 * @throws SQLException
		 */
		public List<Map<String,Object>> query() throws SQLException{
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			/* 创建预编译语句包装器 */
			Debug.debug(PreparedSQL.class,"==>  Preparing: "+this.getSQL());
			Debug.debug(PreparedSQL.class,"==> Parameters: "+this.getParameterMappings());
			/* 创建预编译语句,并设置参数 */
			PreparedStatement pstmt=null;
			ResultSet results=null;
			try{
				pstmt=session.getConnection().prepareStatement(this.getSQL());
				Map<String,Object> parameters=this.getParameters();
				for(Iterator<String> it=parameters.keySet().iterator();it.hasNext();){
					String key=it.next();
					pstmt.setObject(Integer.parseInt(key),parameters.get(key));
				}
				/* 执行语句,并组装结果集 */
				results=pstmt.executeQuery();
				if(null!=results){
					List<String> resultFields=getResultMap(results);
					while(results.next()){
						Map<String,Object> map=new HashMap<String,Object>();
						for(String field:resultFields){
							map.put(field,results.getObject(field));
						}
						list.add(map);
					}
				}
			}catch(SQLException e){
				throw new SQLException(e.getCause());
			}finally{
				if(null!=results){
					results.close();
				}
				if(null!=pstmt){
					pstmt.close();
				}
			}
			return list;
		}
	}
}
