package org.sweet.frameworks.database.core;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.sweet.frameworks.database.envir.DBEnvironment;
import org.sweet.frameworks.database.sql.SQLHelper;

/**
 * DatabaseSession
 * @filename:DatabaseSession
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:
 * @modifyrecords:
 */
public final class DatabaseSession {
	private DBEnvironment dbEnvir;
	private SqlSession session;

	/**
	 * 构造函数
	 * @param dbEnvir
	 */
	public DatabaseSession(DBEnvironment dbEnvir){
		this.dbEnvir=dbEnvir;
		this.session=DatabaseConfigFactory.getSessionFactory(dbEnvir.getId()).openSession();
	}

	/**
	 * 返回会话属性
	 * @return
	 */
	public Map<String,Object> getProperties(){
		Map<String,Object> properties=new HashMap<String,Object>();
		properties.putAll(this.dbEnvir.getTransactionProperties());
		properties.putAll(this.dbEnvir.getDataSourceProperties());
		return properties;
	}
	/*---------------事务相关---------------*/

	/**
	 * 会话提交
	 */
	public void commit(){
		this.session.commit();
	}

	/**
	 * 会话回滚
	 */
	public void rollback(){
		this.session.rollback();
	}

	/**
	 * 关闭会话
	 */
	public void close(){
		this.session.close();
	}

	/*---------------操作相关---------------*/
	/**
	 * 执行新增操作
	 * @param id
	 * @param parameter
	 * @return
	 */
	public int insert(String id,Object parameter){
		return this.update(id,parameter);
	}

	/**
	 * 执行更新操作
	 * @param id
	 * @param parameter
	 * @return
	 */
	public int update(String id,Object parameter){
		return this.session.update(id,parameter);
	}

	/**
	 * 执行删除操作
	 * @param id
	 * @param parameter
	 * @return
	 */
	public int delete(String id,Object parameter){
		return this.update(id,parameter);
	}

	/**
	 * 执行sql语句
	 * @param sql
	 * @param parameter
	 * @return
	 * @throws SQLException
	 */
	public int updateSQL(String sql,Object parameter) throws SQLException{
		return SQLHelper.getPreparedSQL(this.session,sql,parameter).update();
	}

	/**
	 * 返回数据集
	 * @param id
	 * @param parameter
	 * @return
	 */
	public List<Map<String,Object>> query(String id,Object parameter){
		return this.session.selectList(id,parameter);
	}

	/**
	 * 返回单行数据
	 * @param id
	 * @param parameter
	 * @return
	 */
	public Map<String,Object> queryUnique(String id,Object parameter){
		return this.session.selectOne(id,parameter);
	}

	/**
	 * 返回数据集
	 * @param sql
	 * @param parameter
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String,Object>> querySQL(String sql,Object parameter) throws SQLException{
		return SQLHelper.getPreparedSQL(this.session,sql,parameter).query();
	}

	/**
	 * 查询数据库元数据
	 * @return
	 * @throws HibernateException
	 */
	public DatabaseMetaData queryMetaData() throws SQLException{
		return this.session.getConnection().getMetaData();
	}
}
