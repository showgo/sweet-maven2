package org.sweet.frameworks.database.dao.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.sweet.frameworks.database.dao.DAO;
import org.sweet.frameworks.database.dao.DAOFactory;
import org.sweet.frameworks.database.sql.SQL;

/**
 * 基础服务类Service
 * @filename:Service
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public class Service {
	private DAO dao=DAOFactory.getDAO();

	/**
	 * 构造函数
	 */
	public Service(){
	}

	/**
	 * 数据库操纵(增删改)
	 * @param id 命名sql的全名
	 * @param parameter 用于动态解析sql的参数
	 * @return
	 * @throws SQLException
	 */
	public int execute(String id,Object parameter) throws SQLException{
		return this.dao.execute(id,parameter);
	}

	/**
	 * 执行预定义语句组(包括执行语句和查询语句)
	 * @param sql
	 * @return true/false
	 * @throws SQLException
	 */
	public boolean execute(final SQL sql) throws SQLException{
		return this.dao.execute(sql);
	}

	/**
	 * 执行sql语句
	 * @param sql sql语句
	 * @param parameter 参数
	 * @return 执行结果标志(或标志数组)
	 * @throws SQLException
	 */
	public int executeSQL(String sql,Object parameter) throws SQLException{
		return this.dao.executeSQL(sql,parameter);
	}

	/**
	 * 执行查询语句
	 * @param id sql语句配置id
	 * @param parameter 参数
	 * @return 执行结果标志(或标志数组)
	 * @throws SQLException
	 */
	public List<Map<String,Object>> query(String id,Object parameter) throws SQLException{
		return this.dao.query(id,parameter);
	}

	/**
	 * 执行查询语句
	 * @param id sql语句配置id
	 * @param parameter 参数
	 * @return 执行结果标志(或标志数组)
	 * @throws SQLException
	 */
	public Map<String,Object> queryMap(String id,Object parameter) throws SQLException{
		List<Map<String,Object>> list=this.dao.query(id,parameter);
		if(null!=list&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 执行查询语句
	 * @param sql sql语句
	 * @param parameter 参数
	 * @return 查询结果集
	 * @throws SQLException
	 */
	public List<Map<String,Object>> querySQL(String sql,Object parameter) throws SQLException{
		return this.dao.querySQL(sql,parameter);
	}
}
