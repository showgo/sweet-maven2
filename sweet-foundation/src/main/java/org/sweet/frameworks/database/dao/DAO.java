package org.sweet.frameworks.database.dao;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.sweet.frameworks.database.sql.SQL;

/**
 * DAO
 * @filename:DAO
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public interface DAO {
	/**
	 * 执行sql语句
	 * @param id sql语句配置id
	 * @param parameter 参数
	 * @return 执行结果标志(或标志数组)
	 * @throws SQLException
	 */
	public int execute(String id,Object parameter) throws SQLException;

	/**
	 * 执行预定义语句组(包括执行语句和查询语句)
	 * @param sql
	 * @return true/false
	 * @throws SQLException
	 */
	public boolean execute(SQL sql) throws SQLException;

	/**
	 * 执行sql语句
	 * @param sql sql语句
	 * @param parameter 参数
	 * @return 执行结果标志(或标志数组)
	 * @throws SQLException
	 */
	public int executeSQL(String sql,Object parameter) throws SQLException;

	/**
	 * 执行查询语句
	 * @param id sql语句配置id
	 * @param parameter 参数
	 * @return 查询结果集
	 * @throws SQLException
	 */
	public List<Map<String,Object>> query(String id,Object parameter) throws SQLException;

	/**
	 * 执行查询语句
	 * @param sql sql语句
	 * @param parameter 参数
	 * @return 查询结果集
	 * @throws SQLException
	 */
	public List<Map<String,Object>> querySQL(String sql,Object parameter) throws SQLException;

	/**
	 * 查询数据库元数据
	 * @return
	 * @throws HibernateException
	 */
	public DatabaseMetaData queryMetaData() throws SQLException;
}
