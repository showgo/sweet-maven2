package org.sweet.frameworks.database.dao;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.sweet.frameworks.database.core.DatabaseSession;
import org.sweet.frameworks.database.envir.DBEnvironments;
import org.sweet.frameworks.database.sql.SQL;
import org.sweet.frameworks.database.transaction.DatabaseTransaction;

/**
 * DAO工厂类
 * @filename:DAOFactory
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public final class DAOFactory {
	private static DAOFactory factory=null;
	/* 重入锁 */
	private static ReentrantLock lock=null;

	static{
		factory=new DAOFactory();
		lock=new ReentrantLock();
	}

	/**
	 * 构造函数(默认数据源)
	 * @throws SQLException
	 */
	private DAOFactory(){
	}

	/**
	 * 获得DAO
	 * @return
	 */
	public static DAO getDAO(){
		try{
			lock.lock();
			return factory.new DAOImpl();
		}finally{
			lock.unlock();
		}
	}

	/**
	 * 获得DAO
	 * @return
	 */
	public static DAO getDAO(String dasName){
		try{
			lock.lock();
			return (null!=dasName&&!"".equals(dasName)) ? factory.new DAOImpl(dasName) : factory.new DAOImpl();
		}finally{
			lock.unlock();
		}
	}

	// ************************************************************//
	// ***********************以下是私有内部类************************//
	// ************************************************************//
	/**
	 * 数据库DAO(内部类)
	 */
	private class DAOImpl implements DAO {
		private String dasName=null;

		/**
		 * 构造函数
		 */
		public DAOImpl(){
			this(DBEnvironments.getDefaultEnvironment().getId());
		}

		/**
		 * 构造函数
		 */
		public DAOImpl(String dasName){
			this.dasName=dasName;
		}

		/**
		 * 执行sql语句
		 * @param id sql语句配置id
		 * @param parameter 参数
		 * @return 执行结果标志(或标志数组)
		 * @throws SQLException
		 */
		public int execute(final String id,final Object parameter) throws SQLException{
			int[] updated=null;
			if(null!=id&&!"".equals(id)){
				DatabaseTransaction trans=new DatabaseTransaction(this.dasName) {
					protected int[] doTransaction(DatabaseSession session) throws SQLException{
						return new int[]{session.update(id,parameter)};
					}
				};
				// 开始事务
				updated=trans.doTransaction();
			}else{
				throw new SQLException("The SQL statement to be executed is empty, please check...");
			}
			return (null!=updated) ? updated[0] : 0;
		}

		/**
		 * 执行预定义语句组(包括执行语句和查询语句)
		 * @param sql
		 * @return true/false
		 * @throws SQLException
		 */
		public boolean execute(final SQL sql) throws SQLException{
			if(null!=sql){
				DatabaseTransaction trans=new DatabaseTransaction(this.dasName) {
					protected int[] doTransaction(DatabaseSession session) throws SQLException{
						return sql.execute(session);
					}
				};
				// 开始事务
				trans.doTransaction();
				return true;
			}else{
				throw new SQLException("The SQL statement to be executed is empty, please check...");
			}
		}

		/**
		 * 执行sql语句
		 * @param sql sql语句
		 * @param parameter 参数
		 * @return 执行结果标志(或标志数组)
		 * @throws SQLException
		 */
		public int executeSQL(final String sql,final Object parameter) throws SQLException{
			int[] updated=null;
			if(null!=sql&&!"".equals(sql)){
				DatabaseTransaction trans=new DatabaseTransaction(this.dasName) {
					protected int[] doTransaction(DatabaseSession session) throws SQLException{
						return new int[]{session.updateSQL(sql,parameter)};
					}
				};
				// 开始事务
				updated=trans.doTransaction();
			}else{
				throw new SQLException("The SQL statement to be executed is empty, please check...");
			}
			return (null!=updated) ? updated[0] : 0;
		}

		/**
		 * 执行查询语句
		 * @param id sql语句配置id
		 * @param parameter 参数
		 * @return 查询结果集
		 * @throws SQLException
		 */
		public List<Map<String,Object>> query(final String id,final Object parameter) throws SQLException{
			final List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			if(null!=id&&!"".equals(id)){
				DatabaseTransaction trans=new DatabaseTransaction(this.dasName) {
					protected int[] doTransaction(DatabaseSession session) throws SQLException{
						List<Map<String,Object>> results=session.query(id,parameter);
						if(null!=results&&results.size()>0){
							list.addAll(results);
						}
						return new int[]{1};
					}
				};
				// 开始事务
				trans.doTransaction();
			}else{
				throw new SQLException("The SQL statement to be executed is empty, please check...");
			}
			return list;
		}

		/**
		 * 执行查询语句
		 * @param sql sql语句
		 * @param parameter 参数
		 * @return 查询结果集
		 * @throws SQLException
		 */
		public List<Map<String,Object>> querySQL(final String sql,final Object parameter) throws SQLException{
			final List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			if(null!=sql&&!"".equals(sql)){
				DatabaseTransaction trans=new DatabaseTransaction(this.dasName) {
					protected int[] doTransaction(DatabaseSession session) throws SQLException{
						List<Map<String,Object>> results=session.querySQL(sql,parameter);
						if(null!=results&&results.size()>0){
							list.addAll(results);
						}
						return new int[]{1};
					}
				};
				// 开始事务
				trans.doTransaction();
			}else{
				throw new SQLException("The SQL statement to be executed is empty, please check...");
			}
			return list;
		}

		/**
		 * 查询数据库元数据
		 * @return
		 * @throws HibernateException
		 */
		public DatabaseMetaData queryMetaData() throws SQLException{
			final List<DatabaseMetaData> list=new ArrayList<DatabaseMetaData>();
			try{
				DatabaseTransaction trans=new DatabaseTransaction(this.dasName) {
					protected int[] doTransaction(DatabaseSession session) throws SQLException{
						list.add(session.queryMetaData());
						return new int[]{1};
					}
				};
				// 开始事务
				trans.doTransaction();
			}catch(SQLException e){
				throw new SQLException(e.getMessage());
			}
			return list.size()>0 ? list.get(0) : null;
		}
	}
}
